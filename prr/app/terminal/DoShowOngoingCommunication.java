package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Command for showing the ongoing communication.
 */
class DoShowOngoingCommunication extends TerminalCommand {

  /**
   * @param context network
   * @param terminal terminal
   */
  DoShowOngoingCommunication(Network context, Terminal terminal) {
    super(Label.SHOW_ONGOING_COMMUNICATION, context, terminal);
  }


  /**
   * Prints (if it exists) the string that represents the ongoing communication of the terminal on the display
   */
  @Override
  protected final void execute() throws CommandException {
    if (_receiver.getOnGoingComId() == 0)
      _display.popup(Message.noOngoingCommunication());
    else {
      _display.addLine(_network.getCommunication(_receiver.getOnGoingComId()).toString());
      _display.display();
    }
  }
}
