package prr.clients;

import prr.plans.NormalPaymentPlan;

public class NormalClient extends ClientType {
	public NormalClient(Client c) {
		super(c);
		_plan = new NormalPaymentPlan();
	}

	@Override
	public String toString() {
		return "NORMAL";
	}
}
