package prr.exceptions;

public class InvalidCommunicationException extends Exception {
    /** Class serial number. */
	private static final long serialVersionUID = 201109020943L;

	/** The communication's key. */
	private final int _key;

	/** @param key */
	public InvalidCommunicationException(int key) {
		_key = key;
	}

	/** @return the key */
	public int getKey() {
		return _key;
	}
}
