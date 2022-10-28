package prr.terminals;

public class BusyStatus extends TerminalStatus {
    private boolean _silent;
    public BusyStatus(Terminal terminal, boolean silent) {
        super(terminal);
        _silent = silent;
    }

    @Override
    public boolean isBusy() {
        return true;
    }

    public boolean wasSilent() { return _silent; }

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
    public void turnSilent() {
        getTerminal().setStatus(new SilentStatus(getTerminal()));
    }
    
    @Override
    public String toString() {
        return "BUSY";
    }
}