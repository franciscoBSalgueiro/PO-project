package prr.terminals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
import prr.notifications.BusyToIdle;
import prr.notifications.Notification;
import prr.notifications.Observer;
import prr.notifications.OffNotification;
import prr.notifications.SilentToIdle;

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
        private List<Observer> _observers;

        Terminal(String key, Client client) {
                _key = key;
                _friends = new TreeMap<String, Terminal>();
                _client = client;
                _status = new IdleStatus(this);
                _inComms = new TreeMap<Integer, Communication>();
                _outComms = new TreeMap<Integer, Communication>();
                _observers = new ArrayList<Observer>();
        }

        public String getKey() {
                return _key;
        }

        public Client getClient() {
                return _client;
        }

        public TerminalStatus getStatus() {
                return _status;
        }

        public String getStatusString() {
                return _status.toString();
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

        public void addNotification(Notification n) {
                _client.addNotification(n);
        }

        public void addObserver(Observer o) {
                _observers.add(o);
        }

        public void removeObserver(Observer o) {
                _observers.remove(o);
        }

        public void notifyObservers() { // FIXME this is ugly
                List<Observer> toRemove = new ArrayList<>();
                for (Observer o : _observers) {
                        o.update(this, getStatusString());
                        if (o.hasSent())
                                toRemove.add(o);
                }
                for (Observer o : toRemove)
                        removeObserver(o);
        }

        public void sendOffObserver(Terminal t) {
                if (_client.activeNotifications())
                        t.addObserver(new Observer(this, new OffNotification(t)));
        }

        public void sendBusyObserver(Terminal t) {
                if (_client.activeNotifications())
                        t.addObserver(new Observer(this, new BusyToIdle(t)));
        }

        public void sendSilentObserver(Terminal t) {
                if (_client.activeNotifications())
                        t.addObserver(new Observer(this, new SilentToIdle(t)));
        }

        abstract public boolean supportsVideoCommunications();

        public Collection<Communication> getOutComms() {
                return Collections.unmodifiableCollection(_outComms.values());
        }

        public Collection<Communication> getInComms() {
                return Collections.unmodifiableCollection(_inComms.values());
        }

        public InteractiveCommunication getCurrentCommunication() throws NoCurrentCommunicationException {
                if (_currentCommunication == null) {
                        throw new NoCurrentCommunicationException();
                }
                return _currentCommunication;
        }

        public void removeCurrentcommunication() {
                _currentCommunication = null;
                _status.revert();
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
                notifyObservers();
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
                if (!destination.isOn()) { // TODO em todas as exceções tem de tar um create notif
                        sendOffObserver(destination);
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
                if (type.equals("VIDEO")) {
                        if (!supportsVideoCommunications()) {
                                throw new UnsupportedAtOriginException(_key, type);
                        }
                        if (!destination.supportsVideoCommunications()) {
                                throw new UnsupportedAtDestinationException(destinationKey, type);
                        }
                }
                if (destinationKey.equals(_key)) {
                        throw new DestinationIsBusyException(destinationKey);
                } else if (destination.isBusy()) {
                        sendBusyObserver(destination);
                        throw new DestinationIsBusyException(destinationKey);
                } else if (destination.isSilent()) {
                        sendSilentObserver(destination);
                        throw new DestinationIsSilentException(destinationKey);
                } else if (!destination.isOn()) {
                        sendOffObserver(destination);
                        throw new DestinationIsOffException(destinationKey);
                } else if (canStartCommunication()) {
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

        public long endCurrentCommunication(Network network, int duration) throws NoCurrentCommunicationException {
                Terminal destination = _currentCommunication.getDestination();
                InteractiveCommunication current = getCurrentCommunication();
                current.end(duration);
                long cost = current.calculateCost(_client);
                removeCurrentcommunication();
                destination.removeCurrentcommunication();
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
                return _key + "|" + _client.getKey() + "|" + _status + "|" + getPayments() + "|" + getDebts()
                                + renderFriends();
        }
}
