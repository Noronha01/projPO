package prr.core;


/**
 * Interactive communication
 */
public class TextCommunication extends Communication {
    private final String _message;

    public TextCommunication(int id, Terminal sender, Terminal receiver, String message) {
        super(id, sender, receiver);
        _message = message;
    }


    /**
     * Ends a text communication
     * @param size   (size of a communication)
     * @param sender (terminal that made the communication)
     */
    public void endCom(int size, Terminal sender) {
        super.setCost(computeCost(sender.getClient().getTariffPlan()));
        /* increments the debts of a terminal */
        sender.incrementDebts(super.getCost());
        /* increments the debts of a client */
        sender.getClient().incrementDebts(super.getCost());
    }


    /**
     * Returns the type of communication in string
     */
    public String typeToString() {
        return "TEXT";
    }


    /**
     * Returns the cost of a communication
     * @param tariffPlan (Tariff plan of the client that made the communication)
     */
    public double computeCost(TariffPlan tariffPlan) { return tariffPlan.computeCost(super.getClientSender(), this); }


    /**
     * Returns the size of a text communication
     */
    public int getSize() {
        return _message.length();
    }


    /**
     * @return always false because it s a text communication
     */
    public boolean getOnGoing() { return false; }


    /**
     * Returns the string that represents a text communication
     */
    public String toString() {
        return "TEXT|" + super.getId() + "|" + super.getSender().getID() + "|" + super.getReceiver().getID()
                + "|" +  getSize() + "|" + Math.round(getCost()) + "|" + "FINISHED";
    }
}
