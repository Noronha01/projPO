package prr.app.client;

import prr.core.Client;
import prr.core.Network;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;


/**
 * Show all clients.
 */
class DoShowAllClients extends Command<Network> {

  /**
   * @param receiver network
   */
  DoShowAllClients(Network receiver) {
    super(Label.SHOW_ALL_CLIENTS, receiver);
  }


  /**
   * Prints the strings that represent all the clients on the display
   */
  @Override
  protected final void execute() throws CommandException {
    List<Client> _clients = _receiver.getSortedClients();
    for(Client client : _clients)
      _display.addLine(client.toString());
    _display.display();
  }
}
