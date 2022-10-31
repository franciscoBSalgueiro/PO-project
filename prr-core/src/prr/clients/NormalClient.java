package prr.clients;

import prr.plans.NormalPaymentPlan;

public class NormalClient extends ClientType {
	public NormalClient() {
		_plan = new NormalPaymentPlan();
	}

	@Override
	public String toString() {
		return "NORMAL";
	}
}
