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

	@Override
	public String toString() {
		return getSubjectKey();
	}
}
