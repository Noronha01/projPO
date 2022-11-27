package prr.core;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;


/**
 * Notification service
 */
public class NotificationService implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    // List of notifiers to notify later
    List<Notifier> _notifiersToNotify = new ArrayList<>();


    /**
     * Adds a notifier to the notifiers list
     * @param notifier
     */
    public void subscribeNotifier(Notifier notifier) {
        if (!containsClient(notifier.getClient()))
            _notifiersToNotify.add(notifier);
    }


    /**
     * Checks if there's a notifier with a certain client
     * @param client (client to compare)
     */
    public boolean containsClient(Client client) {
        for (Notifier notifier: _notifiersToNotify)
            if (notifier.getClient().equals(client))
                return true;
        return false;
    }


    /**
     * Notifies the notifiers
     * @param notType (notification type)
     * @param idReceiver (id of the terminal that couldn't receive the communication)
     */
    public void notifyNotifiers(String notType, String idReceiver) {
        for (Notifier notifier: _notifiersToNotify) {
            notifier.notifyClient(notType, idReceiver);
        }
    }


    /**
     * Clears the notifiers list
     */
    public void clearNotifiersList () {
        _notifiersToNotify.clear();
    }
}
