package prr.notifications;

import prr.terminals.Terminal;

/* FIXME todo este método de notificações é um bocado ridiculo mas funciona and thats what matters */
public abstract class Notification {
	protected String _subjectKey; //FIXME idk se pode ser protected neste caso

	public Notification(String key) {
		_subjectKey = key;
	}

	public boolean canSend(String status) {
		return status.equals("IDLE");
	}

	public void send(Terminal subscriber) {
		subscriber.addNotification(this);
	}

	@Override
	public String toString() {
		return _subjectKey;
	}
}
