package prr.communications;

import prr.terminals.Terminal;

public class VoiceCommunication extends InteractiveCommunication {
    public VoiceCommunication(int key, Terminal origin, Terminal destination, int duration) {
        super(key, origin, destination, duration);
    }

    @Override
    public String toString() {
        return "VOICE|" + super.toString();
    }
}
