package prr.terminals;

import java.io.Serializable;

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

	public void turnOff() {}
	public void turnOn() {}
	public void turnSilent() {}
	public void turnBusy() {}

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
