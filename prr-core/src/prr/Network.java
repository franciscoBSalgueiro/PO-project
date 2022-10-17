package prr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import prr.clients.Client;
import prr.communications.Communication;
import prr.exceptions.DuplicateClientException;
import prr.exceptions.DuplicateTerminalException;
import prr.exceptions.ImportFileException;
import prr.exceptions.UnknownClientException;
import prr.exceptions.UnknownTerminalException;
import prr.exceptions.UnrecognizedEntryException;
import prr.terminals.BasicTerminal;
import prr.terminals.FancyTerminal;
import prr.terminals.IdleStatus;
import prr.terminals.OffStatus;
import prr.terminals.Terminal;
import prr.terminals.TerminalStatus;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private Map<String, Client> _clients = new TreeMap<>();
	private Map<String, Terminal> _terminals = new TreeMap<>();
	private List<Communication> _communications = new ArrayList<>();

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
	void importFile(String filename) throws UnrecognizedEntryException, IOException, ImportFileException {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split("\\|");
				try {
					registerEntry(fields);
				} catch (DuplicateClientException | UnknownTerminalException | DuplicateTerminalException
						| UnknownClientException | UnrecognizedEntryException e) {
					// DAVID should not happen
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			throw new ImportFileException(filename);
		}
	}

	public void registerEntry(String... fields) throws DuplicateClientException, UnknownTerminalException,
			DuplicateTerminalException, UnknownClientException, UnrecognizedEntryException {
		switch (fields[0]) {
			case "CLIENT" -> registerClient(fields[1], fields[2], Integer.parseInt(fields[3]));
			case "BASIC", "FANCY" -> registerTerminal(fields[1], fields[0], fields[2]);
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
		return _communications;
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
	// acho q n pode ser criado com estado != de idle
	public void registerTerminal(String key, String type, String clientKey)
			throws UnknownClientException, DuplicateTerminalException {
		Terminal terminal;
		Client client = _clients.get(clientKey);
		if (client == null) {
			throw new UnknownClientException(clientKey);
		}
		if (_terminals.containsKey(key)) {
			throw new DuplicateTerminalException(key);
		}
//		if (status.equals("ON")) {
//          terminal.turnOn();
//      } else  {
		//  if (status.equals("OFF")) {
//          terminal.turnOff();
//      }
		if (type.equals("BASIC")) {
			terminal = new BasicTerminal(key, client);
		} else {
			terminal = new FancyTerminal(key, client);
		}
		_terminals.put(key, terminal);
		client.addTerminal(terminal);
	}
}
