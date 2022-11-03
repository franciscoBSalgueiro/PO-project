package prr.exceptions;

public class UnavailableTerminalException extends Exception {
    private static final long serialVersionUID = 202208091753L;

    /** The terminal's key. */
    private final String _key;

    /** @param key */
    public UnavailableTerminalException(String key) {
        _key = key;
    }

    /** @return the key */
    public String getKey() {
        return _key;
    }
}
