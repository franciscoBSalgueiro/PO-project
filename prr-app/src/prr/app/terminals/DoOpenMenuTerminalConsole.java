package prr.app.terminals;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.exceptions.UnknownTerminalException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {

	DoOpenMenuTerminalConsole(Network receiver) {
		super(Label.OPEN_MENU_TERMINAL, receiver);
		addStringField("key", Prompt.terminalKey());
	}

	@Override
	protected final void execute() throws CommandException {
		String key = stringField("key");
		try {
			Terminal terminal = _receiver.getTerminal(key);
			(new prr.app.terminal.Menu(_receiver, terminal)).open();
		} catch (UnknownTerminalException e) {
			throw new UnknownTerminalKeyException(key);
		}
	}
}
