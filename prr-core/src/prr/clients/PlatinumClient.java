package prr.clients;

import prr.plans.PlatinumPaymentPlan;

public class PlatinumClient extends ClientType {
    public PlatinumClient(Client client, int streakText, int streakVideo) {
        super(client, streakText, streakVideo);
        _plan = new PlatinumPaymentPlan();
    }

    @Override
    public void updateType() {
        if (getClient().getBalance() < 0) {
            getClient().setType(new NormalClient(getClient(), getStreakText(), getStreakVideo()));
        }
        if (getStreakText() >= 2) {
            getClient().setType(new GoldClient(getClient(), getStreakText(), getStreakVideo()));
        }
    }

    @Override
    public String toString() {
        return "PLATINUM";
    }
}
