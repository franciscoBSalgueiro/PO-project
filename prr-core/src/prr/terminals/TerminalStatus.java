package prr.terminals;

import java.io.Serializable;

import prr.exceptions.TerminalAlreadyIdleException;
import prr.exceptions.TerminalAlreadyOffException;
import prr.exceptions.TerminalAlreadySilentException;
import prr.notifications.Notification;

/**
 * Abstract terminal status. Possible status are Idle or Off.
 */
public abstract class TerminalStatus implements Serializable {
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
	private long _debt;
	private long _payments;

	private Terminal _terminal;

	public TerminalStatus(Terminal terminal, long debt, long payments) {
		_terminal = terminal;
		_debt = debt;
		_payments = payments;
	}

	public Terminal getTerminal() {
		return _terminal;
	}

	abstract public boolean canEndCurrentCommunication();

	abstract public boolean canStartCommunication();

	abstract public void turnOff() throws TerminalAlreadyOffException;

	abstract public void turnIdle() throws TerminalAlreadyIdleException;

	abstract public void turnSilent() throws TerminalAlreadySilentException;

	abstract public void turnBusy();

	public void revert() {
	};

	public boolean isOn() {
		return true;
	}

	public boolean isBusy() {
		return false;
	}

	public boolean isSilent() {
		return false;
	}

	public void sendNotification(Notification n) {
		_terminal.notifyAllObservers(n);
	}

	public void sendTextNotification(Notification n) {
		_terminal.notifyTextObservers(n);
	}

	public long getDebt() {
		return _debt;
	}

	public long getPayments() {
		return _payments;
	}

	public void addDebt(long debt) {
		_debt += debt;
	}

	public void addPayment(long payment) {
		_debt -= payment;
		_payments += payment;
	}

	@Override
	abstract public String toString();
}
