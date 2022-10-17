package prr.communications;

import java.io.Serializable;

import prr.terminals.Terminal;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract Communication
 */
abstract public class Communication implements Serializable /* FIXME maybe addd more interfaces */ {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    private static int _key = 0; //why is this attribute static?
    private Terminal _origin;
    private Terminal _destination;

    Communication(Terminal origin, Terminal destination) {
        _key = _key++;
        _origin = origin;
        _destination = destination;
    }

    // FIXME define attributes
    // FIXME define contructor(s)
    // FIXME define methods

    public int getKey() { return _key; }

    public Terminal getOrigin() {
        return _origin;
    }

    @Override
    // type|idCommunication|idSender|idReceiver|units|price|status
    public String toString() {
        return _key + "|" + _origin.getKey() + "|" + _destination.getKey();
    }
}
