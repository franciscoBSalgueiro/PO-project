package prr.app.main;

import java.io.IOException;

import prr.NetworkManager;
import prr.app.exceptions.FileOpenFailedException;
import prr.exceptions.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
//FIXME add more imports if needed

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

	DoSaveFile(NetworkManager receiver) {
		super(Label.SAVE_FILE, receiver);
	}

	@Override
	protected final void execute() throws FileOpenFailedException {
		String filename = _receiver.getFilename();
		if (filename == null) {
			filename = Form.requestString(Prompt.newSaveAs());
		}
		try {
			_receiver.saveAs(filename);
		} catch (IOException | MissingFileAssociationException e) {
			throw new FileOpenFailedException(e);
		}
	}
}
