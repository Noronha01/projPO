package prr.core;


/**
 * Notifier
 */
public interface Notifier {
    void notifyClient(String notType, String idReceiver);

    Client getClient();
}
