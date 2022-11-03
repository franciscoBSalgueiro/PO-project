package prr.terminals;

import prr.notifications.BusyToIdle;

public class BusyStatus extends TerminalStatus {
    private boolean _silent;

    public BusyStatus(Terminal terminal,
            long debt,
            long payments,
            boolean silent) {
        super(terminal, debt, payments);
        _silent = silent;
    }

    @Override
    public boolean isBusy() {
        return true;
    }

    @Override
    public boolean canEndCurrentCommunication() {
        return true;
    }

    @Override
    public boolean canStartCommunication() {
        return false;
    }

    @Override
    public void turnIdle() {
        getTerminal().setStatus(new IdleStatus(getTerminal(), getDebt(), getPayments()));
        sendNotification(new BusyToIdle(getTerminal()));
    }

    @Override
    public void turnSilent() {
        getTerminal().setStatus(new SilentStatus(getTerminal(), getDebt(), getPayments()));
    }

    @Override
    public void turnBusy() {
    };

    @Override
    public void turnOff() {
    };

    @Override
    public void revert() {
        if (_silent) {
            turnSilent();
        } else {
            turnIdle();
        }
    }

    @Override
    public String toString() {
        return "BUSY";
    }
}