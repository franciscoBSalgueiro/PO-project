package prr.communications;

import prr.terminals.Terminal;

public class TextCommunication extends Communication {
    private String _message;

    public TextCommunication(Terminal origin, Terminal destination, String message) {
        super(origin, destination);
        this._message = message;
    }

    @Override
    public String toString() {
        return "TEXT|" + _message;
    }
    
}
