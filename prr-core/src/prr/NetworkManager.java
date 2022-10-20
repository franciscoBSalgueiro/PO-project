package prr;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import prr.exceptions.ImportFileException;
import prr.exceptions.MissingFileAssociationException;
import prr.exceptions.UnavailableFileException;

//FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Manage access to network and implement load/save operations.
 */

public class NetworkManager {

	/** The network itself. */
	private Network _network = new Network();
	private String _filename;
	// FIXME addmore fields if needed

	public Network getNetwork() {
		return _network;
	}

	public String getFilename() {
		return _filename;
	}

	/**
	 * @param filename
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @return a new network recovered from file.
	 **/
	public void load(String filename) throws UnavailableFileException {
		_filename = filename;
		try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
			_network = (Network) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new UnavailableFileException(filename);
		}
	}

	/**
	 * @throws IOException
	 * @throws UnnamedDBException
	 */
	public void save() throws IOException, MissingFileAssociationException {
		if (_filename == null || _filename.equals(""))
			throw new MissingFileAssociationException();
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(_filename)))) {
			oos.writeObject(_network);
		}
	}

	/**
	 * Save contents in 'filename'
	 * 
	 * @param filename
	 * @throws IOException
	 * @throws UnnamedDBException
	 */
	public void saveAs(String filename) throws IOException, MissingFileAssociationException {
		_filename = filename;
		save();
	}

	/**
	 * @param filename
	 * @throws ImportFileException
	 */
	public void importFile(String filename) throws ImportFileException {
		_network.importFile(filename);
	}
}
