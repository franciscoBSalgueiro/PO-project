package prr.notifications;

import prr.terminals.Terminal;

public class BusyToIdle extends Notification{
	
	public BusyToIdle(Terminal t) {
		super(t);
	}

	@Override
	public String toString() {
		return "B2I|" + super.toString();
	}
}
