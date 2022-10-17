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
        _terminal.setStatus(new IdleStatus(_terminal));
    }

    @Override
    public void silence() {
        _terminal.setStatus(new SilentStatus(_terminal));
    }
    
    @Override
    public String toString() {
        return "BUSY";
    }
}