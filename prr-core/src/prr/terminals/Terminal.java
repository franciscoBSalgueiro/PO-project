package prr.terminals;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import prr.Network;
import prr.clients.Client;
import prr.communications.Communication;
import prr.exceptions.DestinationUnavailableException;
import prr.exceptions.UnknownTerminalException;

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
        private Map<Integer, Communication> _inComms;
        private Map<Integer, Communication> _outComms;
        private Communication _currentCommunication;

        Terminal(String key, Client client) {
                _key = key;
                _friends = new TreeMap<String, Terminal>();
                _client = client;
                _status = new IdleStatus(this);
                _inComms = new TreeMap<Integer, Communication>();
                _outComms = new TreeMap<Integer, Communication>();
        }

        public String getKey() {
                return _key;
        }

        public boolean isOn() {
                return _status.isOn();
        }

        public Collection<Communication> getOutComms() {
                return Collections.unmodifiableCollection(_outComms.values());
        }

        public Collection<Communication> getInComms() {
                return Collections.unmodifiableCollection(_inComms.values());
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

        public void addFriend(Network network, String friendKey) throws UnknownTerminalException {
                Terminal friend = network.getTerminal(friendKey);
                _friends.put(friendKey, friend);
        }

        public void removeFriend(Network network, String friendKey) throws UnknownTerminalException {
                Terminal friend = network.getTerminal(friendKey);
                _friends.remove(friendKey, friend);
        }

        public void setStatus(TerminalStatus status) {
                _status = status;
        }

        public void turnOff() {
                _status.turnOff();
        }

        public void turnOn() {
                _status.turnOn();
        }

        public void silence() {
                _status.silence();
        }

        public void sendTextCommunication(Network network, String destinationKey, String message)
                        throws DestinationUnavailableException, UnknownTerminalException {
                Terminal destination = network.getTerminal(destinationKey);
                if (canStartCommunication() && destination.isOn()) {
                        Communication communication = network.addCommunication(this, destination, message);
                        // FIXME probably there's a better way to do this
                        _outComms.put(communication.getKey(), communication);
                        destination._inComms.put(communication.getKey(), communication);
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
