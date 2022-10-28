package prr.exceptions;

public class DestinationIsOffException extends Exception {
	/** Class serial number. */
	private static final long serialVersionUID = 202208091753L;

    /** The terminal's key. */
    private final String _key;

    /** @param key */
    public DestinationIsOffException(String key) {
        _key = key;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }
}
