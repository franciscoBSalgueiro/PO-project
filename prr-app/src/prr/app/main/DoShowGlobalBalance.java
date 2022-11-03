package prr.app.main;

import prr.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show global balance.
 */
class DoShowGlobalBalance extends Command<Network> {

	DoShowGlobalBalance(Network receiver) {
		super(Label.SHOW_GLOBAL_BALANCE, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
		long payments = _receiver.getGlobalPayments();
		long debts =_receiver.getGlobalDebts();
		_display.popup(Message.globalPaymentsAndDebts(payments, debts));
	}
}
