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
        _terminal.setStatus(new OffStatus(_terminal));
    }

    @Override
    public void silence() {
        _terminal.setStatus(new SilentStatus(_terminal));
    }

    @Override
    public void busy() {
        _terminal.setStatus(new BusyStatus(_terminal));
    }

    @Override
    public String toString() {
        return "IDLE";
    }
}