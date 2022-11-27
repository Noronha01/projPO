package prr.app.main;

import prr.core.NetworkManager;
import prr.app.exception.FileOpenFailedException;
import prr.core.exception.UnavailableFileException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Command to open a file.
 */
class DoOpenFile extends Command<NetworkManager> {
  private String _filename;


  /**
   *
   * @param receiver network manager
   */
  DoOpenFile(NetworkManager receiver) {
    super(Label.OPEN_FILE, receiver);
    addStringField("filename", Message.openFile());
  }
  
  @Override
  protected final void execute() throws CommandException {
    try {
      _filename = stringField("filename");
      _receiver.load(_filename);
    }
    catch (UnavailableFileException e) {
      throw new FileOpenFailedException(e);
    }

  }
}
