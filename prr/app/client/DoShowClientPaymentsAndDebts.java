package prr.app.client;

import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

  /**
   * User requests to show the payments and debts of a specific client of the network
   * @param receiver network
   */
  DoShowClientPaymentsAndDebts(Network receiver) {
    super(Label.SHOW_CLIENT_BALANCE, receiver);
    addStringField("id", Message.key());
  }


  /**
   *  Prints the payments and debts of a client on the display
   * @throws UnknownClientKeyException if there's not a client with a certain id
   */
  @Override
  protected final void execute() throws CommandException {
    String _id = stringField("id");
    if (!_receiver.findClientId(_id))
      throw new UnknownClientKeyException(_id);
    else
      _display.popup(Message.clientPaymentsAndDebts("'" + _id + "'",
              Math.round(_receiver.getClient(_id).getPayments()),
              Math.round(_receiver.getClient(_id).getDebts())));
  }
}
