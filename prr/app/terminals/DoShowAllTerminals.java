package prr.app.terminals;

import java.util.List;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Show all terminals.
 */
class DoShowAllTerminals extends Command<Network> {

  /**
   * Displays all the terminals registed on the network
   * @param receiver network
   */
  DoShowAllTerminals(Network receiver) {
    super(Label.SHOW_ALL_TERMINALS, receiver);
  }


  /**
   * Prints the strings that represent all the terminals of the network on the display
   */
  @Override
  protected final void execute() throws CommandException {
    List<Terminal> _terminals = _receiver.getSortedTerminals();
    for (Terminal terminal : _terminals)
      _display.addLine(terminal.toString());
    _display.display();
  }
}
