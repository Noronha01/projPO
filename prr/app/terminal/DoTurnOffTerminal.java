package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Turn off the terminal.
 */
class DoTurnOffTerminal extends TerminalCommand {

  /**
   * @param context network
   * @param terminal terminal
   */
  DoTurnOffTerminal(Network context, Terminal terminal) {
    super(Label.POWER_OFF, context, terminal);
  }


  /**
   * Turns of the terminal, if already silence sends message on display
   */
  @Override
  protected final void execute() throws CommandException {
    if (_receiver.getStatus().equals("OFF"))
      _display.popup(Message.alreadyOff());
    else if (!_receiver.getStatus().equals("BUSY"))
      _receiver.turnOff();
  }
}
