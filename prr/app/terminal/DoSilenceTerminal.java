package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Silence the terminal.
 */
class DoSilenceTerminal extends TerminalCommand {

  /**
   * @param context network
   * @param terminal terminal
   */
  DoSilenceTerminal(Network context, Terminal terminal) {
    super(Label.MUTE_TERMINAL, context, terminal);
  }


  /**
   * Sends a terminal on silence, if already silence sends message on display
   */
  @Override
  protected final void execute() throws CommandException {
    if (_receiver.getStatus().equals("SILENCE"))
      _display.popup(Message.alreadySilent());
    else
      _receiver.setOnSilent();
  }
}
