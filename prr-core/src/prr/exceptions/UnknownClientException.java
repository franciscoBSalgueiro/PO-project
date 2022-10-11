package prr.exceptions;

public class UnknownClientException extends Exception {
    /** Class serial number. */
	private static final long serialVersionUID = 201109020943L;

	/** The client's key. */
	private final String _key;

	/** @param key */
	public UnknownClientException(String key) {
		_key = key;
	}

	/** @return the key */
	public String getKey() {
		return _key;
	}
}
