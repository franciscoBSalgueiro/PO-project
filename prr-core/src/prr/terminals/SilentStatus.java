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
        _terminal.setStatus(new IdleStatus(_terminal));
    }

    @Override
    public void turnOff() {
        _terminal.setStatus(new OffStatus(_terminal));
    }

    @Override
    public void busy() {
        _terminal.setStatus(new BusyStatus(_terminal));
    }

    @Override
    public String toString() {
        return "SILENT";
    }
}
