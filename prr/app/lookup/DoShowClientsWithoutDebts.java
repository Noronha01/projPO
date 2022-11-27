package prr.app.lookup;

import prr.core.Client;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;


/**
 * Show clients with positive balance.
 */
class DoShowClientsWithoutDebts extends Command<Network> {

  /**
   * @param receiver network
   */
  DoShowClientsWithoutDebts(Network receiver) {
    super(Label.SHOW_CLIENTS_WITHOUT_DEBTS, receiver);
  }


  /**
   *  Prints the strings that represent all the clients without debts on the display
   */
  @Override
  protected final void execute() throws CommandException {
    List<Client> _clients = _receiver.getSortedClients();
    for(Client client : _clients)
      if (client.getDebts() == 0)
        _display.addLine(client.toString());
    _display.display();
  }
}
