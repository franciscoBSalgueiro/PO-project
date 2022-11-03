package prr.app.terminal;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.exceptions.DestinationIsBusyException;
import prr.exceptions.DestinationIsOffException;
import prr.exceptions.DestinationIsSilentException;
import prr.exceptions.UnavailableTerminalException;
import prr.exceptions.UnknownCommunicationTypeException;
import prr.exceptions.UnknownTerminalException;
import prr.exceptions.UnsupportedAtDestinationException;
import prr.exceptions.UnsupportedAtOriginException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

	DoStartInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
		addStringField("key", Prompt.terminalKey());
		addOptionField("type", Prompt.commType(), "VIDEO", "VOICE");
	}

	@Override
	protected final void execute() throws CommandException {
		String key = stringField("key");
		String type = stringField("type");
		try {
			_receiver.startInteractiveCommunication(_network, key, type);
		} catch (UnsupportedAtOriginException e) {
			_display.popup(Message.unsupportedAtOrigin(e.getKey(), e.getType()));
		} catch (UnsupportedAtDestinationException e) {
			_display.popup(Message.unsupportedAtDestination(e.getKey(), e.getType()));
		} catch (DestinationIsBusyException e) {
			_display.popup(Message.destinationIsBusy(e.getKey()));
		} catch (DestinationIsSilentException e) {
			_display.popup(Message.destinationIsSilent(e.getKey()));
		} catch (DestinationIsOffException e) {
			_display.popup(Message.destinationIsOff(e.getKey()));
		} catch (UnknownTerminalException e) {
			throw new UnknownTerminalKeyException(key);
		} catch (UnavailableTerminalException | UnknownCommunicationTypeException e) {
			// never happens
			e.printStackTrace();
		}
	}
}
