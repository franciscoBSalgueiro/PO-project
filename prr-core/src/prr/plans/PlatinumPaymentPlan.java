package prr.plans;

import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;

public class PlatinumPaymentPlan extends PaymentPlan {
    @Override
    public int getTextCost(TextCommunication communication) {
        int size = communication.getSize();
        if (size < 50) {
            return 0;
        } else {
            return 4;
        }
    }

    @Override
    public int getVoiceCost(VoiceCommunication communication) {
        int duration = communication.getDuration();
        return 10 * duration;
    }

    @Override
    public int getVideoCost(VideoCommunication communication) {
        int duration = communication.getDuration();
        return 10 * duration;
    }
}
