package prr.clients;

import java.io.Serializable;

import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;
import prr.plans.PaymentPlan;

public abstract class ClientType implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;
    protected PaymentPlan _plan; // FIXME: maybe should be private?

    public int getTextCost(TextCommunication communication) {
        return _plan.getTextCost(communication);
    }

    public int getVoiceCost(VoiceCommunication communication) {
        return _plan.getVoiceCost(communication);
    }

    public int getVideoCost(VideoCommunication communication) {
        return _plan.getVideoCost(communication);
    }

    @Override
    abstract public String toString();
}
