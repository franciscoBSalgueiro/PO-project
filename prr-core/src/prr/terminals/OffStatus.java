package prr.terminals;

public class OffStatus extends TerminalStatus {
    public OffStatus(Terminal terminal) { super(terminal); }

    @Override
    public boolean canEndCurrentCommunication() {
        return false;
    }

    @Override
    public boolean canStartCommunication() {
        return false;
    }

    @Override
    public boolean isOn() {
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
        return "OFF";
    }
}
