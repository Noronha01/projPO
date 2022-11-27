package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;

import pt.tecnico.uilib.menus.CommandException;

import prr.app.exception.UnknownClientKeyException;


/**
 * Remove friend.
 */
class DoRemoveFriend extends TerminalCommand {

  /**
   * User requests to remove a friend from the terminal's friends list
   * @param context network
   * @param terminal terminal
   */
  DoRemoveFriend(Network context, Terminal terminal) {
    super(Label.REMOVE_FRIEND, context, terminal);
    addStringField("_friendId", Message.terminalKey());
  }


  /**
   * Removes a friend from the list of friends of the terminal
   * @throws UnknownClientKeyException if there's not a client with a certain id
   */
  @Override
  protected final void execute() throws CommandException {
    String _friendId = stringField("_friendId");

    if(_network.getTerminal(_friendId) == null)
      throw new UnknownClientKeyException(_friendId);

    else if(_receiver.getFriend(_friendId) != null)
      _receiver.removeFriend(_network.getTerminal(_friendId));

  }
}
