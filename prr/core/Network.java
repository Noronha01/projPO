package prr.core;

import java.io.Serializable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import prr.core.exception.UnrecognizedEntryException;
import prr.core.exception.UnsupportedCallAtDestinationException;
import prr.core.exception.UnsupportedCallAtOriginException;

import static java.util.Comparator.comparingDouble;


/**
 * Class Network implements a network.
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  private Map<String, Terminal> _terminals = new HashMap<>(); //all the terminals of the network
  private Map<String, Client> _clients = new HashMap<>(); //all the clients of the network
  private Map<Integer, Communication> _communications = new HashMap<>();
  private Map<String, TariffPlan> _tariffPlans = new HashMap<>();

  private int _comCount;

  private double _payments;

  private double _debts;



  Network() {
    tariffPlansInit();
  }


  /**
   * Read text input file and create corresponding domain entities.
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   */
  void importFile(String filename) throws UnrecognizedEntryException, IOException  {
    Parser parser = new Parser(this);
    parser.parseFile(filename);
  }


  /**
   * Creates a basic tariff plan and puts it in the tariffPlans hash map
   */
  private void tariffPlansInit() {
    BasicPlan basicPlan = new BasicPlan();
    _tariffPlans.put(basicPlan.getName(), basicPlan);
  }


  /**
   * Returns the network's payments
   */
  public double getPayments() { return _payments; }


  /**
   * Returns the network's debts
   */
  public double getDebts() { return _debts; }


  /**
   * Increments network's paymemnts
   * @param value (value to increment to the network's payments)
   */
  public void incrementPayments(double value) {
    _payments += value;
  }


  /**
   * Increments network's debts
   * @param value (value to increment to the network's debts)
   */
  public void incrementDebts(double value) {
    _debts += value;
  }


  // ---------------------------------- Terminal ---------------------------------- //


  /**
   * Registers a terminal in the network terminals array
   * @param terminalType (BASIC or FANCY),
   *                     idTerminal (6 digit string that identifies a terminal)
   *                     and idClient (string that identifies a client)
   * @throws UnrecognizedEntryException if some entry is not correct
   */
  public Terminal registerTerminal(String terminalType, String idTerminal, String idClient) throws UnrecognizedEntryException {
    Terminal terminal = null;
    Client client = getClient(idClient);
    if(terminalType.equals("BASIC")) {
      terminal = new Basic(idTerminal, client);
    } else if (terminalType.equals("FANCY")) {
      terminal = new Fancy(idTerminal, client);
    } else
      throw new UnrecognizedEntryException("Invalid terminal type: " + terminalType);
    _terminals.put(idTerminal, terminal);
    client.addTerminal(terminal);
    return terminal;
  }


  /**
   * Returns the terminal with a certain id
   * @param idTerminal (6 digit string that identifies a terminal)
   */
  public Terminal getTerminal(String idTerminal) {
    return _terminals.get(idTerminal);
  }


  /**
   * Returns the terminals network list
   */
  public List<Terminal> getTerminals() {
    return new ArrayList<Terminal>(_terminals.values());
  }


  /**
   * Returns the terminals (sorted by ids) network list
   */
  public List<Terminal> getSortedTerminals() {
    List<Terminal> terminalsList = getTerminals();
    terminalsList.sort((t1, t2) -> t1.getID().compareTo(t2.getID()));
    return terminalsList;
  }


  /**
   * Adds a friend terminal to a terminal with a certain id
   * @param idTerminal (6 digit string that identifies a terminal) and
   *                   friend (terminal to add to list of terminal friends of terminal)
   */
  public void addFriendToTerminal(String idTerminal, String friend) {
    Terminal terminal = getTerminal(idTerminal);
    Terminal terminalFriend = getTerminal(friend);
    terminal.addFriend(terminalFriend);
  }


  // ------------------------------- Communications ---------------------------- //


  /**
   * Sends text communication
   * @param sender (terminal that sent the text communication)
   * @param receiverId (id of the terminal that receives the communication)
   * @param message (message to send)
   */
  public void sendTextCommunication(Terminal sender, String receiverId, String message) {
      _comCount++;
      _communications.put(_comCount, sender.makeSMS(getTerminal(receiverId), message, _comCount));
      sender.getClient().resetVideoStreak();
      incrementDebts(getCommunication(_comCount).getCost());
      sender.getClient().checkStatusAfterText(_comCount, getClientSentComs(sender.getClient().getId()));
  }


  /**
   * Strats interactive (voice or video) communication
   * @param sender (terminal than makes the call)
   * @param receiverId (id of the terminal that receives the call)
   * @param typeOfCom (type of communication to start)
   * @throws UnsupportedCallAtOriginException when the origin terminal cannot support video call
   * @throws UnsupportedCallAtDestinationException when the destination terminal cannot support video call
   */
  public void startInteractiveCommunication(Terminal sender, String receiverId, String typeOfCom) throws
          UnsupportedCallAtOriginException, UnsupportedCallAtDestinationException {

    Terminal _receiver = getTerminal(receiverId);
    switch (typeOfCom) {
      case "VOICE" -> {
        _comCount++;
        _communications.put(_comCount, sender.makeVoiceCall(_receiver, _comCount));
        bothEndsOnBusy(sender, _receiver);
        sender.getClient().resetVideoStreak();
      }
      case "VIDEO" -> {
        if (sender instanceof Basic)
          throw new UnsupportedCallAtOriginException();
        else if (_receiver instanceof Basic)
          throw new UnsupportedCallAtDestinationException();
        _comCount++;
        sender.getClient().increaseVideoStreak();
        _communications.put(_comCount, sender.makeVideoCall(_receiver, _comCount));
        bothEndsOnBusy(sender, _receiver);
      }
    }
  }


  /**
   * Ends interactive communication
   * @param sender (terminal that made communication)
   * @param duration (duration of thecommunication)
   */
  public void endCommunication(Terminal sender, int duration) {
    /* receiver and sender of communication to status before call (Receiver previous status is always IDLE) */
    _communications.get(sender.getOnGoingComId()).getReceiver().turnOn();
    sender.toPreviousStatus();

    /* ends communication */
    _communications.get(sender.getOnGoingComId()).endCom(duration, sender);

    /* Increment network's debts */
    incrementDebts(sender.getSentCommunication(sender.getOnGoingComId()).getCost());

    /* puts the _onGoingComId equals to 0 */
    sender.OnGoingIdToZero();

    /* check if client Type changes after communication */
    //sender.getClient().checkChangeAfterCom1(getClientSentComs(sender.getClient().getId()));
    sender.getClient().checkChangeAfterCom();
  }


  /**
   * Puts both terminals on BUSY status
   * @param t1 (sender terminal)
   * @param t2 (receiver terminal)
   */
  public void bothEndsOnBusy(Terminal t1, Terminal t2) {
    t1.setOnBusy();
    t2.setOnBusy();
  }


  /**
   * Returns a communication with a certain id
   * @param comId (id of the communication)
   */
  public Communication getCommunication(int comId) {
    return _communications.get(comId);
  }


  /**
   * Returns a list with all the communications of the network
   */
  public List<Communication> getAllCommunications() {
    return new ArrayList<Communication>(_communications.values());
  }


  /**
   * Returns a list of all the communications (sorted by ids) a client made
   * @param id (id of the client)
   */
  public List<Communication> getClientSentComs(String id) {
    Map<Integer, Communication> communications = _communications.entrySet()
            .stream()
            .filter(map -> map.getValue().getClientSender().getId().equals(id))
            .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
    List<Communication> com = new ArrayList<>(communications.values());
    com.sort((c1, c2) -> c1.compareTo(c2));
    return com;
  }


  /**
   * Returns a list of all the communications (sorted by ids) a client received
   * @param id (id of the client)
   */
  public List<Communication> getClientReceivedComs(String id) {
    Map<Integer, Communication> communications = _communications.entrySet()
            .stream()
            .filter(map -> map.getValue().getClientReceiver().getId().equals(id))
            .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
    List<Communication> com = new ArrayList<>(communications.values());
    com.sort((c1, c2) -> c1.compareTo(c2));
    return com;
  }


  // ---------------------------------- Client ---------------------------------- //


  /**
   * Registers a client in the network clients array
   * @param idClient (string that identifies a client), name (client's name)
   *                 and taxId (client's tax number)
   */
  public void registerClient(String idClient, String name, int taxId) {
    Client client = new Client(idClient, name, taxId, _tariffPlans.get("BASIC"));
    // Hashmap Key given by id in lower case so AAA is the same id as aaa
    _clients.put(idClient.toLowerCase(), client);
  }


  /**
   * Returns the client with a certain id
   * @param idClient (string that identifies a client)
   */
  public Client getClient(String idClient) { return _clients.get(idClient.toLowerCase()); }


  /**
   * Checks if there's a client with the same lower case id in the clients array
   * @param id (string that identifies a client)
   */
  public boolean findClientId(String id) {
    return _clients.get(id.toLowerCase()) != null;
  }


  /**
   * Returns the clients network list
   */
  public List<Client> getClients() {
    return new ArrayList<Client>(_clients.values());
  }


  /**
   * Returns the clients (sorted by ids) network list
   */
  public List<Client> getSortedClients() {
    List<Client> clientsList = getClients();
    clientsList.sort((c1, c2) -> c1.getId().toLowerCase().compareTo(c2.getId().toLowerCase()));
    return clientsList;
  }

  public List<Client> getSortedByDebts() {
    List<Client> clientsList = getSortedClients();
    clientsList.sort(comparingDouble(Client ::getDebts).reversed());
    return clientsList;
  }
}

