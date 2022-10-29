package prr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import prr.clients.Client;
import prr.communications.Communication;
import prr.communications.InteractiveCommunication;
import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;
import prr.exceptions.DuplicateClientException;
import prr.exceptions.DuplicateTerminalException;
import prr.exceptions.ImportFileException;
import prr.exceptions.InvalidTerminalException;
import prr.exceptions.NotificationsAlreadyDisabledException;
import prr.exceptions.NotificationsAlreadyEnabledException;
import prr.exceptions.TerminalAlreadyIdleException;
import prr.exceptions.TerminalAlreadyOffException;
import prr.exceptions.TerminalAlreadySilentException;
import prr.exceptions.UnknownClientException;
import prr.exceptions.UnknownTerminalException;
import prr.exceptions.UnknownTerminalTypeException;
import prr.exceptions.UnrecognizedEntryException;
import prr.terminals.BasicTerminal;
import prr.terminals.FancyTerminal;
import prr.terminals.Terminal;

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

	public void enableNotifications(String key) throws UnknownClientException, NotificationsAlreadyEnabledException {
		Client client = getClient(key);
		client.enableNotifications();
	}

	public void disableNotifications(String key) throws UnknownClientException, NotificationsAlreadyDisabledException {
		Client client = getClient(key);
		client.disableNotifications();
	}

	public Collection<Communication> getCommunicationsFromClient(String key) throws UnknownClientException {
		Client client = getClient(key);
		return client.getOutComms();
	}

	public Collection<Communication> getCommunicationsToClient(String key) throws UnknownClientException {
		Client client = getClient(key);
		return client.getInComms();
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
			String[] friends = fields[2].split(",");
			for (String friend : friends) {
				terminal.addFriend(this, friend);
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
		return _terminals.values().stream().filter(t -> t.getInComms().size() == 0).collect(Collectors.toList());
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

	public TextCommunication addTextCommunication(Terminal origin, Client originClient, Terminal destination,
			String message) {
		int key = getUUID();
		TextCommunication communication = new TextCommunication(key, origin, destination, message);
		long cost = originClient.getCost(communication);
		communication.setCost(cost);
		_communications.put(key, communication);
		return communication;
	}

	public InteractiveCommunication addInteractiveCommunication(Terminal origin, Terminal destination, String type) {
		InteractiveCommunication communication;
		int key = getUUID();
		switch (type) {
			case "VOICE" -> {
				communication = new VoiceCommunication(key, origin, destination);
			}
			case "VIDEO" -> {
				communication = new VideoCommunication(key, origin, destination);
			}
			// FIXME add real exception
			default -> throw new IllegalArgumentException("Unknown communication type: " + type);
		}
		_communications.put(key, communication);
		return communication;
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
		Client client = getClient(clientKey);
		if (key.length() != 6 || !key.matches("\\d+")) {
			throw new InvalidTerminalException(key);
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
		try {

			switch (status) {
				case "ON" -> terminal.turnOn();
				case "OFF" -> terminal.turnOff();
				case "SILENCE" -> terminal.silence();
				default -> throw new InvalidTerminalException(key);
			}
		} catch (TerminalAlreadyIdleException | TerminalAlreadyOffException | TerminalAlreadySilentException e) {
		}
		_terminals.put(key, terminal);
		client.addTerminal(terminal);
	}
}
