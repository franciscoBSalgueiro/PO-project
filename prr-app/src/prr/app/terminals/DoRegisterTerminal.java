package prr.app.terminals;

import prr.Network;
import prr.app.exceptions.DuplicateTerminalKeyException;
import prr.app.exceptions.UnknownClientKeyException;
import prr.exceptions.DuplicateTerminalException;
import prr.exceptions.UnknownClientException;
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
		addStringField("key", Prompt.terminalKey());
		addOptionField("type", Prompt.terminalType(), options);
		addStringField("clientKey", Prompt.clientKey());
	}

	@Override
	protected final void execute() throws CommandException {
		String key = stringField("key");
		String type = stringField("type");
		String clientKey = stringField("clientKey");
		try {
			_receiver.registerTerminal(key, type, clientKey);
		} catch (UnknownClientException e) {
			throw new UnknownClientKeyException(clientKey);
		} catch (DuplicateTerminalException e) {
			throw new DuplicateTerminalKeyException(key);
		}
	}
}
