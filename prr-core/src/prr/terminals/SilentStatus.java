package prr.terminals;

public class SilentStatus extends TerminalStatus {
    public SilentStatus(Terminal terminal) { super(terminal); }

    @Override
    public boolean canEndCurrentCommunication() {
        return false;
    }

    @Override
    public boolean canStartCommunication() {
        return true;
    }

    @Override
    public void turnOn() {
        getTerminal().setStatus(new IdleStatus(getTerminal()));
    }

    @Override
    public void turnOff() {
        getTerminal().setStatus(new OffStatus(getTerminal()));
    }

    @Override
    public void turnBusy() {
        getTerminal().setStatus(new BusyStatus(getTerminal(), true));
    }

    @Override
    public boolean isSilent() {
        return true;
    }

    @Override
    public String toString() {
        return "SILENCE";
    }
}
