package prr.communications;

import prr.terminals.Terminal;

public class TextCommunication extends Communication {
    private String _message;

    public TextCommunication(int key, Terminal origin, Terminal destination, String message) {
        super(key, origin, destination);
        this._message = message;
    }

    @Override
    public String toString() {
        return "TEXT|" + super.toString() + "|" + _message.length();
    }
    
}
