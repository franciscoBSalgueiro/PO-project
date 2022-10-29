package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show balance.
 */
class DoShowTerminalBalance extends TerminalCommand {

	DoShowTerminalBalance(Network context, Terminal terminal) {
		super(Label.SHOW_BALANCE, context, terminal);
	}

	@Override
	protected final void execute() throws CommandException {
		String key = _receiver.getKey();
		long debts = _receiver.getDebts();
		long payments = _receiver.getPayments();
		_display.popup(Message.terminalPaymentsAndDebts(key, payments, debts));
	}
}
