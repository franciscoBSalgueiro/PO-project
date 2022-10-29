package prr.plans;

import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;

public class NormalPaymentPlan extends PaymentPlan {
    @Override
    public int getTextCost(TextCommunication communication) {
        int size = communication.getSize();
        if (size < 50) {
            return 10;
        } else if (size < 100) {
            return 16;
        } else {
            return 2 * size;
        }
    }

    @Override
    public int getVoiceCost(VoiceCommunication communication) {
        int duration = communication.getDuration();
        return 20 * duration;
    }

    @Override
    public int getVideoCost(VideoCommunication communication) {
        int duration = communication.getDuration();
        return 30 * duration;
    }
}
