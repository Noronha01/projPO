package prr.app.terminals;

import prr.core.Network;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import prr.app.exception.UnknownTerminalKeyException;


/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {


  /**
   * User requests to open the console menu of a terminal of the network
   * @param receiver network
   */
  DoOpenMenuTerminalConsole(Network receiver) {
    super(Label.OPEN_MENU_TERMINAL, receiver);
    addStringField("_id", Message.terminalKey());
  }


  /**
   * Opens the console menu of a certain terminal
   * @throws UnknownTerminalKeyException if there's not a terminal with a certain id
   */
  @Override
  protected final void execute() throws CommandException {
    String _id = stringField("_id");

    if(_receiver.getTerminal(_id) == null)
      throw new UnknownTerminalKeyException(_id);
    else
      (new prr.app.terminal.Menu(_receiver, _receiver.getTerminal(_id))).open();
  }
}
