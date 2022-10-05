package prr.terminals;

public class SilentStatus extends TerminalStatus {

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
        return "SILENT";
    }
}
