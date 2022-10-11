package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import prr.exceptions.UnknownClientException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

	DoShowClient(Network receiver) {
		super(Label.SHOW_CLIENT, receiver);
		addStringField("clientId", Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
		String clientId = stringField("clientId");
		try {
			_display.popup(_receiver.getClient(clientId));
		} catch (UnknownClientException e) {
			throw new UnknownClientKeyException(clientId);
		}
	}
}
