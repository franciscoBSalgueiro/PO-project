package prr.notifications;

import prr.terminals.Terminal;

public class OffToIdle extends Notification{
	
	public OffToIdle(Terminal t) {
		super(t);
	}

	@Override
	public String toString() {
		return "O2I|" + super.toString();
	}
}
