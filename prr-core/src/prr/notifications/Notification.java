package prr.notifications;

import prr.terminals.Terminal;

public abstract class Notification {
	private Terminal _subject;

	public Notification(Terminal t) {
		_subject = t;
	}

	@Override
	public String toString() {
		return _subject.getKey();
	}
}
