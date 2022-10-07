package prr.app.clients;

import prr.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

	DoRegisterClient(Network receiver) {
		super(Label.REGISTER_CLIENT, receiver);
		addStringField("key", Prompt.key());
		addStringField("name", Prompt.name());
		addIntegerField("taxId", Prompt.taxId());
	}

	@Override
	protected final void execute() throws CommandException {
		String key = stringField("key");
		String name = stringField("name");
		int taxId = integerField("taxId");
		_receiver.registerClient(key, name, taxId);
	}

}
