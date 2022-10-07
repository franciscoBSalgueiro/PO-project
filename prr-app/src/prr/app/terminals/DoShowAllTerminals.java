package prr.app.terminals;

import prr.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show all terminals.
 */
class DoShowAllTerminals extends Command<Network> {

	DoShowAllTerminals(Network receiver) {
		super(Label.SHOW_ALL_TERMINALS, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		_display.popup(_receiver.getAllTerminals());
	}
}
