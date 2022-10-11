package prr.terminals;

import java.io.Serializable;

/**
 * Abstract terminal status. Possible status are Idle or Off.
 */
public abstract class TerminalStatus implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    abstract public boolean canEndCurrentCommunication();
    abstract public boolean canStartCommunication();

    public boolean isOn() {
        return true;
    }

    @Override
    abstract public String toString();
}
