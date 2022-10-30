package prr.notifications;

import prr.terminals.Terminal;

public class OffNotification extends Notification{
	private Notification _notif;

	public OffNotification(String key) {
		super(key);
	}

	@Override
	public boolean canSend(String status) {
		switch (status) {
			case "IDLE": _notif = new OffToIdle(_subjectKey); return true;
			case "SILENCE": _notif = new OffToSilent(_subjectKey); return true;
			default: return false;
		}
	}

	@Override
	public void send(Terminal subscriber) {
		_notif.send(subscriber);
	}

}
