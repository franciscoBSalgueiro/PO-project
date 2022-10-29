package prr.communications;

import prr.clients.Client;
import prr.terminals.Terminal;

public class VideoCommunication extends InteractiveCommunication {
    public VideoCommunication(int key, Terminal origin, Terminal destination) {
        super(key, origin, destination);
    }

    @Override
    public String toString() {
        return "VIDEO|" + super.toString();
    }
    
    @Override
    public long calculateCost(Client client) {
        long cost = client.getCost(this);
        setCost(cost);
        return cost;
    }
}
