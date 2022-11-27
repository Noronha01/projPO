package prr.app.client;

import prr.core.Network;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import prr.app.exception.DuplicateClientKeyException;


/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

  /**
   * User requests to register a client on the network
   * @param receiver network
   */
  DoRegisterClient(Network receiver) {
    super(Label.REGISTER_CLIENT, receiver);
    addStringField("id", Message.key());
    addStringField("name", Message.name());
    addIntegerField("taxId", Message.taxId());
  }


  /**
   * Registers a client if there's not a client with same id already registed
   * @throws DuplicateClientKeyException if there's already a client with a certain id
   */
  @Override
  protected final void execute() throws CommandException {
    String _id = stringField("id");
    String _name = stringField("name");
    Integer _taxId = integerField("taxId");

    if (_receiver.findClientId(_id))
      throw new DuplicateClientKeyException(_id);
    else
      _receiver.registerClient(_id, _name, _taxId);
  }
}
