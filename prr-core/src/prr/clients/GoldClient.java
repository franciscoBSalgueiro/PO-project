package prr.clients;

import prr.plans.GoldPaymentPlan;

public class GoldClient extends ClientType {
    public GoldClient() {
        _plan = new GoldPaymentPlan();
    }

    @Override
    public String toString() {
        return "GOLD";
    }
}
