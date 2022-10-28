package prr.app.terminal;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.exceptions.DestinationIsOffException;
import prr.exceptions.UnknownTerminalException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

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
                        _receiver.sendTextCommunication(_network,key,message);
                } catch (UnknownTerminalException e) {
                        throw new UnknownTerminalKeyException(key);
                } catch (DestinationIsOffException e) {
                        _display.popup(Message.destinationIsOff(key));
                }
        }
}
