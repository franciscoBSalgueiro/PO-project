package prr.notifications;

public class SilentToIdle extends Notification{
	
	public SilentToIdle(String key) {
		super(key);
	}

	@Override
	public String toString() {
		return "B2I" + super.toString();
	}
}