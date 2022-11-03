package prr.notifications;

import java.io.Serializable;

import prr.terminals.Terminal;

public abstract class Notification implements Serializable{
	private Terminal _subject;

	public Notification(Terminal t) {
		_subject = t;
	}

	@Override
	public String toString() {
		return _subject.getKey();
	}
}
