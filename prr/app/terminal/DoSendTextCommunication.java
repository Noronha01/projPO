package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

  /**
   * User requests to send text communication
   * @param context network
   * @param terminal terminal
   */
  DoSendTextCommunication(Network context, Terminal terminal) {
    super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    addStringField("receiverId", Message.terminalKey());
    addStringField("message", Message.textMessage());
  }


  /**
   * Sends a text communication to a certain terminal if possible
   */
  @Override
  protected final void execute() throws CommandException {
    String _receiverId = stringField("receiverId");
    String _message = stringField("message");
    if (_network.getTerminal(_receiverId) == null) {
      throw new UnknownTerminalKeyException(_receiverId);
    }
    if(_network.getTerminal(_receiverId).getStatus().equals("OFF")) {
      _network.getTerminal(_receiverId).addPendingNotifier(_receiver.getClient());;
      _display.popup(Message.destinationIsOff(_receiverId));
    }
    else
      _network.sendTextCommunication(_receiver, _receiverId, _message);
  }
} 
