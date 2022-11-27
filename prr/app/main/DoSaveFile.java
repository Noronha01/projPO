package prr.app.main;

import prr.app.exception.FileOpenFailedException;
import prr.core.NetworkManager;
import prr.core.exception.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import java.io.IOException;

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

  /**
   *
   * @param receiver network manager
   */
  DoSaveFile(NetworkManager receiver) {
    super(Label.SAVE_FILE, receiver);
  }

  /**
   *
   * @throws FileOpenFailedException  if fails to access the file to save
   */
  @Override
  protected final void execute() throws FileOpenFailedException {
    try {
      _receiver.save();
    } catch (MissingFileAssociationException mfae) {
      Form form = new Form();
      form.addStringField("filename", Message.newSaveAs());
      form.parse();
      String _filename = form.stringField("filename");
      try {
        _receiver.saveAs(_filename);
      } catch (MissingFileAssociationException | IOException e) {
        throw new FileOpenFailedException(e);
      }
    } catch (IOException e) {
      throw new FileOpenFailedException(e);
    }
  }
}
