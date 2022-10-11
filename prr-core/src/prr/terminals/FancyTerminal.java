package prr.terminals;

import prr.clients.Client;

public class FancyTerminal extends Terminal {

    public FancyTerminal(String key, Client client, TerminalStatus status) {
        super(key, client, status);
    }

    @Override
    public String toString() {
        return "FANCY|" + super.toString();
    }
}
