package prr.clients;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import prr.communications.Communication;
import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;
import prr.exceptions.NotificationsAlreadyDisabledException;
import prr.exceptions.NotificationsAlreadyEnabledException;
import prr.terminals.Terminal;

/**
 * Abstract client.
 */
public class Client implements Serializable /* FIXME maybe addd more interfaces */ {
        /** Serial number for serialization. */
        private static final long serialVersionUID = 202208091753L;

        private String _key;
        private String _name;
        private int _taxId;
        private ClientType _type;
        private List<Terminal> _terminals; // FIXME maybe change to a map
        private boolean activeNotifications;

        public Client(String key, String name, int taxId) {
                _key = key;
                _name = name;
                _taxId = taxId;
                _type = new NormalClient(this);
                _terminals = new ArrayList<Terminal>();
                activeNotifications = true;
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

        public void addTerminal(Terminal t) {
                _terminals.add(t);
        }

        public void enableNotifications() throws NotificationsAlreadyEnabledException {
                if (activeNotifications) {
                        throw new NotificationsAlreadyEnabledException();
                }
                activeNotifications = true;
        }

        public void disableNotifications() throws NotificationsAlreadyDisabledException {
                if (!activeNotifications) {
                        throw new NotificationsAlreadyDisabledException();
                }
                activeNotifications = false;
        }

        public long getPayments() {
                return 0;
        }

        public long getDebts() {
                long total = 0;
                for (Terminal t : _terminals) {
                        total += t.getDebts();
                }
                return total;
        }

        public Collection<Communication> getInComms() {
                // FIXME DM doesnt like lists
                List<Communication> communications = new ArrayList<Communication>();
                for (Terminal t : _terminals) {
                        communications.addAll(t.getInComms());
                }
                return communications;
        }

        public Collection<Communication> getOutComms() {
                // FIXME DM doesnt like lists
                List<Communication> communications = new ArrayList<Communication>();
                for (Terminal t : _terminals) {
                        communications.addAll(t.getOutComms());
                }
                return communications;
        }

        @Override
        public String toString() {
                // CLIENT|key|name|taxId|type|notifications|terminals|payments|debts
                return "CLIENT|" + _key + "|" + _name + "|" + _taxId + "|" + _type + "|"
                                + (activeNotifications ? "YES" : "NO") + "|" + _terminals.size() + "|" + getPayments()
                                + "|" + getDebts();
        }
}
