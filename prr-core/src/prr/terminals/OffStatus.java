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
        getTerminal().setStatus(new IdleStatus(getTerminal()));
    }

    @Override
    public void turnSilent() {
        getTerminal().setStatus(new SilentStatus(getTerminal()));
    }

    @Override
    public String toString() {
        return "OFF";
    }
}
