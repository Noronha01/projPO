package prr.app.client;

import prr.core.Network;

import prr.core.Notification;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import prr.app.exception.UnknownClientKeyException;

import java.util.List;


/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

  /**
   * User requests to show a specific client of the network
   * @param receiver network
   */
  DoShowClient(Network receiver) {
    super(Label.SHOW_CLIENT, receiver);
    addStringField("id", Message.key());
  }


  /**
   *  Prints the string that represents a client on the display
   * @throws UnknownClientKeyException if there's not a client with a certain id
   */
  @Override
  protected final void execute() throws CommandException {
    String _id = stringField("id");
    if(_receiver.findClientId(_id)) {
      _display.addLine(_receiver.getClient(_id).toString());
      List<Notification> _notifications = _receiver.getClient(_id).getNotifications();
      if (_notifications != null) {
        for (Notification not : _notifications)
          _display.addLine(not.toString());
        _receiver.getClient(_id).clearNotifications();
      }
    }
    else
      throw new UnknownClientKeyException(_id);
    _display.display();
  }
}
