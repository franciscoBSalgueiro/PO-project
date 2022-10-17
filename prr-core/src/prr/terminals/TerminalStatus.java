package prr.terminals;

import java.io.Serializable;

/**
 * Abstract terminal status. Possible status are Idle or Off.
 */
public abstract class TerminalStatus implements Serializable {
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;
	
	public Terminal _terminal; //public por agora

	public TerminalStatus(Terminal terminal) { _terminal = terminal; }

	abstract public boolean canEndCurrentCommunication();
	abstract public boolean canStartCommunication();

	public void turnOff() {}
	public void turnOn() {}
	public void silence() {}
	public void busy() {}

	public boolean isOn() {
		return true;
	}

	@Override
	abstract public String toString();
}
