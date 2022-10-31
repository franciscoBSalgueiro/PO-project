package prr.notifications;

import prr.terminals.Terminal;

public class OffNotification extends Notification{
	private Notification _notif;

	public OffNotification(Terminal t) {
		super(t);
	}

	@Override
	public boolean canSend(String status) {
		switch (status) {
			case "IDLE": _notif = new OffToIdle(_subject); return true;
			case "SILENCE": _notif = new OffToSilent(_subject); return true;
			default: return false;
		}
	}

	@Override
	public void send(Terminal subscriber) {
		_notif.send(subscriber);
	}

}
