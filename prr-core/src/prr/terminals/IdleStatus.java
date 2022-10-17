package prr.terminals;

public class IdleStatus extends TerminalStatus {
    public IdleStatus(Terminal terminal) { super(terminal); }

    @Override
    public boolean canEndCurrentCommunication() {
        return false;
    }

    @Override
    public boolean canStartCommunication() {
        return true;
    }

    @Override
    public void turnOff() {
        getTerminal().setStatus(new OffStatus(getTerminal()));
    }

    @Override
    public void silence() {
        getTerminal().setStatus(new SilentStatus(getTerminal()));
    }

    @Override
    public void busy() {
        getTerminal().setStatus(new BusyStatus(getTerminal()));
    }

    @Override
    public String toString() {
        return "IDLE";
    }
}