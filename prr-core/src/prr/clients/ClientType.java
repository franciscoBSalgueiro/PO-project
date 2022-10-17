package prr.clients;

import java.io.Serializable;

public abstract class ClientType implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;
    private Client _client;

    public ClientType(Client c) { _client=c; }

    public Client getClient() { return _client; }

    @Override
    abstract public String toString();
}
