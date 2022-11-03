package prr.plans;

import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;

public interface PaymentPlan {
    int getTextCost(TextCommunication communication);
    int getVoiceCost(VoiceCommunication communication);
    int getVideoCost(VideoCommunication communication);
}
