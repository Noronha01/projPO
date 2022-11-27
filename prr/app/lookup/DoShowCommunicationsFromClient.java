package prr.app.lookup;

import prr.core.Communication;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;


/**
 * Show communications from a client.
 */
class DoShowCommunicationsFromClient extends Command<Network> {

  /**
   * User requests to show the communications sent by a specific client of the network
   * @param receiver network
   */
  DoShowCommunicationsFromClient(Network receiver) {
    super(Label.SHOW_COMMUNICATIONS_FROM_CLIENT, receiver);
    addStringField("id", Message.clientKey());
  }


  /**
   *  Prints the strings that represent all the communications sent by a client on the display
   */
  @Override
  protected final void execute() throws CommandException {
    String _id = stringField("id");
    List<Communication> sendedCom =  _receiver.getClientSentComs(_id);
    for (Communication com: sendedCom)
      _display.addLine(com.toString());
    _display.display();
  }
}
