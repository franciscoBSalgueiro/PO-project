package prr.notifications;

public class OffToIdle extends Notification{
	
	public OffToIdle(String key) {
		super(key);
	}

	@Override
	public String toString() {
		return "O2I|" + super.toString();
	}
}
