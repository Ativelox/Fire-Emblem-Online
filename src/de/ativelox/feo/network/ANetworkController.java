package de.ativelox.feo.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringJoiner;

import de.ativelox.feo.network.property.EIO;
import de.ativelox.feo.network.util.ImmutablePair;
import de.ativelox.feo.network.util.NetworkUtils;

/**
 * Provides an abstract implementation for {@link INetworkController}, which
 * handles reading, and writing to the given streams. This should be started in
 * its own Thread, since this {@link Runnable} blocks the current Thread. Checks
 * every {@link ANetworkController#CHECK_EACH_MS} milliseconds for data on the
 * stream, and writes data to the stream. Subclasses of this should override
 * {@link ANetworkController#serve(Enum, String[])} to properly handle incoming
 * messages.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public abstract class ANetworkController<POut extends Enum<POut>, PIn extends Enum<PIn>>
        implements INetworkController<POut, PIn> {

    /**
     * The delimiter used to split incoming message parameters.
     */
    public static final String DELIMITER = "\t";

    /**
     * Provides a {@link BufferedWriter} for the underlying {@link OutputStream}.
     */
    protected final BufferedWriter mOs;

    /**
     * Provides a {@link BufferedReader} for the underlying {@link InputStream}.
     */
    protected final BufferedReader mIs;

    /**
     * Represents a buffer for all the messages that should be sent in the next
     * {@link ANetworkController#run()} iteration.
     */
    protected final Queue<ImmutablePair<POut, String[]>> mMessageBuffer;

    /**
     * The time in milliseconds to wait after each {@link ANetworkController#run()}
     * iteration.
     */
    protected final long CHECK_EACH_MS = 1000;

    /**
     * The class of the enumeration of the protocol that is read by this instance.
     */
    private final Class<PIn> mProtocol;

    /**
     * Whether this instance is currently running or not.
     */
    private boolean mIsRunning;

    /**
     * Whether to refuse reading from the {@link InputStream}.
     */
    private boolean mRefuseRead;

    /**
     * Whether to refuse writing to the {@link OutputStream}.
     */
    private boolean mRefuseWrite;

    /**
     * Creates a new {@link ANetworkController}.
     * 
     * @param is       The input stream to read from.
     * @param os       The output stream to write to.
     * @param protocol The protocol used for the incoming messages.
     */
    public ANetworkController(final InputStream is, final OutputStream os, final Class<PIn> protocol) {
        mOs = new BufferedWriter(new OutputStreamWriter(os));
        mIs = new BufferedReader(new InputStreamReader(is));
        mMessageBuffer = new LinkedList<>();
        mIsRunning = true;
        mProtocol = protocol;

        mRefuseRead = false;
        mRefuseWrite = false;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.model.INetworkController#ignore(de.ativelox.rummyz.model.
     * property.EIO)
     */
    @Override
    public void ignore(final EIO ioType) {
        switch (ioType) {
        case READ:
            mRefuseRead = true;
            break;

        case WRITE:
            mRefuseWrite = true;
            break;

        default:
            break;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        while (mIsRunning) {

            // first read incoming messages from the server
            try {

                while (mIs.ready()) {
                    final String line = mIs.readLine();

                    if (line == null) {
                        break;

                    }
                    final String[] split = line.split(DELIMITER);
                    final String[] args = new String[split.length - 1];

                    for (int i = 0; i < args.length; i++) {
                        args[i] = split[i + 1];
                    }

                    this.serve(NetworkUtils.ensureEnumConversion(mProtocol, split[0]), args);

                }

            } catch (IOException e1) {
                e1.printStackTrace();

            }

            final int size = mMessageBuffer.size();

            for (int i = 0; i < size; i++) {
                try {
                    final ImmutablePair<POut, String[]> pair = mMessageBuffer.poll();
                    final StringJoiner sj = new StringJoiner("\t");

                    sj.add(pair.getKey().ordinal() + "");

                    if (pair.getValue() != null) {
                        for (final String arg : pair.getValue()) {
                            sj.add(arg);
                        }
                    }

                    mOs.write(sj.toString() + "\n");

                } catch (IOException e) {
                    e.printStackTrace();

                }

            }
            try {
                mOs.flush();

            } catch (IOException e1) {
                e1.printStackTrace();

            }

            try {
                Thread.sleep(CHECK_EACH_MS);

            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.model.INetworkController#send(java.lang.Object)
     */
    @Override
    public void send(final POut protocol) {
        this.send(protocol, null);

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.model.INetworkController#send(java.lang.Object,
     * java.lang.String)
     */
    @Override
    public void send(final POut protocol, final String[] additional) {
        if (mRefuseWrite) {
            return;
        }

        mMessageBuffer.add(new ImmutablePair<>(protocol, additional));

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.model.INetworkController#serve(java.lang.String)
     */
    @Override
    public void serve(final PIn protocol, final String[] additional) {
        if (mRefuseRead) {
            return;

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see de.ativelox.rummyz.model.INetworkController#stop()
     */
    @Override
    public void stop() {
        mIsRunning = false;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.ativelox.rummyz.model.INetworkController#unignore(de.ativelox.rummyz.model
     * .property.EIO)
     */
    @Override
    public void unignore(final EIO ioType) {
        switch (ioType) {
        case READ:
            mRefuseRead = false;
            break;

        case WRITE:
            mRefuseWrite = false;
            break;

        default:
            break;

        }
    }
}
