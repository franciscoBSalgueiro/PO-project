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
		super.addStringField("clientId", Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
		String clientId = super.stringField("clientId");
		System.out.println(_receiver.getClient(clientId));
	}
}
