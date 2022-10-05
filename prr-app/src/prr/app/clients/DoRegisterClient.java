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
		super.addStringField("key", Prompt.key());
		super.addStringField("name", Prompt.name());
		super.addIntegerField("taxId", Prompt.taxId());
	}

	@Override
	protected final void execute() throws CommandException {
		String key = super.stringField("key");
		String name = super.stringField("name");
		int taxId = super.integerField("taxId");
		_receiver.registerClient(key, name, taxId);
	}

}
