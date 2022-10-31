package prr.notifications;

public class OffToSilent extends Notification{
	
	public OffToSilent(String key) {
		super(key);
	}

	@Override
	public String toString() {
		return "O2S|" + super.toString(); 
	}
}
