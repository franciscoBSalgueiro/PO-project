package prr.terminals;

/**
 * Abstract terminal status. Possible status are Idle or Off.
 */
public abstract class TerminalStatus {

    abstract public boolean canEndCurrentCommunication();
    abstract public boolean canStartCommunication();

    public boolean isOn() {
        return true;
    }

    @Override
    abstract public String toString();
}
