package prr.notifications;

public class BusyToIdle extends Notification{
	
	public BusyToIdle(String key) {
		super(key);
	}

	@Override
	public String toString() {
		return "B2I" + super.toString();
	}
}
