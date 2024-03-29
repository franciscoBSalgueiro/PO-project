package prr.terminals;

import prr.clients.Client;

public class BasicTerminal extends Terminal {
    public BasicTerminal(String key, Client client) {
        super(key, client);
    }

    @Override
    public boolean supportsVideoCommunications() {
        return false;
    }

    @Override
    public String toString() {
        return "BASIC|" + super.toString();
    }
}
