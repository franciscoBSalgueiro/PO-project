package prr.clients;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

        public Client(String key, String name, int taxId) {
                _key = key;
                _name = name;
                _taxId = taxId;
                _type = new NormalClient();
                _terminals = new ArrayList<Terminal>();
        }

        // FIXME define attributes
        // FIXME define contructor(s)
        // FIXME define methods

        public String get_key() {
                return _key;
        }

        public void addTerminal(Terminal t) {
                _terminals.add(t);
        }

        @Override
        public String toString() {
                return "CLIENT|" + _key + "|" + _name + "|" + _taxId + "|" + _type; // |notifications|terminals|payments|debts
        }
}
