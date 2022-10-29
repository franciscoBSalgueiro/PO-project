package prr.terminals;

import prr.exceptions.TerminalAlreadyOffException;

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
    public void turnIdle() {
        getTerminal().setStatus(new IdleStatus(getTerminal()));
    }

    @Override
    public void turnOff() throws TerminalAlreadyOffException {
        throw new TerminalAlreadyOffException();
    }

    @Override
    public void turnSilent() {
        getTerminal().setStatus(new SilentStatus(getTerminal()));
    }

    @Override
    public void turnBusy() {}

    @Override
    public String toString() {
        return "OFF";
    }
}
