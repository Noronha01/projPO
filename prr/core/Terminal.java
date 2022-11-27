package prr.core;

import java.io.Serializable;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

  private final String _idTerminal;
  private TerminalStatus _terminalStatus;

  private TerminalStatus _statusBeforeCall;
  private final Client _client;
  private double _payments;
  private double _debts;

  // stores the onGoing communication ID, if there is no onGoing, stores value 0
  private int _onGoingComId;

  //list of terminals that are friends of the terminal
  private Map<String, Terminal> _friends = new HashMap<>();

  // list of communications of a terminal
  private Map<Integer, Communication> _communicationsSent = new HashMap<>();
  private Map<Integer, Communication> _communicationsReceived = new HashMap<>();

  // list of notifications to send when terminal is able to receive communications
  //private List<Terminal> _terminalsToNotify = new ArrayList<>();

  private NotificationService _notificationService;


  Terminal(String idTerminal, Client client) {
    _idTerminal = idTerminal;
    _terminalStatus = TerminalStatus.IDLE;
    _client = client;
    _payments = 0;
    _debts = 0;
    _notificationService = new NotificationService();
  }


  /**
   * Returns the terminal id
   */
  public String getID() {
    return _idTerminal;
  }


  /**
   * Returns the terminal's status
   */
  public String getStatus() { return _terminalStatus.name(); }


  /**
   * Returns the client associated to the terminal
   */
  public Client getClient() { return _client; }


  /**
   * Returns the terminal's payments
   */
  public double getPayments() { return _payments; }


  /**
   * Returns the terminal's debts
   */
  public double getDebts() { return _debts; }


  /**
   * Increments terminal's paymemnts
   * @param value (value to increment to the terminal's payments)
   */
  public void incrementPayments(double value) {
    _payments += value;
  }


  /**
   * Increments terminal's debts
   * @param value (value to increment to the terminal's debts)
   */
  public void incrementDebts(double value) {
    _debts += value;
  }


  /**
   * Pays a communication
   * @param cost (cost of the communication)
   * @param com (communication to pay)
   */
  public void performPayment(double cost, Communication com) {
    incrementDebts(-cost);
    incrementPayments(cost);
    getClient().incrementDebts(-cost);
    getClient().incrementPayments(cost);
    com.pay();
  }


  /**
   * Returns the id of the ongoing communication, if there's no ongoing communication returns 0
   */
  public int getOnGoingComId() {return _onGoingComId; }


  /**
   * Puts the terminal on SILENCE
   */
  public void setOnSilent() {
    sendNotifications(getStatus(), "2S");
    _terminalStatus =  TerminalStatus.SILENCE;
  }


  /**
   * Puts the terminal on BUSY mode
   */
  public void setOnBusy() {
    _terminalStatus = TerminalStatus.BUSY;
  }


  /**
   * Turns OFF the terminal
   */
  public void turnOff() {
    _terminalStatus = TerminalStatus.OFF;
  }


  /**
   * Turns ON the terminal
   */
  public void turnOn() {
    sendNotifications(getStatus(), "2I");
    _terminalStatus = TerminalStatus.IDLE;
  }


  /**
   * Changes the terminal's status to its previous status before a communication
   */
  public void toPreviousStatus() {
    if (_statusBeforeCall.name().equals("IDLE"))
      turnOn();
    else
      _terminalStatus = _statusBeforeCall;
  }


  /**
   * Adds a friend of the terminal to the terminal's list of friends
   * @param friend (friend terminal to add)
   */
  public void addFriend(Terminal friend) {
    _friends.put(friend.getID(), friend);
  }


  /**
   * Removes a friend of the terminal of the terminal's list of friends
   * @param friend (friend terminal to remove)
   */
  public void removeFriend(Terminal friend) { _friends.remove(friend.getID(), friend); }


  /**
   * Returns a friend of the terminal from the terminal's list of friends
   * @param friendId (id of the friend of the terminal to return)
   */
  public Terminal getFriend(String friendId) { return _friends.get(friendId); }


  /**
   * Returns a sent communication with a certain id
   * @param commId (id of the communication)
   */
  public Communication getSentCommunication(int commId) {
    return _communicationsSent.get(commId);
  }


  /**
   * Returns the list of communications made by a terminal
   */
  public List<Communication> getCommunicationsSent() {return new ArrayList<>(_communicationsSent.values()); }


  /**
   * Returns the list of communications received by a terminal
   */
  public List<Communication> getCommunicationsReceived() {return new ArrayList<>(_communicationsReceived.values()); }


  /**
   * Sends a text communication and returns it
   * @param receiver (terminal that sent the communication)
   * @param message (message to send)
   * @param id (id of the communication)
   */
  public Communication makeSMS(Terminal receiver, String message, int id) {
    Communication textCommunication = new TextCommunication(id, this, receiver, message);
    textCommunication.endCom(message.length(),this);
    makeCommunication(textCommunication, receiver);
    return textCommunication;
  }


  /**
   * Makes a voice call and returns it
   * @param receiver (terminal that receives the communication)
   * @param id (id of the communication)
   */
  public Communication makeVoiceCall(Terminal receiver, int id) {
    Communication voiceCommunication = new VoiceCommunication(id, this, receiver);
    _onGoingComId = id;
    _statusBeforeCall = _terminalStatus;
    makeCommunication(voiceCommunication, receiver);
    return voiceCommunication;
  }


  /**
   * Makes a video call and returns it
   * @param receiver (terminal that receives the communication)
   * @param id (id of the communication)
   */
  public Communication makeVideoCall(Terminal receiver, int id) {
    Communication videoCommunication = new VideoCommunication(id, this, receiver);
    _onGoingComId = id;
    _statusBeforeCall = _terminalStatus;
    makeCommunication(videoCommunication, receiver);
    return videoCommunication;
  }


  /**
   * Makes interactive communication
   * @param com (communication)
   * @param receiver (terminal that receives the communication)
   */
  public void makeCommunication(Communication com, Terminal receiver) {
    _communicationsSent.put(com.getId(), com);
    receiver.addReceived(com);
  }


  /**
   * Puts a communication in the received communications list of a terminal
   * @param com (communication to add)
   */
  public void addReceived(Communication com) {
    _communicationsReceived.put(com.getId(), com);
  }


  /**
   * Puts the _onGoingId variable equals to 0 when a communication is over
   */
  public void OnGoingIdToZero() { _onGoingComId = 0; }


  /**
   * Checks if this terminal can end the current interactive communication.
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    return _onGoingComId != 0;
  }


  /**
   * Checks if this terminal can start a new communication.
   * @return true if this terminal is neither off nor busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    return !(_terminalStatus.name().equals("BUSY") || _terminalStatus.name().equals("OFF"));
  }


  /**
   * Adds a subscriber to the notification service to notify later
   * @param client (client of the terminal that couldn't receive a communication)
   */
  public void addPendingNotifier(Client client) {
    if (client.getNotificationsSwitch()) {
      AppNotifier notifier = new AppNotifier(client);
      _notificationService.subscribeNotifier(notifier);
    }
  }


  /**
   * Sends the notifications to the notifiers
   * @param prevStatus (previous status of a terminal before being able to accept a communication)
   * @param notificationSuffix (2S(to SILENT) or 2I(to IDLE))
   */
  public void sendNotifications(String prevStatus, String notificationSuffix) {
    String notType = null;
    switch (prevStatus) {
      case "OFF" -> notType = "O" + notificationSuffix;
      case "BUSY" -> notType = "B" + notificationSuffix;
      case "SILENCE" -> notType = "S" + notificationSuffix;
    }
    _notificationService.notifyNotifiers(notType, this.getID());
    _notificationService.clearNotifiersList();
  }


  /**
   * Checks if a terminal is in the friends list
   * @param terminal (terminal that might be a friend)
   */
  public boolean isFriend(Terminal terminal) {
    return _friends.containsValue(terminal);
  }


  /**
   * Returns the string that represents a friend of the terminal
   */
  public String friendsToString() {
    //List<String> friendsIds = _friends.keySet().stream().collect(Collectors.toList());
    String str = "";
    List<String> friendsIds = new ArrayList<String>(_friends.keySet());
    if(friendsIds.size() != 0) { str = "|"; }
    Collections.sort(friendsIds);
    return str + String.join(",", friendsIds);
  }


  /**
   * Returns the string that represents a terminal
   */
  public String toString() {
    return _idTerminal + "|" + _client.getId() + "|" + _terminalStatus.name() + "|" + Math.round(_payments) +
            "|" + Math.round(_debts) + friendsToString();
  }
}

