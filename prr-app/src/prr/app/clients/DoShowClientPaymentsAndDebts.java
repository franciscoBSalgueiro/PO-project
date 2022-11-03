package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import prr.exceptions.UnknownClientException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

	DoShowClientPaymentsAndDebts(Network receiver) {
		super(Label.SHOW_CLIENT_BALANCE, receiver);
		addStringField("clientId", Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
        String clientId = stringField("clientId");
		try {
			long payments = _receiver.getClientPayments(clientId);
			long debts = _receiver.getClientDebts(clientId);
			_display.popup(Message.clientPaymentsAndDebts(clientId, payments, debts));
		} catch (UnknownClientException e) {
			throw new UnknownClientKeyException(clientId);
		}
	}
}
