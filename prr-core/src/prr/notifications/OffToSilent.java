package prr.notifications;

import prr.terminals.Terminal;

public class OffToSilent extends Notification{
	
	public OffToSilent(Terminal t) {
		super(t);
	}

	@Override
	public String toString() {
		return "O2S|" + super.toString(); 
	}
}
