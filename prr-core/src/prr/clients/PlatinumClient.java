package prr.clients;

import prr.plans.PlatinumPaymentPlan;

public class PlatinumClient extends ClientType {
    public PlatinumClient(Client client) {
        super(client);
        setPaymentPlan(new PlatinumPaymentPlan());
    }

    @Override
    public void updateType() {
        if (getClient().getBalance() < 0) {
            getClient().setType(new NormalClient(getClient()));
        }
        if (getStreakText() >= 2) {
            getClient().setType(new GoldClient(getClient()));
        }
    }

    @Override
    public String toString() {
        return "PLATINUM";
    }
}
