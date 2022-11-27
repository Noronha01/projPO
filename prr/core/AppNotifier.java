package prr.core;


/**
 * App Notifier
 */
public class AppNotifier implements Notifier{

    Client _client;


    /**
     * @param client
     */
    public AppNotifier(Client client){
        _client = client;
    }


    /**
     * Returns the notifier client
     */
    public Client getClient(){
        return _client;
    }


    /**
     * Adds the notification to the client's notifications list
     * @param notType (notification type)
     * @param idReceiver (id of the terminal that couldn't receive the communication)
     */
    @Override
    public void notifyClient(String notType, String idReceiver) {
        Notification notification = new Notification(idReceiver, notType);
        _client.addNotification(notification);
    }
}
