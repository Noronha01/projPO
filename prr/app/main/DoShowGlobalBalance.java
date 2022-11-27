package prr.app.main;

import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Show global balance.
 */
class DoShowGlobalBalance extends Command<Network> {

  /**
   * @param receiver network manager
   */
  DoShowGlobalBalance(Network receiver) {
    super(Label.SHOW_GLOBAL_BALANCE, receiver);
  }


  /**
   *  Prints the global balance of the network on the display
   */
  @Override
  protected final void execute() throws CommandException {
    _display.popup(Message.globalPaymentsAndDebts(Math.round(_receiver.getPayments()),
            Math.round(_receiver.getDebts())));
  }
}
