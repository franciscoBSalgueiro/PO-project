package prr.exceptions;

public class DuplicateClientException extends Exception {
	/** Class serial number. */
	private static final long serialVersionUID = 202208091753L;

    /** The client's key. */
    private final String _key;

    /** @param key */
    public DuplicateClientException(String key) {
        _key = key;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }
}
