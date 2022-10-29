package prr.terminals;

import java.io.Serializable;

import prr.exceptions.TerminalAlreadyIdleException;
import prr.exceptions.TerminalAlreadyOffException;
import prr.exceptions.TerminalAlreadySilentException;

/**
 * Abstract terminal status. Possible status are Idle or Off.
 */
public abstract class TerminalStatus implements Serializable {
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
	
	private Terminal _terminal;

	public TerminalStatus(Terminal terminal) { _terminal = terminal; }

	public Terminal getTerminal() { return _terminal; }

	abstract public boolean canEndCurrentCommunication();
	abstract public boolean canStartCommunication();

	abstract public void turnOff() throws TerminalAlreadyOffException;
	abstract public void turnIdle() throws TerminalAlreadyIdleException;
	abstract public void turnSilent() throws TerminalAlreadySilentException;
	abstract public void turnBusy();
	public void revert() {};

	public boolean isOn() {
		return true;
	}

	public boolean isBusy() {
		return false;
	}

	public boolean isSilent() {
		return false;
	}

	@Override
	abstract public String toString();
}
