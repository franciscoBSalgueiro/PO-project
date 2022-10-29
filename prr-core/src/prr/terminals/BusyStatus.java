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

    @Override
    public boolean canEndCurrentCommunication() {
        return true;
    }

    @Override
    public boolean canStartCommunication() {
        return false;
    }

    @Override
    public void turnIdle() {
        getTerminal().setStatus(new IdleStatus(getTerminal()));
    }

    @Override
    public void turnSilent() {
        getTerminal().setStatus(new SilentStatus(getTerminal()));
    }

    @Override
    public void turnBusy() {};

    @Override
    public void turnOff() {};

    @Override
    public void revert() {
        if (_silent) {
            getTerminal().setStatus(new SilentStatus(getTerminal()));
        } else {
            getTerminal().setStatus(new IdleStatus(getTerminal()));
        }
    }
    
    @Override
    public String toString() {
        return "BUSY";
    }
}