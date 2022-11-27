package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Turn on the terminal.
 */
class DoTurnOnTerminal extends TerminalCommand {

  /**
   * @param context network
   * @param terminal terminal
   */
  DoTurnOnTerminal(Network context, Terminal terminal) {
    super(Label.POWER_ON, context, terminal);
  }


  /**
   * Turns on the terminal, if already silence sends message on display
   */
  @Override
  protected final void execute() throws CommandException {
    if (_receiver.getStatus().equals("IDLE"))
      _display.popup(Message.alreadyOn());
    else
      _receiver.turnOn();
  }
}
