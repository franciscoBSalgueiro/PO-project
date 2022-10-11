package prr.app.main;

import prr.NetworkManager;
import prr.app.exceptions.FileOpenFailedException;
import prr.exceptions.UnavailableFileException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//Add more imports if needed

/**
 * Command to open a file.
 */
class DoOpenFile extends Command<NetworkManager> {

        DoOpenFile(NetworkManager receiver) {
                super(Label.OPEN_FILE, receiver);
                addStringField("filename", Prompt.openFile());
        }

        @Override
        protected final void execute() throws CommandException {
                String filename = stringField("filename");
                try {
                        _receiver.load(filename);
                } catch (UnavailableFileException e) {
                        throw new FileOpenFailedException(e);
                }
        }
}
