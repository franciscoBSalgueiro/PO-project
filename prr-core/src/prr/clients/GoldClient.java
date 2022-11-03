package prr.clients;

import prr.plans.GoldPaymentPlan;

public class GoldClient extends ClientType {
    public GoldClient(Client client, int streakText, int streakVideo) {
		super(client, streakText, streakVideo);
		_plan = new GoldPaymentPlan();
	}

    @Override
    public void updateType() {
        if (getClient().getBalance() < 0) {
            getClient().setType(new NormalClient(getClient(), getStreakText(), getStreakVideo()));
        }
        if (getStreakVideo() > 5) {
            getClient().setType(new PlatinumClient(getClient(), getStreakText(), getStreakVideo()));
        }
    }

    @Override
    public String toString() {
        return "GOLD";
    }
}
