package prr.clients;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import prr.communications.Communication;
import prr.exceptions.NotificationsAlreadyDisabledException;
import prr.exceptions.NotificationsAlreadyEnabledException;
import prr.terminals.Terminal;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

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
        private List<Terminal> _terminals;
        private boolean activeNotifications;

        public Client(String key, String name, int taxId) {
                _key = key;
                _name = name;
                _taxId = taxId;
                _type = new NormalClient(this);
                _terminals = new ArrayList<Terminal>();
                activeNotifications = true;
        }

        // FIXME define attributes
        // FIXME define contructor(s)
        // FIXME define methods

        public String getKey() {
                return _key;
        }

        public void setClientType(ClientType type) {
                _type = type;
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
                return 0;
        }

        public Collection<Communication> getCommunications() {
                // FIXME DM doesnt like lists
                List<Communication> communications = new ArrayList<Communication>();
                for (Terminal t : _terminals) {
                        communications.addAll(t.getCommunications());
                }
                return communications;
        }

        @Override
        public String toString() {
                // CLIENT|key|name|taxId|type|notifications|terminals|payments|debts
                return "CLIENT|" + _key + "|" + _name + "|" + _taxId + "|" + _type + "|" + (activeNotifications ? "YES" : "NO") + "|" + _terminals.size() + "|" + getPayments() + "|" + getDebts();
        }
}
