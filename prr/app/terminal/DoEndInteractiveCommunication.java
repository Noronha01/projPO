package prr.app.terminal;

import prr.core.Communication;
import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for ending communication.
 */
class DoEndInteractiveCommunication extends TerminalCommand {

  /**
   * User requests to end communication
   * @param context network
   * @param terminal terminal
   */
  DoEndInteractiveCommunication(Network context, Terminal terminal) {
    super(Label.END_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canEndCurrentCommunication());
    addIntegerField("duration", Message.duration());
  }


  /**
   *  Ends the current interactive communication of the terminal
   */
  @Override
  protected final void execute() throws CommandException {
    int _duration = integerField("duration");
    Communication com = _network.getCommunication(_receiver.getOnGoingComId());
    _network.endCommunication(_receiver, _duration);
    double comCost = com.getCost();
    _display.popup(Message.communicationCost(Math.round(comCost)));
  }
}
