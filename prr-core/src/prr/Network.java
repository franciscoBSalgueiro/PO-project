package prr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import prr.clients.Client;
import prr.communications.Communication;
import prr.exceptions.DuplicateClientException;
import prr.exceptions.DuplicateTerminalException;
import prr.exceptions.ImportFileException;
import prr.exceptions.InvalidTerminalException;
import prr.exceptions.UnknownClientException;
import prr.exceptions.UnknownTerminalException;
import prr.exceptions.UnknownTerminalTypeException;
import prr.exceptions.UnrecognizedEntryException;
import prr.terminals.BasicTerminal;
import prr.terminals.FancyTerminal;
import prr.terminals.Terminal;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private Map<String, Client> _clients = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
	private Map<String, Terminal> _terminals = new TreeMap<>();
	private Map<Integer, Communication> _communications = new TreeMap<>();
	private int _uuid = 0; // unique identifier for communications

	private int getUUID() {
		return ++_uuid;
	}

	/**
	 * Read text input file and create corresponding entities.
	 * 
	 * @param filename name of the text input file
	 * @throws ImportFileException
	 */
	void importFile(String filename) throws ImportFileException {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split("\\|");
				try {
					registerEntry(fields);
				} catch (DuplicateClientException | UnknownTerminalException | DuplicateTerminalException
						| UnknownClientException | UnrecognizedEntryException | InvalidTerminalException
						| UnknownTerminalTypeException e) {
					// never happens
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			throw new ImportFileException(filename);
		}
	}

	/**
	 * Register an entry in the network
	 * 
	 * @param fields array of fields of the entry
	 * @throws UnrecognizedEntryException
	 * @throws DuplicateClientException
	 * @throws UnknownTerminalException
	 * @throws DuplicateTerminalException
	 * @throws UnknownClientException
	 * @throws InvalidTerminalException
	 * @throws UnknownTerminalTypeException
	 */
	public void registerEntry(String... fields) throws DuplicateClientException, UnknownTerminalException,
			DuplicateTerminalException, UnrecognizedEntryException, UnknownClientException, InvalidTerminalException,
			UnknownTerminalTypeException {
		switch (fields[0]) {
			case "CLIENT" -> registerClient(fields[1], fields[2], Integer.parseInt(fields[3]));
			case "BASIC", "FANCY" -> registerTerminal(fields[1], fields[0], fields[2], fields[3]);
			case "FRIENDS" -> registerFriends(fields);
			default -> throw new UnrecognizedEntryException(fields[0]);
		}
	}

	/**
	 * Register friends for a client
	 * 
	 * @param fields
	 * @throws UnknownTerminalException
	 */
	public void registerFriends(String... fields) throws UnknownTerminalException {
		try {
			Terminal terminal = getTerminal(fields[1]);
			for (int i = 2; i < fields.length; i++) {
				Terminal friend = getTerminal(fields[i]);
				terminal.addFriend(friend);
			}
		} catch (UnknownTerminalException e) {
			throw new UnknownTerminalException(e.getKey());
		}
		
	}

	/**
	 * Return all the clients as an unmodifiable collection.
	 * 
	 * @return a collection with all the clients.
	 */
	public Collection<Client> getAllClients() {
		return Collections.unmodifiableCollection(_clients.values());
	}

	/**
	 * @param key the client's key
	 * @return the client with the given key
	 * @throws UnknownClientException
	 */
	public Client getClient(String key) throws UnknownClientException {
		Client client = _clients.get(key);
		if (client == null) {
			throw new UnknownClientException(key);
		}
		return client;
	}

	/**
	 * Return all the terminals as an unmodifiable collection.
	 * 
	 * @return a collection with all the terminals.
	 */
	public Collection<Terminal> getAllTerminals() {
		return Collections.unmodifiableCollection(_terminals.values());
	}

	/**
	 * Return all the terminals that don't have communications as an unmodifiable
	 * collection.
	 * 
	 * @return a collection with all the terminals that don't have communications.
	 */
	public Collection<Terminal> getUnusedTerminals() {
		Collection<Terminal> unused = new ArrayList<>();
		for (Terminal terminal : _terminals.values()) {
			if (terminal.getCommunications().size() == 0) {
				unused.add(terminal);
			}
		}
		return unused;
	}

	/**
	 * @param key the client's key
	 * @return the client with the given key
	 * @throws UnknownTerminalException
	 */
	public Terminal getTerminal(String key) throws UnknownTerminalException {
		Terminal terminal = _terminals.get(key);
		if (terminal == null) {
			throw new UnknownTerminalException(key);
		}
		return _terminals.get(key);
	}

	/**
	 * Return all the communications as an unmodifiable collection.
	 * 
	 * @return a collection with all the communications.
	 */
	public Collection<Communication> getAllCommunications() {
		return Collections.unmodifiableCollection(_communications.values());
	}

	public Communication getCommunication(int id) {
		// FIXME add exception if communication does not exist
		return _communications.get(id);
	}

	/**
	 * @param key
	 * @param name
	 * @param taxId
	 * @throws DuplicateClientException
	 */
	public void registerClient(String key, String name, int taxId) throws DuplicateClientException {
		if (_clients.containsKey(key)) {
			throw new DuplicateClientException(key);
		}
		_clients.put(key, new Client(key, name, taxId));
	}

	/**
	 * @param key
	 * @param type
	 * @param clientKey
	 * @param status
	 * @throws UnknownClientException
	 * @throws DuplicateTerminalException
	 * @throws InvalidTerminalException
	 * @throws UnknownTerminalTypeException
	 */
	public void registerTerminal(String key, String type, String clientKey, String status)
			throws UnknownClientException, DuplicateTerminalException, InvalidTerminalException,
			UnknownTerminalTypeException {
		Terminal terminal;
		Client client = _clients.get(clientKey);
		if (key.length() != 6 || !key.matches("\\d+")) {
			throw new InvalidTerminalException(key);
		}
		if (client == null) {
			throw new UnknownClientException(clientKey);
		}
		if (_terminals.containsKey(key)) {
			throw new DuplicateTerminalException(key);
		}
		if (type.equals("BASIC")) {
			terminal = new BasicTerminal(key, client);
		} else if (type.equals("FANCY")) {
			terminal = new FancyTerminal(key, client);
		} else {
			throw new UnknownTerminalTypeException(type);
		}
		switch (status) {
			case "ON" -> terminal.turnOn();
			case "OFF" -> terminal.turnOff();
			case "SILENCE" -> terminal.silence();
			default -> throw new InvalidTerminalException(key);
		}
		_terminals.put(key, terminal);
		client.addTerminal(terminal);
	}
}
