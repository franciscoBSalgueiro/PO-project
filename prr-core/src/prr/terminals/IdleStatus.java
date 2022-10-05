package prr.terminals;

public class IdleStatus extends TerminalStatus {

    @Override
    public boolean canEndCurrentCommunication() {
        return false;
    }

    @Override
    public boolean canStartCommunication() {
        return true;
    }

    @Override
    public String toString() {
        return "IDLE";
    }
}