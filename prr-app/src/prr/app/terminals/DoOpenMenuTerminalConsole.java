package prr.app.terminals;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add mode import if needed

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
		Terminal terminal = _receiver.getTerminal(key);
		if (terminal == null) {
			throw new UnknownTerminalKeyException(key);
		}
		(new prr.app.terminal.Menu(_receiver, terminal)).open();
	}
}
