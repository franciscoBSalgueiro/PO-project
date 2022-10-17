package prr.terminals;

public class BusyStatus extends TerminalStatus {
    public BusyStatus(Terminal terminal) { super(terminal); }

    @Override
    public boolean canEndCurrentCommunication() {
        return true;
    }

    @Override
    public boolean canStartCommunication() {
        return false;
    }

    @Override
    public void turnOn() {
        getTerminal().setStatus(new IdleStatus(getTerminal()));
    }

    @Override
    public void silence() {
        getTerminal().setStatus(new SilentStatus(getTerminal()));
    }
    
    @Override
    public String toString() {
        return "BUSY";
    }
}