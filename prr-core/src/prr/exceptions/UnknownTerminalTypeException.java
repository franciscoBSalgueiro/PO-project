package prr.exceptions;

public class UnknownTerminalTypeException extends Exception {
    /** Class serial number. */
	private static final long serialVersionUID = 201109020943L;

	/** The terminal's type. */
	private final String _type;

	/** @param key */
	public UnknownTerminalTypeException(String type) {
		_type = type;
	}

	/** @return the type */
	public String getType() {
		return _type;
	}
}
