package prr.plans;

import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;

public class NormalPaymentPlan extends BasePlan {
    @Override
    public int getTextCost(TextCommunication communication) {
        int size = communication.getUnits();
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
        int duration = communication.getUnits();
        return 20 * duration;
    }

    @Override
    public int getVideoCost(VideoCommunication communication) {
        int duration = communication.getUnits();
        return 30 * duration;
    }
}
