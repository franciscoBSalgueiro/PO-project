package prr.notifications;

import prr.terminals.Terminal;

/* TODO métodos de delivery (wtf are they even for tho, mfw mando um pombo para casa do DM) */
public class Observer {
	private Terminal _subscriber;
	private Notification _notif;

	public Observer(Terminal subscriber, Notification notif) {
		_subscriber = subscriber;
		_notif = notif;
	}

	public void update(Terminal subject, String status) {
		if (canSend(status)) {
			send();
			_notif = null;
		}
	}

	public boolean hasSent() {
		return _notif == null;
	}

	public boolean canSend(String status) {
		return _notif.canSend(status);
	}

	public void send() {
		_notif.send(_subscriber);
	}
}
