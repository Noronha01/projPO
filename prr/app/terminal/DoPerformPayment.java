package prr.app.terminal;

import prr.core.Communication;
import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {

  /**
   * User requests to perform payment
   * @param context network
   * @param terminal terminal
   */
  DoPerformPayment(Network context, Terminal terminal) {
    super(Label.PERFORM_PAYMENT, context, terminal);
    addIntegerField("commKey", Message.commKey());
  }


  /**
   *  Performs payment of a certain communication
   */
  @Override
  protected final void execute() throws CommandException {
    int _commKey = integerField("commKey");
    Communication com = _receiver.getSentCommunication(_commKey);
    if (com != null && !com.isPaid() && !com.getOnGoing()) {
      double comPrice = com.getCost();
      _receiver.performPayment(comPrice, _receiver.getSentCommunication(_commKey));
      _network.incrementDebts(-comPrice);
      _network.incrementPayments(comPrice);
    }
    else {
      _display.popup(Message.invalidCommunication());
    }
  }
}
