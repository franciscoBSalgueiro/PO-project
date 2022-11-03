package prr.communications;

import prr.clients.Client;
import prr.terminals.Terminal;

public class TextCommunication extends Communication {
    private String _message;

    public TextCommunication(int key, Terminal origin, Terminal destination, String message) {
        super(key, origin, destination);
        _message = message;
    }

    @Override
    public String toString() {
        return "TEXT|" + super.toString();
    }

    @Override
    public int getUnits() {
        return _message.length();
    }

    @Override
    String getStatus() {
        return "FINISHED";
    }

    @Override
    public long calculateCost(Client client) {
        long cost = client.getCost(this);
        setCost(cost);
        return cost;
    }
}
