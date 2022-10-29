package prr.clients;

import prr.plans.GoldPaymentPlan;

public class GoldClient extends ClientType {
    public GoldClient(Client c) {
        super(c);
        _plan = new GoldPaymentPlan();
    }

    @Override
    public String toString() {
        return "GOLD";
    }
}
