package prr.app.terminal;

import prr.Network;
import prr.exceptions.NoCurrentCommunicationException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
// Add more imports if needed

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {

	DoPerformPayment(Network context, Terminal terminal) {
		super(Label.PERFORM_PAYMENT, context, terminal);
		addIntegerField("key", Prompt.commKey());
	}

	@Override
	protected final void execute() throws CommandException {
        int key = integerField("key");
		try {
			_receiver.getCurrentCommunication();
			//FIXME implement command

		} catch (NoCurrentCommunicationException e) {
			_display.popup(Message.invalidCommunication());
		}
	}
}
