package prr.terminals;

import prr.clients.Client;

public class FancyTerminal extends Terminal {
    public FancyTerminal(String key, Client client) {
        super(key, client);
    }

    @Override
    public boolean supportsVideoCommunications() {
        return true;
    }

    @Override
    public String toString() {
        return "FANCY|" + super.toString();
    }
}
