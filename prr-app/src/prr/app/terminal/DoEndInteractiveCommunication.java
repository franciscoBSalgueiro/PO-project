package prr.app.terminal;

import prr.Network;
import prr.exceptions.NoCurrentCommunicationException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for ending communication.
 */
class DoEndInteractiveCommunication extends TerminalCommand {

	DoEndInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.END_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canEndCurrentCommunication());
		addIntegerField("duration", Prompt.duration());
	}

	@Override
	protected final void execute() throws CommandException {
		int duration = integerField("duration");
		try {
			long cost = _receiver.endCurrentCommunication(_network, duration);
			_display.popup(Message.communicationCost(cost));
		} catch (NoCurrentCommunicationException e) {
			// never happens
			e.printStackTrace();
		}

	}
}
