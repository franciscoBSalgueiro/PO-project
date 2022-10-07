package prr.app.clients;

import prr.Network;
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
		_display.popup(_receiver.getClient(clientId));
	}
}
