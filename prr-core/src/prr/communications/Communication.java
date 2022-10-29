package prr.communications;

import java.io.Serializable;

import prr.terminals.Terminal;

/**
 * Abstract Communication
 */
abstract public class Communication implements Serializable /* FIXME maybe addd more interfaces */ {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    private int _key;
    private Terminal _origin;
    private Terminal _destination;
    private long _cost = 0;

    Communication(int key, Terminal origin, Terminal destination) {
        _key = key;
        _origin = origin;
        _destination = destination;
    }

    public int getKey() { return _key; }

    public Terminal getOrigin() {
        return _origin;
    }

    public Terminal getDestination() {
        return _destination;
    }

    public long getCost() {
        return _cost;
    }

    public void setCost(long cost) {
        _cost = cost;
    }

    @Override
    // type|idCommunication|idSender|idReceiver|units|price|status
    public String toString() {
        return _key + "|" + _origin.getKey() + "|" + _destination.getKey();
    }
}
