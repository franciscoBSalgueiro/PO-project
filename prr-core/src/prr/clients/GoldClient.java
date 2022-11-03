package prr.clients;

import prr.plans.GoldPaymentPlan;

public class GoldClient extends ClientType {
    public GoldClient(Client client) {
		super(client);
		setPaymentPlan(new GoldPaymentPlan());
	}

    @Override
    public void updateType() {
        if (getClient().getBalance() < 0) {
            getClient().setType(new NormalClient(getClient()));
        }
        if (getStreakVideo() >= 5) {
            getClient().setType(new PlatinumClient(getClient()));
        }
    }

    @Override
    public String toString() {
        return "GOLD";
    }
}
