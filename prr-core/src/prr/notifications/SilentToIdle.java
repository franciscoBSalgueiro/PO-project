package prr.notifications;

import prr.terminals.Terminal;

public class SilentToIdle extends Notification{
	
	public SilentToIdle(Terminal t) {
		super(t);
	}

	@Override
	public String toString() {
		return "S2I" + super.toString();
	}
}