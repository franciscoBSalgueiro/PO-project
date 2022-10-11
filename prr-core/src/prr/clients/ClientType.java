package prr.clients;

import java.io.Serializable;

public abstract class ClientType implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    @Override
    abstract public String toString();
}
