package de.ativelox.feo.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import de.ativelox.feo.network.INetworkController;
import de.ativelox.feo.network.protocol.EC2S;
import de.ativelox.feo.network.protocol.ES2C;
import de.ativelox.feo.server.controller.IGameController;
import de.ativelox.feo.server.controller.ServerNetworkController;

/**
 * The Server functions as the top-class for the Server, also having the
 * {@link Server#main(String[])} function. It maintains connection to its
 * clients, and creates an {@link IGameController} with multiple
 * {@link INetworkController} for each player which handles further socket
 * communication.
 * 
 * @author Ativelox {@literal <ativelox.dev@web.de>}
 *
 */
public final class Server {

    /**
     * The main method. Creates a new Server, calls {@link Server#init()} and
     * {@link Server#waitForPlayers()} to start a game.
     * 
     * @param args The command-line arguments. Unused.
     * @throws IOException If a socket related I/O exception occurs.
     */
    public static void main(final String[] args) throws IOException {
        final Server s = new Server(2555, 2);
        s.init();
        s.waitForPlayers();

    }

    /**
     * The socket representing the server.
     */
    private ServerSocket mSocket;

    /**
     * The port this server listens on.
     */
    private final int mPort;

    /**
     * The amount of players this server manages.
     */
    private final int mPlayerAmount;

    /**
     * The game controller used to manage the game.
     */
    private final IGameController<ES2C, EC2S> mGameController;

    /**
     * A list of all the {@link INetworkController}s for each player.
     */
    private final List<INetworkController<ES2C, EC2S>> mNetworkControllers;

    /**
     * A list of all the sockets corresponding to each client.
     */
    private final List<Socket> mSockets;

    /**
     * Creates a new {@link Server}.
     * 
     * @param port         The port this server listens on.
     * @param playerAmount The amount of players this server manages
     */
    public Server(final int port, final int playerAmount) {
        mPort = port;
        mPlayerAmount = playerAmount;
        mGameController = new de.ativelox.feo.server.controller.GameController(playerAmount);
        mNetworkControllers = new LinkedList<>();
        mSockets = new LinkedList<>();

    }

    /**
     * Initializes this servers socket.
     * 
     * @throws IOException If an I/O exception occurs when trying to open the
     *                     socket.
     */
    public void init() throws IOException {
        mSocket = new ServerSocket(mPort);
    }

    /**
     * Shuts down this server, by stopping all network controllers and closing all
     * sockets.
     * 
     * @throws IOException If an I/O exception occurs when trying to close a socket.
     */
    public void shutdown() throws IOException {
        for (final INetworkController<ES2C, EC2S> controller : mNetworkControllers) {
            controller.stop();
        }

        for (final Socket s : mSockets) {
            s.close();
        }
    }

    /**
     * Waits for {@link Server#mPlayerAmount} players, and then the game gets
     * started indirectly by the underlying {@link IGameController}. This method
     * blocks, until all players have connected.
     * 
     * @throws IOException If an I/O exception occurs when trying to connect to
     *                     sockets.
     */
    public void waitForPlayers() throws IOException {

        int playerId = 0;
        while (mSockets.size() < mPlayerAmount) {
            final Socket s = mSocket.accept();
            final INetworkController<ES2C, EC2S> nc = new ServerNetworkController(mGameController, playerId,
                    s.getInputStream(), s.getOutputStream());

            mNetworkControllers.add(nc);
            mSockets.add(s);
            new Thread(nc).start();

            mGameController.register(playerId, nc);

            playerId++;
        }
        mGameController.sendWelcome(0);
        mGameController.sendWelcome(1);

    }
}
