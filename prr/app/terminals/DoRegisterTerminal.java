package prr.app.terminals;

import prr.core.Network;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import prr.app.exception.DuplicateTerminalKeyException;
import prr.app.exception.InvalidTerminalKeyException;
import prr.app.exception.UnknownClientKeyException;
import prr.core.exception.UnrecognizedEntryException;


/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

  /**
   * User requests to register a terminal on the network
   * @param receiver network
   */
  DoRegisterTerminal(Network receiver) {
    super(Label.REGISTER_TERMINAL, receiver);
    addStringField("terminalId", Message.terminalKey());
    addOptionField("terminalType", Message.terminalType(), "BASIC", "FANCY");
    addStringField("clientId", Message.clientKey());
  }


  /**
   * Registers a terminal if there's not a terminal with same id already registed,
   * if the terminal id is valid and if the client id belongs to a registed client
   * @throws DuplicateTerminalKeyException if there's already a terminal with a certain id
   * @throws InvalidTerminalKeyException if the terminal id is not a string of 6 digits
   * @throws UnknownClientKeyException if there's not a clients registed with that id
   */
  @Override
  protected final void execute() throws CommandException {
    String _terminalId = stringField("terminalId");
    String _terminalType = optionField("terminalType");
    String _clientId = stringField("clientId");

    if (_receiver.getTerminal(_terminalId) != null)
      throw new DuplicateTerminalKeyException(_terminalId);
    else if(!_terminalId.matches("[0-9]+") || _terminalId.length() != 6)
      throw new InvalidTerminalKeyException(_terminalId);
    else if(_receiver.getClient(_clientId) == null)
      throw new UnknownClientKeyException(_clientId);
    try {
      _receiver.registerTerminal(_terminalType, _terminalId, _clientId);
    }
    catch (UnrecognizedEntryException e) {
    }
  }
}
