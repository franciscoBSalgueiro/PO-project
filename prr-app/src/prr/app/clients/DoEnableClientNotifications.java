package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import prr.clients.Client;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Enable client notifications.
 */
class DoEnableClientNotifications extends Command<Network> {

	DoEnableClientNotifications(Network receiver) {
		super(Label.ENABLE_CLIENT_NOTIFICATIONS, receiver);
		super.addStringField("key", Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
		String key = super.stringField("key");
		Client client = _receiver.getClient(key);
		if (client == null)
			throw new UnknownClientKeyException(key);
		client.enableNotifications();
	}
}
