package prr.terminals;

import prr.exceptions.TerminalAlreadyIdleException;

public class IdleStatus extends TerminalStatus {
    public IdleStatus(Terminal terminal,
            long debt,
            long payments) {
        super(terminal, debt, payments);
    }

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
        getTerminal().setStatus(new OffStatus(getTerminal(), getDebt(), getPayments()));
    }

    @Override
    public void turnIdle() throws TerminalAlreadyIdleException {
        throw new TerminalAlreadyIdleException();
    }

    @Override
    public void turnSilent() {
        getTerminal().setStatus(new SilentStatus(getTerminal(), getDebt(), getPayments()));
    }

    @Override
    public void turnBusy() {
        getTerminal().setStatus(new BusyStatus(getTerminal(), getDebt(), getPayments(), false));
    }

    @Override
    public String toString() {
        return "IDLE";
    }
}