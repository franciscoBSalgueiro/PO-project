package prr.notifications;

import java.io.Serializable;

import prr.terminals.Terminal;

public abstract class Notification implements Serializable {
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private Terminal _subject;

	public Notification(Terminal t) {
		_subject = t;
	}

	@Override
	public String toString() {
		return _subject.getKey();
	}
}
