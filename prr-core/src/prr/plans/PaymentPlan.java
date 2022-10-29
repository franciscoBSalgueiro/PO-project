package prr.plans;

import java.io.Serializable;

import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;

public abstract class PaymentPlan implements Serializable {
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    // Maybe add a plan name here?

    abstract public int getTextCost(TextCommunication communication);
    abstract public int getVoiceCost(VoiceCommunication communication);
    abstract public int getVideoCost(VideoCommunication communication);
}
