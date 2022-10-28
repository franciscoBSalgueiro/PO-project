package prr.exceptions;

public class UnsupportedAtOriginException extends Exception {
	/** Class serial number. */
	private static final long serialVersionUID = 202208091753L;

    /** The terminal's key. */
    private final String _key;


    /** The terminal's key. */
    private final String _type;

    /** @param key */
    public UnsupportedAtOriginException(String key, String type) {
        _key = key;
        _type = type;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }

    /** @return the type */
    public String getType() {
        return _type;
    }
}
