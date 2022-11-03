package prr.clients;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import prr.communications.Communication;
import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;
import prr.exceptions.NotificationsAlreadyDisabledException;
import prr.exceptions.NotificationsAlreadyEnabledException;
import prr.notifications.Notification;
import prr.notifications.NotificationDeliveryMethod;
import prr.notifications.Observer;
import prr.terminals.Terminal;

/**
 * Abstract client.
 */
public class Client implements Serializable, Observer /* FIXME maybe addd more interfaces */ {
        /** Serial number for serialization. */
        private static final long serialVersionUID = 202208091753L;

        private String _key;
        private String _name;
        private int _taxId;
        private ClientType _type;
        private Map<String, Terminal> _terminals;
        private boolean _activeNotifications;
        private List<Notification> _notifications = new ArrayList<>();
        private NotificationDeliveryMethod _notificationDeliveryMethod = _notifications::add;

        public Client(String key, String name, int taxId) {
                _key = key;
                _name = name;
                _taxId = taxId;
                _type = new NormalClient();
                _terminals = new TreeMap<String, Terminal>();
                _activeNotifications = true;
        }

        public Client(String key, String name, int taxId, NotificationDeliveryMethod delivery) {
                _key = key;
                _name = name;
                _taxId = taxId;
                _type = new NormalClient();
                _terminals = new TreeMap<String, Terminal>();
                _activeNotifications = true;
                _notifications = new ArrayList<Notification>();
                _notificationDeliveryMethod = delivery;
        }

        public int getCost(TextCommunication communication) {
                return _type.getTextCost(communication);
        }

        public int getCost(VoiceCommunication communication) {
                return _type.getVoiceCost(communication);
        }

        public int getCost(VideoCommunication communication) {
                return _type.getVideoCost(communication);
        }

        public String getKey() {
                return _key;
        }

        public boolean activeNotifications() {
                return _activeNotifications;
        }

        public void addTerminal(Terminal t) {
                _terminals.put(t.getKey(), t);
        }

        public void enableNotifications() throws NotificationsAlreadyEnabledException {
                if (_activeNotifications) {
                        throw new NotificationsAlreadyEnabledException();
                }
                _activeNotifications = true;
        }

        public void disableNotifications() throws NotificationsAlreadyDisabledException {
                if (!_activeNotifications) {
                        throw new NotificationsAlreadyDisabledException();
                }
                _activeNotifications = false;
        }

        public long getPayments() {
                long total = 0;
                for (Terminal t : _terminals.values()) {
                        total += t.getPayments();
                }
                return total;
        }

        public long getDebts() {
                long total = 0;
                for (Terminal t : _terminals.values()) {
                        total += t.getDebts();
                }
                return total;
        }

        public long getBalance() {
                return getPayments() - getDebts();
        }

        public Collection<Communication> getInComms() {
                List<Communication> comms = new ArrayList<>();
                for (Terminal t : _terminals.values()) {
                        comms.addAll(t.getInComms());
                }
                return comms;
        }

        public Collection<Communication> getOutComms() {
                List<Communication> comms = new ArrayList<>();
                for (Terminal t : _terminals.values()) {
                        comms.addAll(t.getOutComms());
                }
                return comms;
        }

        /*
         * public void payComm(Terminal t, Communication c) throws
         * CommunicationAlreadyPaidException {
         * getCost(c);
         * }
         */

        public void addNotification(Notification n) {
                if (!_notifications.contains(n))
                        _notificationDeliveryMethod.deliver(n);
        }

        public Collection<Notification> getAllNotifications() {
                Collection<Notification> notifications = new ArrayList<>(_notifications);
                clearNotifications();
                return notifications;
        }

        public void clearNotifications() {
                _notifications.clear();
        }

        public void update(Notification n) {
                addNotification(n);
        }

        @Override
        public String toString() {
                // CLIENT|key|name|taxId|type|notifications|terminals|payments|debts
                return "CLIENT|" + _key + "|" + _name + "|" + _taxId + "|" + _type + "|"
                                + (_activeNotifications ? "YES" : "NO") + "|" + _terminals.size() + "|" + getPayments()
                                + "|" + getDebts();
        }
}
