package prr.clients;

import prr.plans.NormalPaymentPlan;

public class NormalClient extends ClientType {
	public NormalClient(Client client) {
		super(client);
		setPaymentPlan(new NormalPaymentPlan());
	}

	@Override
	public void updateType() {
		if (getClient().getBalance() > 500) {
			getClient().setType(new GoldClient(getClient()));
		}
	}

	@Override
	public String toString() {
		return "NORMAL";
	}
}
