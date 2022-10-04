package prr;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import prr.app.exceptions.DuplicateClientKeyException;
import prr.clients.Client;
import prr.exceptions.UnrecognizedEntryException;
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
	ArrayList<Terminal> _terminals;

	public Network() {
		_clients = new HashMap<String, Client>();
		_terminals = new ArrayList<Terminal>();
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

	public Client getClient(String id) {
		return _clients.get(id);
	}

	public ArrayList<Terminal> getAllTerminals() {
		return _terminals;
	}

	public void registerClient(String id, String name, int taxId) throws CommandException {
		if (_clients.containsKey(id)) {
			throw new DuplicateClientKeyException(id);
		}
		_clients.put(id, new Client(id, name, taxId));
	}
}
