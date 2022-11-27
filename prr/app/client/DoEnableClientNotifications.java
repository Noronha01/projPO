package prr.app.client;

import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Enable client's notifications.
 */
class DoEnableClientNotifications extends Command<Network> {

  /**
   * User requests to enable a client notifications
   * @param receiver network
   */
  DoEnableClientNotifications(Network receiver) {
    super(Label.ENABLE_CLIENT_NOTIFICATIONS, receiver);
    addStringField("id", Message.key());
  }


  /**
   * Enables client notifications if a client with a certain id exists
   * @throws UnknownClientKeyException if there's no client with a certain id
   */
  @Override
  protected final void execute() throws CommandException {
    String _id = stringField("id");
    if (!_receiver.findClientId(_id))
      throw new UnknownClientKeyException(_id);
    else if (_receiver.getClient(_id).getNotificationsSwitch()){
      _display.addLine(Message.clientNotificationsAlreadyEnabled());
      _display.display();
    }
    else
      _receiver.getClient(_id).turnOnNotificationSwitch();
  }
}
