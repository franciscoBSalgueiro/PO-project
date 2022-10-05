package prr.terminals;

public class BusyStatus extends TerminalStatus {

    @Override
    public boolean canEndCurrentCommunication() {
        return true;
    }

    @Override
    public boolean canStartCommunication() {
        return false;
    }
    
    @Override
    public String toString() {
        return "BUSY";
    }
}