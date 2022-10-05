package prr;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import prr.app.exceptions.DuplicateClientKeyException;
import prr.app.exceptions.UnknownClientKeyException;
import prr.clients.Client;
import prr.communications.Communication;
import prr.exceptions.UnrecognizedEntryException;
import prr.terminals.BasicTerminal;
import prr.terminals.FancyTerminal;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	HashMap<String, Client> _clients;
	HashMap<String, Terminal> _terminals;
	ArrayList<Communication> _communications;

	public Network() {
		_clients = new HashMap<String, Client>();
		_terminals = new HashMap<String,Terminal>();
		_communications = new ArrayList<Communication>();
	}

	// FIXME define attributes
	// FIXME define contructor(s)
	// FIXME define methods

	/**
	 * Read text input file and create corresponding domain entities.
	 * 
	 * @param filename name of the text input file
	 * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException                if there is an IO erro while processing
	 *                                    the text file
	 */
	void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */ {
		// FIXME implement method
	}

	public HashMap<String, Client> getAllClients() {
		return _clients;
	}

	public Client getClient(String key) {
		return _clients.get(key);
	}

	public HashMap<String, Terminal> getAllTerminals() {
		return _terminals;
	}

	public Terminal getTerminal(String key) {
		return _terminals.get(key);
	}

	public ArrayList<Communication> getAllCommunications() {
		return _communications;
	}

	public void registerClient(String key, String name, int taxId) throws CommandException {
		if (_clients.containsKey(key)) {
			throw new DuplicateClientKeyException(key);
		}
		_clients.put(key, new Client(key, name, taxId));
	}

	public void registerTerminal(String key, String type, String clientKey) throws CommandException {
		Terminal terminal;
		Client client = _clients.get(clientKey);
		if (client == null) {
			throw new UnknownClientKeyException(clientKey);
		}
		if (type.equals("BASIC")) {
			terminal = new BasicTerminal(key, client);
		} else {
			terminal = new FancyTerminal(key, client);
		}
		_terminals.put(key, terminal);
		client.addTerminal(terminal);
	}
}
