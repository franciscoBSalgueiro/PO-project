package prr.exceptions;

public class UnknownCommunicationTypeException extends Exception {
    /** Class serial number. */
    private static final long serialVersionUID = 201109020943L;

    /** The communication's type. */
    private final String _type;

    /** @param key */
    public UnknownCommunicationTypeException(String type) {
        _type = type;
    }

    /** @return the type */
    public String getType() {
        return _type;
    }
}
