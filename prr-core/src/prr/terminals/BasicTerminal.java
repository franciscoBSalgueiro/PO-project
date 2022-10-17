package prr.terminals;

import prr.clients.Client;

public class BasicTerminal extends Terminal {
    public BasicTerminal(String key, Client client) {
        super(key, client);
    }

    public BasicTerminal(String key, Client client, TerminalStatus status) {
        super(key, client, status);
    }

    @Override
    public String toString() {
        return "BASIC|" + super.toString();
    }
}
