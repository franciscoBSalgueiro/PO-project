package prr.communications;

import prr.terminals.Terminal;

public class VideoCommunication extends InteractiveCommunication {
    public VideoCommunication(Terminal origin, Terminal destination, int duration) {
        super(origin, destination, duration);
    }

    @Override
    public String toString() {
        return "VIDEO|" + super.toString();
    }
    
}
