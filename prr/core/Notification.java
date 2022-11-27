package prr.core;

import java.io.Serializable;


/**
 * Notification
 */
public class Notification implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    private NotificationType _notificationType;
    private final String _terminalNotifiedId;


    public Notification(String notifyingTerminalId, String notType) {
        _terminalNotifiedId = notifyingTerminalId;
        switch(notType) {
            case "O2S" -> _notificationType = NotificationType.O2S;
            case "O2I" -> _notificationType = NotificationType.O2I;
            case "S2I" -> _notificationType = NotificationType.S2I;
            case "B2I" -> _notificationType = NotificationType.B2I;
        }
    }


    /**
     * Returns notification type as string
     */
    public String getNotificationType() {
        return _notificationType.name();
    }


    /**
     * Returns a string that represents a notification
     */
    public String toString() {
        return getNotificationType() + "|" + _terminalNotifiedId;
    }
}
