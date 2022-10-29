package prr.communications;

import prr.clients.Client;
import prr.terminals.Terminal;

public class VoiceCommunication extends InteractiveCommunication {
    public VoiceCommunication(int key, Terminal origin, Terminal destination) {
        super(key, origin, destination);
    }

    @Override
    public String toString() {
        return "VOICE|" + super.toString();
    }

    @Override
    public long calculateCost(Client client) {
        long cost = client.getCost(this);
        setCost(cost);
        return cost;
    }
}
