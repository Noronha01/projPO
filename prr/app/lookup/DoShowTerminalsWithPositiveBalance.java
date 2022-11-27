package prr.app.lookup;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;


/**
 * Show terminals with positive balance.
 */
class DoShowTerminalsWithPositiveBalance extends Command<Network> {

  /**
   * @param receiver network
   */
  DoShowTerminalsWithPositiveBalance(Network receiver) {
    super(Label.SHOW_TERMINALS_WITH_POSITIVE_BALANCE, receiver);
  }


  /**
   *  Prints the strings that represent all the terminals with positive balance on the display
   */
  @Override
  protected final void execute() throws CommandException {
    List<Terminal> _terminals = _receiver.getSortedTerminals();
    for (Terminal terminal: _terminals)
      if(terminal.getPayments() - terminal.getDebts() > 0)
        _display.addLine(terminal.toString());
    _display.display();
  }
}
