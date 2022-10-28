package prr.communications;

import prr.terminals.Terminal;

public abstract class InteractiveCommunication extends Communication {
    private int _duration;

    public InteractiveCommunication(int key, Terminal origin, Terminal destination) {
        super(key, origin, destination);
    }
}
