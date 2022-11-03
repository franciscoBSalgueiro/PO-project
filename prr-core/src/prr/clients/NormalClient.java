package prr.clients;

import prr.plans.NormalPaymentPlan;

public class NormalClient extends ClientType {
	public NormalClient(Client client, int streakText, int streakVideo) {
		super(client, streakText, streakVideo);
		_plan = new NormalPaymentPlan();
	}

	@Override
	public void updateType() {
		if (getClient().getBalance() > 500) {
			getClient().setType(new GoldClient(getClient(), getStreakText(), getStreakVideo()));
		}
	}

	@Override
	public String toString() {
		return "NORMAL";
	}
}
