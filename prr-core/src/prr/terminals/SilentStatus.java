package prr.terminals;

import prr.exceptions.TerminalAlreadySilentException;
import prr.notifications.SilentToIdle;

public class SilentStatus extends TerminalStatus {
    public SilentStatus(Terminal terminal,
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
    public void turnIdle() {
        getTerminal().setStatus(new IdleStatus(getTerminal(), getDebt(), getPayments()));
        sendNotification(new SilentToIdle(getTerminal()));
    }

    @Override
    public void turnOff() {
        getTerminal().setStatus(new OffStatus(getTerminal(), getDebt(), getPayments()));
    }

    @Override
    public void turnSilent() throws TerminalAlreadySilentException {
        throw new TerminalAlreadySilentException();
    }

    @Override
    public void turnBusy() {
        getTerminal().setStatus(new BusyStatus(getTerminal(), getDebt(), getPayments(), true));
    }

    @Override
    public boolean isSilent() {
        return true;
    }

    @Override
    public String toString() {
        return "SILENCE";
    }
}
