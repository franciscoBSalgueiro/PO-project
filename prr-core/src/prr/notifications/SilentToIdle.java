package prr.notifications;

public class SilentToIdle extends Notification{
	
	public SilentToIdle(String key) {
		super(key);
	}

	@Override
	public String toString() {
		return "S2I|" + super.toString();
	}
}