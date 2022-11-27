package prr.app.lookup;

import prr.core.Network;
import prr.core.Terminal;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;


/**
 * Show unused terminals (without communications).
 */
class DoShowUnusedTerminals extends Command<Network> {


  /**
   * @param receiver network
   */
  DoShowUnusedTerminals(Network receiver) {
    super(Label.SHOW_UNUSED_TERMINALS, receiver);
  }


  /**
   *  Prints the strings that represent all the terminals without communications on the display
   */
  @Override
  protected final void execute() throws CommandException {
    List<Terminal> _terminals = _receiver.getSortedTerminals();
    for (Terminal terminal: _terminals)
      if (terminal.getCommunicationsSent().isEmpty() && terminal.getCommunicationsReceived().isEmpty())
        _display.addLine(terminal.toString());
    _display.display();;

  }
}
