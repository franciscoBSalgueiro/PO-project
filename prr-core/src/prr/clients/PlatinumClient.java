package prr.clients;

import prr.plans.PlatinumPaymentPlan;

public class PlatinumClient extends ClientType {
    public PlatinumClient(Client c) {
        super(c);
        _plan = new PlatinumPaymentPlan();
    }

    @Override
    public String toString() {
        return "PLATINUM";
    }
}
