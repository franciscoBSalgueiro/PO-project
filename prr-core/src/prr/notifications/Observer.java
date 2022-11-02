package prr.notifications;

import prr.terminals.Terminal;

public interface Observer {
	public void update(Notification n);
}
