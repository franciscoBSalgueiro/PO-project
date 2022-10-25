package prr.communications;

import prr.terminals.Terminal;

public class VideoCommunication extends InteractiveCommunication {
    public VideoCommunication(int key, Terminal origin, Terminal destination, int duration) {
        super(key, origin, destination, duration);
    }

    @Override
    public String toString() {
        return "VIDEO|" + super.toString();
    }
    
}
