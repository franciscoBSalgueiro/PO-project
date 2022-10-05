package prr.communications;

import prr.terminals.Terminal;

public abstract class InteractiveCommunication extends Communication {
    private int _duration;

    public InteractiveCommunication(Terminal origin, Terminal destination, int duration) {
        super(origin, destination);
        this._duration = duration;
    }
}
