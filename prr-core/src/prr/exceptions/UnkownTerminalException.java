package prr.exceptions;

public class UnkownTerminalException extends Exception {
    /** Class serial number. */
	private static final long serialVersionUID = 201109020943L;

	/** The terminal's key. */
	private final String _key;

	/** @param key */
	public UnkownTerminalException(String key) {
		_key = key;
	}

	/** @return the key */
	public String getKey() {
		return _key;
	}
}
