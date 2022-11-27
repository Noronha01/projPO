package prr.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


/**
 * Client
 */
public class Client implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

  private final String _id;
  private final String _name;
  private final int _taxId;

  private int _videoStreak;
  private double _payments;
  private double _debts;
  private boolean _notificationsSwitch;
  private ClientType _clientType;
  private TariffPlan _tariffPlan;

  //list of terminals that are associated to a client
  private Map<String, Terminal> _clientTerminals = new HashMap<String, Terminal>();

  //list of pending notifications
  private List<Notification> _notifications = new ArrayList<>();


  Client(String id, String name, int taxId, TariffPlan tariffPlan) {
      _id = id;
      _name = name;
      _taxId = taxId;
      _payments = 0;
      _debts = 0;
      _notificationsSwitch = true;
      _clientType = ClientType.NORMAL;
      _tariffPlan = tariffPlan;
  }


  /**
   * Adds a terminal to the client's terminals list
   *
   * @param terminal (terminal to add)
   */
  public void addTerminal(Terminal terminal) {
      _clientTerminals.put(terminal.getID(), terminal);
  }


  /**
   * Increments client's paymemnts
   *
   * @param value (value to increment to the client's payments)
   */
  public void incrementPayments(double value) {
      _payments += value;
      checkChangeAfterPay();
  }


  /**
   * Increments client's debts
   *
   * @param value (value to increment to the client's debts)
   */
  public void incrementDebts(double value) {
      _debts += value;
  }


  /**
   * Returns the client's id
   */
  public String getId() {
      return _id;
  }


  /**
   * Returns the clientType in String
   */
  public String getClientType() {
      return _clientType.name();
  }


  /**
   * Returns the client's payments
   */
  public double getPayments() {
      return _payments;
  }


  /**
   * Returns the client's debts
   */
  public double getDebts() {
      return _debts;
  }


  /**
   * Returns the client's balance
   */
  public double getBalance() { return _payments - _debts; }


  /**
   * Returns the client's notification switch
   */
  public boolean getNotificationsSwitch() {
    return _notificationsSwitch;
  }


  /**
   * Returns the client's tariff plan
   */
  public TariffPlan getTariffPlan() { return _tariffPlan; }


  /**
   * Turns on the client's notification switch
   */
  public void turnOnNotificationSwitch() {
      _notificationsSwitch = true;
  }


  /**
   * Turns off the client's notification switch
   */
  public void turnOffNotificationSwitch() {
      _notificationsSwitch = false;
  }


  /**
   * Changes the client's type to normal
   */
  public void changeClientToNormal() {
      _clientType = ClientType.NORMAL;
      resetVideoStreak();
  }


  /**
   * Changes the client's type to gold
   */
  public void changeClientToGold() {
      _clientType = ClientType.GOLD;
  }


  /**
   * Changes the client's type to platinum
   */
  public void changeClientToPlatinum() {
      _clientType = ClientType.PLATINUM;
  }

  public void increaseVideoStreak(){
      if (_clientType.name().equals("GOLD"))
        _videoStreak++;
  }

  public void resetVideoStreak(){
      _videoStreak = 0;
  }


  /**
   * Changes the client's type to GOLD if previously was NORMAL and had a balance superior to 500 credits
   */
  public void checkChangeAfterPay() {
      if (getClientType().equals("NORMAL") && (getBalance() > 500))
          changeClientToGold();
  }


  /**
   * Changes the client's type do NORMAL if the balance id negative
   * or changes the client's type to PLATINUM if previously was GOLD and made 5 consecutive video calls
   *
   * @param comSent (list of the communications made by a client)
   */
  public void checkChangeAfterCom1(List<Communication> comSent) {
      int count = 0;
      int size = comSent.size();
      if (getBalance() < 0) {
          changeClientToNormal();
      }
      else if(comSent.size() >= 5 && getClientType().equals("GOLD")) {
          for (int i = size - 5; i < size; i++) {
              if(comSent.get(i).typeToString().equals("VIDEO"))
                  count+=1;
          }
          if(count == 5)
              changeClientToPlatinum();
      }
  }

    public void checkChangeAfterCom() {
        if (getBalance() < 0) {
            changeClientToNormal();
        }
        else if (_videoStreak == 5) {
            changeClientToPlatinum();
        }
    }


  /**
   * Changes the client's type do GOLD if previously was PLATINUM and made 2 consecutive text messages
   *
   * @param id (client's last communication id)
   * @param comSent (list of the communications made by a client)
   */
  public void checkStatusAfterText(int id, List<Communication> comSent) {
      if(getBalance() < 0)
          changeClientToNormal();
      else if(comSent.size() >= 2 && getClientType().equals("PLATINUM") && comSent.get(comSent.size()-1).typeToString().equals("TEXT") && comSent.get(comSent.size()-2).typeToString().equals("TEXT"))
          changeClientToGold();
  }


  /**
   * Puts a notification in the list of the pending client's notification
   *
   * @param notification (notification to add)
   */
  public void addNotification(Notification notification) {
      _notifications.add(notification);
}


  /**
   * Returns the list of the client's pending notifications
   */
  public List<Notification> getNotifications() {
      return new ArrayList<>(_notifications);
  }


  /**
   * Clears the pending notifications list
   */
  public void clearNotifications() {
      _notifications.clear();
  }


  /**
   * Returns the string that represents a client
   */
  public String toString() {
    return "CLIENT|" + _id + "|" + _name + "|" + _taxId + "|" + _clientType.name() + "|"
            + (getNotificationsSwitch() ? "YES" : "NO") + "|" + _clientTerminals.size() + "|"
            + Math.round(_payments) + "|" + Math.round(_debts);
  }

}