package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;

import pt.tecnico.uilib.menus.CommandException;
import prr.app.exception.UnknownTerminalKeyException;


/**
 * Add a friend.
 */
class DoAddFriend extends TerminalCommand {

  /**
   * User requests to add a friend to the terminal's friends list
   * @param context network
   * @param terminal terminal
   */
  DoAddFriend(Network context, Terminal terminal) {
    super(Label.ADD_FRIEND, context, terminal);
    addStringField("_friendId", Message.terminalKey());
  }


  /**
   *  Adds the friend to the list of friends of the terminal
   * @throws UnknownTerminalKeyException if there's not a client with a certain id
   */
  @Override
  protected final void execute() throws CommandException {
    String _friendId = stringField("_friendId");
    if(_network.getTerminal(_friendId) == null)
      throw new UnknownTerminalKeyException(_friendId);
    else if(_receiver.getFriend(_friendId) == null && !_receiver.getID().equals(_friendId))
      _receiver.addFriend(_network.getTerminal(_friendId));
  }
}
