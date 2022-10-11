package prr.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import prr.NetworkManager;
import prr.exceptions.MissingFileAssociationException;
import pt.tecnico.uilib.menus.Command;
//FIXME add more imports if needed

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

	DoSaveFile(NetworkManager receiver) {
		super(Label.SAVE_FILE, receiver);
		if (_receiver.getFilename() == null)
			addStringField("filename", Prompt.newSaveAs());
	}

	@Override
	protected final void execute() {
		String filename = stringField("filename");
		try {
			_receiver.saveAs(filename);
		} catch (FileNotFoundException e) {
			// FIXME implement error handling
		} catch (MissingFileAssociationException e) {
			// FIXME implement error handling
		} catch (IOException e) {
			// FIXME implement error handling
		}
	}
}
