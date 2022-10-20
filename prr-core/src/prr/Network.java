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
	private int _uuid = 0;

	private int getUUID() {
		return ++_uuid;
	}

	// FIXME define attributes
	// FIXME define contructor(s)
	// FIXME define methods

	/**
	 * Read text input file and create corresponding entities.
	 * 
	 * @param filename name of the text input file
	 * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException                if there is an IO erro while processing
	 *                                    the text file
	 */
	void importFile(String filename) throws ImportFileException {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split("\\|");
				try {
					registerEntry(fields);
				} catch (DuplicateClientException | UnknownTerminalException | DuplicateTerminalException
						| UnknownClientException | UnrecognizedEntryException | InvalidTerminalException | UnknownTerminalTypeException e) {
					// never happens
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			throw new ImportFileException(filename);
		}
	}

	public void registerEntry(String... fields) throws DuplicateClientException, UnknownTerminalException,
			DuplicateTerminalException, UnrecognizedEntryException, UnknownClientException, InvalidTerminalException, UnknownTerminalTypeException {
		switch (fields[0]) {
			case "CLIENT" -> registerClient(fields[1], fields[2], Integer.parseInt(fields[3]));
			case "BASIC", "FANCY" -> registerTerminal(fields[1], fields[0], fields[2], fields[3]);
			case "FRIENDS" -> registerFriends(fields);
			default -> throw new UnrecognizedEntryException(fields[0]);
		}
	}

	public void registerFriends(String... fields) throws UnknownTerminalException {
		Terminal terminal = _terminals.get(fields[1]);
		if (terminal == null) {
			throw new UnknownTerminalException(fields[1]);
		}
		for (int i = 2; i < fields.length; i++) {
			Terminal friend = _terminals.get(fields[i]);
			if (friend == null) {
				throw new UnknownTerminalException(fields[i]);
			}
			terminal.addFriend(friend);
		}
	}

	public Collection<Client> getAllClients() {
		return Collections.unmodifiableCollection(_clients.values());
	}

	public Client getClient(String key) throws UnknownClientException {
		Client client = _clients.get(key);
		if (client == null) {
			throw new UnknownClientException(key);
		}
		return client;
	}

	public Collection<Terminal> getAllTerminals() {
		return Collections.unmodifiableCollection(_terminals.values());
	}

	public Collection<Terminal> getUnusedTerminals() {
		Collection<Terminal> unused = new ArrayList<>();
		for (Terminal terminal : _terminals.values()) {
			if (terminal.getCommunications().size() == 0) {
				unused.add(terminal);
			}
		}
		return unused;
	}

	public Terminal getTerminal(String key) {
		return _terminals.get(key);
	}

	public Collection<Communication> getAllCommunications() {
		return Collections.unmodifiableCollection(_communications.values());
	}

	public Communication getCommunication(int id) {
		return _communications.get(id);
	}

	public void registerClient(String key, String name, int taxId) throws DuplicateClientException {
		if (_clients.containsKey(key)) {
			throw new DuplicateClientException(key);
		}
		_clients.put(key, new Client(key, name, taxId));
	}

	// FIXME replace exception and catch it
	public void registerTerminal(String key, String type, String clientKey, String status)
			throws UnknownClientException, DuplicateTerminalException, InvalidTerminalException, UnknownTerminalTypeException {
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
