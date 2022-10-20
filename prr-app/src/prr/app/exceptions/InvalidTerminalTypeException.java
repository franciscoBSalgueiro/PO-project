package prr.app.exceptions;

import pt.tecnico.uilib.menus.CommandException;

/** Exception for unknown terminals. */
public class InvalidTerminalTypeException extends CommandException {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	/** @param type Unknown terminal to report. */
	public InvalidTerminalTypeException(String type) {
		super(Message.invalidTerminalType(type));
	}

}
