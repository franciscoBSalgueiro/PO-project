package prr.terminals;

public class OffStatus extends TerminalStatus {
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
    public String toString() {
        return "OFF";
    }
}
