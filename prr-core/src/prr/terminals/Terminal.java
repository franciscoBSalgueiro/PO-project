package prr.terminals;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import prr.Network;
import prr.clients.Client;
import prr.communications.Communication;
import prr.communications.InteractiveCommunication;
import prr.communications.TextCommunication;
import prr.exceptions.DestinationIsBusyException;
import prr.exceptions.DestinationIsOffException;
import prr.exceptions.DestinationIsSilentException;
import prr.exceptions.NoCurrentCommunicationException;
import prr.exceptions.TerminalAlreadyIdleException;
import prr.exceptions.TerminalAlreadyOffException;
import prr.exceptions.TerminalAlreadySilentException;
import prr.exceptions.UnknownTerminalException;
import prr.exceptions.UnsupportedAtDestinationException;
import prr.exceptions.UnsupportedAtOriginException;

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
        private InteractiveCommunication _currentCommunication;

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

        public boolean isBusy() {
                return _status.isBusy();
        }

        public boolean isSilent() {
                return _status.isSilent();
        }

        public long getPayments() {
                return 0;
        }

        public long getDebts() {
                long total = 0;
                for (Communication c : _outComms.values()) {
                        total += c.getCost();
                }
                return total;
        }

        abstract public boolean supportsVideoCommunications();

        public Collection<Communication> getOutComms() {
                return Collections.unmodifiableCollection(_outComms.values());
        }

        public Collection<Communication> getInComms() {
                return Collections.unmodifiableCollection(_inComms.values());
        }

        public Communication getCurrentCommunication() throws NoCurrentCommunicationException {
                if (_currentCommunication == null) {
                        throw new NoCurrentCommunicationException();
                }
                return _currentCommunication;
        }

        /**
         * Checks if this terminal can end the current interactive communication.
         *
         * @return true if this terminal is busy (i.e., it has an active interactive
         *         communication) and
         *         it was the originator of this communication.
         **/
        public boolean canEndCurrentCommunication() {
                return _status.canEndCurrentCommunication() && _currentCommunication.getOrigin() == this;
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
                if (friend != this) {
                        _friends.put(friendKey, friend);
                }
        }

        public void removeFriend(Network network, String friendKey) throws UnknownTerminalException {
                Terminal friend = network.getTerminal(friendKey);
                _friends.remove(friendKey, friend);
        }

        public void setStatus(TerminalStatus status) {
                _status = status;
        }

        public void turnOff() throws TerminalAlreadyOffException {
                _status.turnOff();
        }

        public void turnOn() throws TerminalAlreadyIdleException {
                _status.turnIdle();
        }

        public void silence() throws TerminalAlreadySilentException {
                _status.turnSilent();
        }

        public void sendTextCommunication(Network network, String destinationKey, String message)
                        throws DestinationIsOffException, UnknownTerminalException {
                Terminal destination = network.getTerminal(destinationKey);
                if (!destination.isOn()) {
                        throw new DestinationIsOffException(destinationKey);
                }
                if (canStartCommunication()) {
                        TextCommunication communication = network.addTextCommunication(this, _client, destination,
                                        message);
                        // FIXME probably there's a better way to do this
                        _outComms.put(communication.getKey(), communication);
                        destination._inComms.put(communication.getKey(), communication);
                } else {
                        // FIXME maybe throw an exception
                }
        }

        public void startInteractiveCommunication(Network network, String destinationKey, String type)
                        throws DestinationIsOffException, DestinationIsBusyException, DestinationIsSilentException,
                        UnknownTerminalException, UnsupportedAtOriginException, UnsupportedAtDestinationException {
                Terminal destination = network.getTerminal(destinationKey);
                if (type == "VIDEO") {
                        if (!supportsVideoCommunications()) {
                                throw new UnsupportedAtOriginException(_key, type);
                        }
                        if (!destination.supportsVideoCommunications()) {
                                throw new UnsupportedAtDestinationException(destinationKey, type);
                        }
                }
                if (destination.isBusy()) {
                        throw new DestinationIsBusyException(destinationKey);
                }
                if (destination.isSilent()) {
                        throw new DestinationIsSilentException(destinationKey);
                }
                if (!destination.isOn()) {
                        throw new DestinationIsOffException(destinationKey);
                }
                if (canStartCommunication()) {
                        InteractiveCommunication communication = network.addInteractiveCommunication(this, destination,
                                        type);
                        // FIXME probably there's a better way to do this
                        _currentCommunication = communication;
                        destination._currentCommunication = communication;
                        _status.turnBusy();
                        destination._status.turnBusy();
                        _outComms.put(communication.getKey(), communication);
                        destination._inComms.put(communication.getKey(), communication);
                } else {
                        // FIXME Add exception
                }
        }

        public long endCurrentCommunication(Network network,
                        int duration) /* throws NoCurrentCommunicationException */ {
                // FIXME: Bad implementation
                _currentCommunication.end(duration);
                long cost = _currentCommunication.calculateCost(_client);
                _status.revert();
                _currentCommunication.getDestination()._status.revert();
                _currentCommunication.getDestination()._currentCommunication = null;
                _currentCommunication = null;
                return cost;

        }

        /**
         * @return string of friends' keys comma separated
         **/
        private String renderFriends() {
                String friends = "";
                if (_friends.size() > 0) {
                        friends += "|" + String.join(",", _friends.keySet());
                }
                return friends;
        }

        @Override
        public String toString() {
                // terminalType|terminalId|clientId|terminalStatus|balance-paid|balance-debts|friend1,...,friend
                return _key + "|" + _client.getKey() + "|" + _status + "|" + getPayments() + "|" + getDebts() + renderFriends();
        }
}
