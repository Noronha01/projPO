package prr.core;


import java.io.Serializable;

/**
 * Communication
 */
public abstract class Communication implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;
    private final int _id;
    private boolean _isPaid;
    private double _cost;
    private final Terminal _sender;
    private final Terminal _receiver;



    public Communication(int id, Terminal sender, Terminal receiver) {
        _id = id;
        _sender = sender;
        _receiver = receiver;
    }


    /**
     * Returns the size of the communication
     */
    public abstract int getSize();


    /**
     * Returns the id of the communication
     */
    public int getId() { return _id; }


    /**
     * Returns the terminal that made the communication
     */
    public Terminal getSender() {
        return _sender;
    }


    /**
     * Returns the terminal that received the communication
     */
    public Terminal getReceiver() {
        return _receiver;
    }


    /**
     * Returns the client that made the communication
     */
    public Client getClientSender() {
        return getSender().getClient();
    }


    /**
     * Returns the client that received the communication
     */
    public Client getClientReceiver() {
        return getReceiver().getClient();
    }

    public abstract boolean getOnGoing();


    /**
     * Returns the cost of the communication
     */
    public double getCost() {
        return _cost;
    }


    /**
     * Sets the cost of the communication
     * @param value (value to actualize the cost value)
     */
    public void setCost(double value) {
        _cost = value;
    }


    /**
     * Returns the variable _isPaid of the communication
     */
    public boolean isPaid () {
        return _isPaid;
    }


    /**
     * Returns the value of the _idPaid variable of the communication
     */
    public void pay() {
        _isPaid = true;
    }


    /**
     * End a communication
     * @param size (size of a communication)
     * @param sender (terminal that made the communication)
     */
    public abstract void endCom(int size, Terminal sender);


    /**
     * Returns the cost of a communication
     * @param tariffPlan (Tariff plan of the client that made the communication)
     */
    public abstract double computeCost(TariffPlan tariffPlan);

    /**
     * Returns the type of communication in String
     */
    public abstract String typeToString();


    /**
     * Returns the difference between the ids of two communications
     * @param com (communication to compare)
     */
    public int compareTo(Communication com) {
        return this.getId() - com.getId();
    }


    /**
     * Returns the string that represents a communication
     */
    public abstract String toString();
}
