package prr.communications;

import prr.terminals.Terminal;

public abstract class InteractiveCommunication extends Communication {
    private int _duration;
    private boolean _finished = false;

    public InteractiveCommunication(int key, Terminal origin, Terminal destination) {
        super(key, origin, destination);
    }

    public void end(int duration) {
        _duration = duration;
        _finished = true;
    }

    @Override
    public int getUnits() {
        return _duration;
    }

    @Override
    String getStatus() {
        return _finished ? "FINISHED" : "ONGOING";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
