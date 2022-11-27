package prr.app.client;

import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Disable client's notifications.
 */
class DoDisableClientNotifications extends Command<Network> {

  /**
   * User requests to disable a client's notifications
   * @param receiver network
   */
  DoDisableClientNotifications(Network receiver) {
    super(Label.DISABLE_CLIENT_NOTIFICATIONS, receiver);
    addStringField("id", Message.key());
  }


  /**
   * Disables client's notifications if a client with a certain id exists
   * @throws UnknownClientKeyException if there's no client with a certain id
   */
  @Override
  protected final void execute() throws CommandException {
    String _id = stringField("id");
    if (!_receiver.findClientId(_id))
      throw new UnknownClientKeyException(_id);
    else if(!_receiver.getClient(_id).getNotificationsSwitch()){
      _display.addLine(Message.clientNotificationsAlreadyDisabled());
      _display.display();
    }
    else
      _receiver.getClient(_id).turnOffNotificationSwitch();

  }
}
