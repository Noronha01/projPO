package prr.app.lookup;

import prr.app.exception.UnknownClientKeyException;
import prr.core.Communication;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import java.util.List;


/**
 * Command for showing all communications.
 */
class DoShowAllCommunications extends Command<Network> {

  /**
   * @param receiver network
   */
  DoShowAllCommunications(Network receiver) {
    super(Label.SHOW_ALL_COMMUNICATIONS, receiver);
  }


  /**
   *  Prints all the strings that represent the communications on the display
   */
  @Override
  protected final void execute() throws CommandException {
    List<Communication> _communications = _receiver.getAllCommunications();
    for(Communication com: _communications)
      _display.addLine(com.toString());
    _display.display();
  }
}
