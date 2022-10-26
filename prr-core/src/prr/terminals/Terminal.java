package prr.terminals;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import prr.clients.Client;
import prr.communications.Communication;
import prr.exceptions.DestinationUnavailableException;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */ {
        /** Serial number for serialization. */
        private static final long serialVersionUID = 202208091753L;

        private Client _client;
        private String _key;
        private Map<String, Terminal> _friends;
        private TerminalStatus _status;
        private Map<Integer, Communication> _communications;
        private Communication _currentCommunication;

        Terminal(String key, Client client) {
                _key = key;
                _friends = new TreeMap<String, Terminal>();
                _client = client;
                _status = new IdleStatus(this);
                _communications = new TreeMap<Integer, Communication>();
        }

        // FIXME define attributes
        // FIXME define contructor(s)
        // FIXME define methods

        public String getKey() {
                return _key;
        }

        public boolean isOn() {
                return _status.isOn();
        }

        public Collection<Communication> getCommunications() {
                return Collections.unmodifiableCollection(_communications.values());
        }

        /**
         * Checks if this terminal can end the current interactive communication.
         *
         * @return true if this terminal is busy (i.e., it has an active interactive
         *         communication) and
         *         it was the originator of this communication.
         **/
        public boolean canEndCurrentCommunication() {
                return _status.canEndCurrentCommunication();
        }

        /**
         * Checks if this terminal can start a new communication.
         *
         * @return true if this terminal is neither off neither busy, false otherwise.
         **/
        public boolean canStartCommunication() {
                return _status.canStartCommunication();
        }

        /* FIXME add Javadoc */
        public void addFriend(Terminal t) {
                _friends.put(t.getKey(), t);
        }

        /* FIXME add Javadoc */
        public void removeFriend(Terminal t) {
                _friends.remove(t.getKey());
        }

        public void setStatus(TerminalStatus status) {
                _status = status;
        }

        /* FIXME add Javadoc */
        public void turnOff() {
                _status.turnOff();
        }

        /* FIXME add Javadoc */
        public void turnOn() {
                _status.turnOn();
        }

        /* FIXME add Javadoc */
        public void silence() {
                _status.silence();
        }

        public void sendTextCommunication(int key, Communication c, Terminal destination)
                        throws DestinationUnavailableException {
                if (canStartCommunication() && destination.isOn()) {
                        _communications.put(c.getKey(), c);
                } else {
                        throw new DestinationUnavailableException();
                }
        }

        @Override
        public String toString() {
                // terminalType|terminalId|clientId|terminalStatus|balance-paid|balance-debts|friend1,...,friend
                return _key + "|" + _client.getKey() + "|" + _status + "|" + "0" + "|" + "0";
        }
}
