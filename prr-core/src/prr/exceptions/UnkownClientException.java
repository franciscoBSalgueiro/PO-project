package prr.exceptions;

public class UnkownClientException extends Exception {
    /** Class serial number. */
	private static final long serialVersionUID = 201109020943L;

	/** The client's key. */
	private final String _key;

	/** @param key */
	public UnkownClientException(String key) {
		_key = key;
	}

	/** @return the key */
	public String getKey() {
		return _key;
	}
}
