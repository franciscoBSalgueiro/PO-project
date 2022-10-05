package prr.terminals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import prr.clients.Client;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */ {
        private Client _client;
        private String _key;
        private List<Terminal> _friends;
        private TerminalStatus _status;

        Terminal(String key, Client client) {
                _key = key;
                _friends = new ArrayList<Terminal>();
                _client = client;
                _status = new IdleStatus();
        }

        /** Serial number for serialization. */
        private static final long serialVersionUID = 202208091753L;

        // FIXME define attributes
        // FIXME define contructor(s)
        // FIXME define methods

        /**
         * Checks if this terminal can end the current interactive communication.
         *
         * @return true if this terminal is busy (i.e., it has an active interactive
         *         communication) and
         *         it was the originator of this communication.
         **/
        public boolean canEndCurrentCommunication() {
                // FIXME add implementation code
                return false;
        }

        /**
         * Checks if this terminal can start a new communication.
         *
         * @return true if this terminal is neither off neither busy, false otherwise.
         **/
        public boolean canStartCommunication() {
                // FIXME add implementation code
                return true;
        }

        /* FIXME add Javadoc */
        public void addFriend(Terminal t) {
                _friends.add(t);
                t.addFriend(t);
        }

        /* FIXME add Javadoc */
        public void removeFriend(Terminal t) {
                _friends.remove(t);
                t.removeFriend(t);
        }

        /* FIXME add Javadoc */
        public void turnOff() {
                _status = new OffStatus();
        }

        /* FIXME add Javadoc */
        public void turnOn() {
                _status = new IdleStatus();
        }

        /* FIXME add Javadoc */
        public void silence() {
                _status = new SilentStatus();
        }

        @Override
        public String toString() {
                // terminalType|terminalId|clientId|terminalStatus|balance-paid|balance-debts|friend1,...,friend
                return _key + "|" + _client.get_key() + "|" + _status + "|";
        }
}
