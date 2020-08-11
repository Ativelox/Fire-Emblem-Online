package de.ativelox.feo.client.model.network;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.client.model.unit.DummyUnit;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.client.model.unit.UnitProperties;
import de.ativelox.feo.network.ANetworkController;
import de.ativelox.feo.network.exception.UnsupportedProtocolException;
import de.ativelox.feo.network.protocol.EC2S;
import de.ativelox.feo.network.protocol.ES2C;
import de.ativelox.feo.util.Pair;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class ClientNetworkController extends ANetworkController<EC2S, ES2C> {

    /**
     * The controller for the player this controller is associated with, designed to
     * receive data from this class' underlying {@link InputStream}.
     */
    private final IPlayerControllerReceiver mPc;

    /**
     * Creates a new {@link ClientNetworkController}.
     * 
     * @param pc The {@link IPlayerControllerReceiver} that's used to send data read
     *           from the given {@link InputStream} to.s
     * @param is The {@link InputStream} from which to read data from in the
     *           protocol specified by {@link ES2C}.
     * @param os The {@link OutputStream} to which to write data in the protocol
     *           specified by {@link EC2S}.
     */
    public ClientNetworkController(IPlayerControllerReceiver pc, InputStream is, OutputStream os) {
        super(is, os, ES2C.class);

        mPc = pc;
    }

    @Override
    public void serve(final ES2C protocol, final String[] additional) {
        super.serve(protocol, additional);

        switch (protocol) {
        case ATTACK:
            mPc.onAttackReceived(decodeUnit(additional[0]), decodeUnit(additional[1]), decodePath(additional[2]));
            break;
        case END_TURN:
            mPc.onTurnEndReceived();
            break;
        case WAIT:
            // TODO: implement wait received
            break;
        case WELCOME:
            mPc.onServerWelcome(Integer.parseInt(additional[0]));
            break;
        default:
            throw new UnsupportedProtocolException("The protocol " + protocol + " isn't supported");
        }
    }

    private static IUnit decodeUnit(String encodedUnit) {
        String[] data = encodedUnit.split("\\s+");

        return new DummyUnit(0, 0, EAffiliation.valueOf(data[1]),
                UnitProperties.load(data[0].toLowerCase() + ".json").get());

    }

    private static List<Pair<Integer, Integer>> decodePath(String encodedPath) {
        String[] data = encodedPath.split("\\s+");

        System.out.println("Gotten: " + encodedPath);

        List<Pair<Integer, Integer>> coords = new ArrayList<>();

        for (int i = 0; i < data.length - 1; i += 2) {
            System.out.println("Decoding: " + Integer.parseInt(data[i]) + " " + Integer.parseInt(data[i + 1]));

            int value1 = Integer.parseInt(data[i]);
            int value2 = Integer.parseInt(data[i + 1]);

            if (coords.size() > 1 && value1 == coords.get(coords.size() - 1).getFirst()
                    && value2 == coords.get(coords.size() - 1).getSecond()) {
                continue;

            } 

            coords.add(Pair.of(value1, value2));

        }
        return coords;
    }
}
