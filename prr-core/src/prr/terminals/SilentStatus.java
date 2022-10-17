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
    public void busy() {
        getTerminal().setStatus(new BusyStatus(getTerminal()));
    }

    @Override
    public String toString() {
        return "SILENT";
    }
}
