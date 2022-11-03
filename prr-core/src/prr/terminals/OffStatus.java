package prr.terminals;

import prr.exceptions.TerminalAlreadyOffException;
import prr.notifications.OffToIdle;
import prr.notifications.OffToSilent;

public class OffStatus extends TerminalStatus {
    public OffStatus(Terminal terminal,
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
        return false;
    }

    @Override
    public boolean isOn() {
        return false;
    }

    @Override
    public void turnIdle() {
        getTerminal().setStatus(new IdleStatus(getTerminal(), getDebt(), getPayments()));
        sendNotification(new OffToIdle(getTerminal()));
    }

    @Override
    public void turnOff() throws TerminalAlreadyOffException {
        throw new TerminalAlreadyOffException();
    }

    @Override
    public void turnSilent() {
        getTerminal().setStatus(new SilentStatus(getTerminal(), getDebt(), getPayments()));
        sendTextNotification(new OffToSilent(getTerminal()));
    }

    @Override
    public void turnBusy() {
    }

    @Override
    public String toString() {
        return "OFF";
    }
}
