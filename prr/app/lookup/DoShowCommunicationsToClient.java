package prr.app.lookup;

import prr.core.Communication;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;


/**
 * Show communications to a client.
 */
class DoShowCommunicationsToClient extends Command<Network> {

  /**
   * User requests to show the communications received by a specific client of the network
   * @param receiver network
   */
  DoShowCommunicationsToClient(Network receiver) {
    super(Label.SHOW_COMMUNICATIONS_TO_CLIENT, receiver);
    addStringField("id", Message.clientKey());
  }


  /**
   *  Prints the strings that represent all the communications received by a client on the display
   */
  @Override
  protected final void execute() throws CommandException {
    String _id = stringField("id");
    List<Communication> receivedCom =  _receiver.getClientReceivedComs(_id);
    for (Communication com: receivedCom)
      _display.addLine(com.toString());
    _display.display();
  }
}
