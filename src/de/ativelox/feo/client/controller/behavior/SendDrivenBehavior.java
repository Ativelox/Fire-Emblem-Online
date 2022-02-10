package de.ativelox.feo.client.controller.behavior;

import de.ativelox.feo.client.model.map.Map;
import de.ativelox.feo.client.model.network.IPlayerControllerSender;
import de.ativelox.feo.client.model.property.EAffiliation;
import de.ativelox.feo.client.model.unit.IUnit;
import de.ativelox.feo.network.protocol.EC2S;
import de.ativelox.feo.network.protocol.ES2C;

/**
 * @author Ativelox ({@literal ativelox.dev@web.de})
 *
 */
public class SendDrivenBehavior extends DefaultPlayerBehavior {

    private final IPlayerControllerSender<EC2S, ES2C> mPc;

    /**
     * @param map
     * @param affiliation
     */
    public SendDrivenBehavior(Map map, EAffiliation affiliation, IPlayerControllerSender<EC2S, ES2C> pc) {
	super(map, affiliation);

	mPc = pc;
    }

    @Override
    public void onTurnStart() {
	mMap.getCommander(mAffiliation).ifPresent(c -> mController.moveCursor(mMap.getByPos(c.getX(), c.getY())));

	super.onTurnStart();
    }

    @Override
    public void beforeAttack(IUnit target) {
	mPc.sendAttack(mPathRoutine.getActor(), target, mPathRoutine.getPath());

    }

    @Override
    public void beforeWait() {
	mPc.sendWait(mPathRoutine.getActor(), mPathRoutine.getPath());
    }

    @Override
    public void beforeTurnEnd() {
	mPc.sendEndTurn();

    }
}
