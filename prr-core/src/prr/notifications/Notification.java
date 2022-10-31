package prr.notifications;

import prr.clients.Client;
import prr.terminals.Terminal;

/* FIXME todo este método de notificações é um bocado ridiculo mas funciona and thats what matters */
public abstract class Notification {
	protected Terminal _subject; //FIXME idk se pode ser protected neste caso

	public Notification(Terminal t) {
		_subject = t;
	}

	public String getSubjectKey() {
		return _subject.getKey();
	}

	public Client getClient() {
		return _subject.getClient();
	}

	public boolean canSend(String status) {
		return status.equals("IDLE");
	}

	public void send(Terminal subscriber) {
		subscriber.addNotification(this);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Notification n) {
			return getClient() == n.getClient();
		}
		return false;
	}

	@Override
	public String toString() {
		return getSubjectKey();
	}
}
