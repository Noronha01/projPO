package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import prr.core.exception.UnsupportedCallAtDestinationException;
import prr.core.exception.UnsupportedCallAtOriginException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

  DoStartInteractiveCommunication(Network context, Terminal terminal) {
    super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    addStringField("receiverId", Message.terminalKey());
    addOptionField("typeOfCom", Message.commType(), "VOICE", "VIDEO");
  }

  @Override
  protected final void execute() throws CommandException {
    String _receiverId = stringField("receiverId");
    String _typeOfCom = stringField("typeOfCom");

    if (_network.getTerminal(_receiverId) == null) {
      throw new UnknownTerminalKeyException(_receiverId);
    }

    Terminal _receiverOfCom = _network.getTerminal(_receiverId);

    if (_network.getTerminal(_receiverId).equals(_receiver))
      _display.popup(Message.destinationIsBusy(_receiverId));

    switch (_receiverOfCom.getStatus()) {
      case "BUSY":
        _display.popup(Message.destinationIsBusy(_receiverId));
        _receiverOfCom.addPendingNotifier(_receiver.getClient());
        break;
      case "SILENCE":
        _display.popup(Message.destinationIsSilent(_receiverId));
        _receiverOfCom.addPendingNotifier(_receiver.getClient());
        break;
      case "OFF":
        _display.popup(Message.destinationIsOff(_receiverId));
        _receiverOfCom.addPendingNotifier(_receiver.getClient());
        break;
      default:
        try {
          _network.startInteractiveCommunication(_receiver, _receiverId, _typeOfCom);
        } catch (UnsupportedCallAtOriginException e) {
          _display.popup(Message.unsupportedAtOrigin(_receiver.getID(), _typeOfCom));
        } catch (UnsupportedCallAtDestinationException e) {
          _display.popup(Message.unsupportedAtDestination(_receiverId, _typeOfCom));
        }
        break;
    }
  }
}

