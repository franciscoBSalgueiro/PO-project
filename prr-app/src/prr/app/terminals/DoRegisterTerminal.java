package prr.app.terminals;

import prr.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {
	String[] options = new String[] { "BASIC", "FANCY" };

	DoRegisterTerminal(Network receiver) {
		super(Label.REGISTER_TERMINAL, receiver);
		super.addStringField("key", Prompt.terminalKey());
		super.addOptionField("type", Prompt.terminalType(), options);
		super.addStringField("clientKey", Prompt.clientKey());
	}

	@Override
	protected final void execute() throws CommandException {
		String key = super.stringField("key");
		String type = super.stringField("type");
		String clientKey = super.stringField("clientKey");
		_receiver.registerTerminal(key, type, clientKey);
	}
}
