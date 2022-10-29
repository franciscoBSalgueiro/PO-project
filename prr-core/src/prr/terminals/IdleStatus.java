package prr.terminals;

import prr.exceptions.TerminalAlreadyIdleException;

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
    public void turnIdle() throws TerminalAlreadyIdleException {
        throw new TerminalAlreadyIdleException();
    }

    @Override
    public void turnSilent() {
        getTerminal().setStatus(new SilentStatus(getTerminal()));
    }

    @Override
    public void turnBusy() {
        getTerminal().setStatus(new BusyStatus(getTerminal(), false));
    }

    @Override
    public String toString() {
        return "IDLE";
    }
}