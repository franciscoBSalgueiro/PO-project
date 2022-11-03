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
import prr.exceptions.InvalidCommunicationException;
import prr.exceptions.NoCurrentCommunicationException;
import prr.exceptions.TerminalAlreadyIdleException;
import prr.exceptions.TerminalAlreadyOffException;
import prr.exceptions.TerminalAlreadySilentException;
import prr.exceptions.UnavailableTerminalException;
import prr.exceptions.UnknownCommunicationTypeException;
import prr.exceptions.UnknownTerminalException;
import prr.exceptions.UnsupportedAtDestinationException;
import prr.exceptions.UnsupportedAtOriginException;
import prr.notifications.Notification;
import prr.notifications.Observer;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable {
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
        private List<Observer> _textObservers;

        Terminal(String key, Client client) {
                _key = key;
                _friends = new TreeMap<String, Terminal>();
                _client = client;
                _status = new IdleStatus(this, 0, 0);
                _inComms = new TreeMap<Integer, Communication>();
                _outComms = new TreeMap<Integer, Communication>();
                _observers = new ArrayList<Observer>();
                _textObservers = new ArrayList<Observer>();
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
                return _status.getPayments();
        }

        public long getDebts() {
                return _status.getDebt();
        }

        public long getBalance() {
                return getPayments() - getDebts();
        }

        public void addTextObserver(Client o) {
                if (o.activeNotifications()) {
                        _observers.add(o);
                        _textObservers.add(o);
                }
        }

        public void addInteractiveObserver(Client o) {
                if (o.activeNotifications()) _observers.add(o);
        }

        public void removeTextObserver(Observer o) {
                _observers.remove(o);
                _textObservers.remove(o);
        }

        public void removeInteractiveObserver(Observer o) {
                _observers.remove(o);
        }

        public void notifyAllObservers(Notification n) {
                for (Observer o : _observers)
                        o.update(n);
                _observers.clear();
        }

        public void notifyTextObservers(Notification n) {
                for (Observer o : _textObservers) {
                        o.update(n);
                        _observers.remove(o);
                }
                _textObservers.clear();
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

        public void performPayment(int key) throws InvalidCommunicationException {
                Communication c = _outComms.get(key);
                if (c == null || c.isPaid() || c == _currentCommunication) {
                        throw new InvalidCommunicationException(key);
                }
                c.pay();
                _status.addPayment(c.getCost());
                _client.updateType();
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

        public boolean isFriend(Terminal t) {
                return _friends.containsValue(t);
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

        public void addCommunication(Communication c) {
                if (c.getOrigin() == this) {
                        _outComms.put(c.getKey(), c);
                } else {
                        _inComms.put(c.getKey(), c);
                }
        }

        public void addCurrentCommunication(InteractiveCommunication comm) {
                addCommunication(comm);
                _currentCommunication = comm;
                _status.turnBusy();
        }

        public void sendTextCommunication(Network network, String destKey, String msg)
                        throws DestinationIsOffException, UnknownTerminalException, UnavailableTerminalException {
                Terminal dest = network.getTerminal(destKey);
                if (!dest.isOn()) {
                        dest.addTextObserver(_client);
                        throw new DestinationIsOffException(destKey);
                }
                if (canStartCommunication()) {
                        TextCommunication comm = network.addTextCommunication(this, _client, dest, msg);
                        addCommunication(comm);
                        _status.addDebt(comm.getCost());
                        _client.addText();
                        dest.addCommunication(comm);
                } else {
                        throw new UnavailableTerminalException(_key);
                }
        }

        public void startInteractiveCommunication(Network network, String destKey, String type)
                        throws DestinationIsOffException, DestinationIsBusyException, DestinationIsSilentException,
                        UnknownTerminalException, UnsupportedAtOriginException, UnsupportedAtDestinationException,
                        UnavailableTerminalException, UnknownCommunicationTypeException {
                Terminal dest = network.getTerminal(destKey);
                if (type.equals("VIDEO")) {
                        if (!supportsVideoCommunications()) {
                                throw new UnsupportedAtOriginException(_key, type);
                        }
                        if (!dest.supportsVideoCommunications()) {
                                throw new UnsupportedAtDestinationException(destKey, type);
                        }
                }
                if (destKey.equals(_key)) {
                        throw new DestinationIsBusyException(destKey);
                } else if (dest.isBusy()) {
                        dest.addInteractiveObserver(_client);
                        throw new DestinationIsBusyException(destKey);
                } else if (dest.isSilent()) {
                        dest.addInteractiveObserver(_client);
                        throw new DestinationIsSilentException(destKey);
                } else if (!dest.isOn()) {
                        dest.addInteractiveObserver(_client);
                        throw new DestinationIsOffException(destKey);
                } else if (canStartCommunication()) {
                        InteractiveCommunication comm = network.addInteractiveCommunication(this, dest, type);
                        addCurrentCommunication(comm);
                        dest.addCurrentCommunication(comm);
                } else {
                        throw new UnavailableTerminalException(_key);
                }
        }

        public long endCurrentCommunication(Network network, int duration) throws NoCurrentCommunicationException {
                Terminal destination = _currentCommunication.getDestination();
                InteractiveCommunication current = getCurrentCommunication();
                current.end(duration);
                long cost = current.calculateCost(_client);
                _status.addDebt(cost);
                _client.addVideo();
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
