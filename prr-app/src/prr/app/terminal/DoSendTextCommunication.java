package prr.app.terminal;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.exceptions.DestinationUnavailableException;
import prr.exceptions.UnknownTerminalException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

        DoSendTextCommunication(Network context, Terminal terminal) {
                super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
                addStringField("key", Prompt.terminalKey());
                addStringField("message", Prompt.textMessage());
        }

        @Override
        protected final void execute() throws CommandException {
                String key = stringField("key");
                String message = stringField("message");
                try {
                        _network.sendTextCommunication(_receiver, key, message);
                } catch (UnknownTerminalException e) {
                        throw new UnknownTerminalKeyException(key);
                } catch (DestinationUnavailableException e) {
                        _display.popup(Message.destinationIsOff(key));
                }
        }
}
