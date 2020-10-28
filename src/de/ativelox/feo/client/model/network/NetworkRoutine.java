package de.ativelox.feo.client.model.network;

import java.net.InetAddress;
import java.net.Socket;

import de.ativelox.feo.client.controller.GameController;
import de.ativelox.feo.client.controller.behavior.IBehavior;
import de.ativelox.feo.client.controller.behavior.ReceiveDrivenBehavior;
import de.ativelox.feo.client.controller.behavior.SendDrivenBehavior;
import de.ativelox.feo.client.controller.input.InputManager;
import de.ativelox.feo.client.model.camera.Camera;
import de.ativelox.feo.client.model.map.Map;
import de.ativelox.feo.client.model.map.TutorialMap;
import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.client.view.screen.IScreenManager;
import de.ativelox.feo.client.view.screen.game.GameScreen;
import de.ativelox.feo.client.view.screen.game.GameUIScreen;
import de.ativelox.feo.client.view.screen.game.IGameScreen;
import de.ativelox.feo.client.view.screen.game.IGameUIScreen;
import de.ativelox.feo.network.INetworkController;
import de.ativelox.feo.network.protocol.EC2S;
import de.ativelox.feo.network.protocol.ES2C;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class NetworkRoutine {

    private final Camera mCamera;

    private final IScreenManager mManager;

    private final InputManager mInputManager;

    private ReceiveDrivenBehavior mReceiveBehavior;

    private Map mMap;

    private IGameScreen mGameScreen;

    private IGameUIScreen mUiScreen;

    private INetworkController<EC2S, ES2C> mNetworkController;

    public NetworkRoutine(Camera camera, IScreenManager manager, InputManager inputManager) {
	mCamera = camera;
	mManager = manager;
	mInputManager = inputManager;

	this.connect();

    }

    private void connect() {
	mMap = new TutorialMap(0, 0);
	mCamera.setBounds(mMap);
	mGameScreen = new GameScreen(mMap, mCamera);
	mUiScreen = new GameUIScreen();

	mReceiveBehavior = new ReceiveDrivenBehavior(mMap, EAffiliation.OPPOSED, this);

	try {
	    @SuppressWarnings("resource")
	    Socket server = new Socket(InetAddress.getByName("localhost"), 2555);
	    mNetworkController = new ClientNetworkController(mReceiveBehavior, server.getInputStream(),
		    server.getOutputStream());
	    new Thread(mNetworkController).start();

	} catch (Exception e) {

	}
    }

    public void start(int playerId, long seed) {

	IPlayerControllerSender<EC2S, ES2C> controllerSender = new DefaultPlayerControllerSender();
	controllerSender.register(mNetworkController);

	IBehavior behavior1 = new SendDrivenBehavior(mMap, EAffiliation.ALLIED, controllerSender);

	if (playerId == 0) {
	    new GameController(mManager, mInputManager, mMap, mCamera, mGameScreen, mUiScreen, behavior1,
		    mReceiveBehavior).setSeed(seed);
	} else {
	    mReceiveBehavior.setAffiliation(EAffiliation.ALLIED);
	    behavior1.setAffiliation(EAffiliation.OPPOSED);
	    new GameController(mManager, mInputManager, mMap, mCamera, mGameScreen, mUiScreen, mReceiveBehavior,
		    behavior1).setSeed(seed);
	}
	mManager.addScreen(mGameScreen);
	mManager.addScreen(mUiScreen);
    }

}
