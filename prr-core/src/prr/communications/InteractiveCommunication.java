package prr.communications;

import prr.terminals.Terminal;

public abstract class InteractiveCommunication extends Communication {
    private int _duration;

    public InteractiveCommunication(int key, Terminal origin, Terminal destination, int duration) {
        super(key, origin, destination);
        this._duration = duration;
    }
}
