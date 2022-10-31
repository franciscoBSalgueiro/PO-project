package prr.clients;

import prr.plans.PlatinumPaymentPlan;

public class PlatinumClient extends ClientType {
    public PlatinumClient() {
        _plan = new PlatinumPaymentPlan();
    }

    @Override
    public String toString() {
        return "PLATINUM";
    }
}
