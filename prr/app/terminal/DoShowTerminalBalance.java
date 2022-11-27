package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Show balance.
 */
class DoShowTerminalBalance extends TerminalCommand {

  /**
   * @param context network
   * @param terminal terminal
   */
  DoShowTerminalBalance(Network context, Terminal terminal) {
    super(Label.SHOW_BALANCE, context, terminal);
  }


  /**
   * Prints the terminal's balance on the display
   */
  @Override
  protected final void execute() throws CommandException {
    _display.popup(Message.terminalPaymentsAndDebts(_receiver.getID(), (long)_receiver.getPayments(), (long)_receiver.getDebts()));
  }
}
