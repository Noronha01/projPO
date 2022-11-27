package prr.core;

import java.io.Serializable;


/**
 * Tariff Plan
 */
public abstract class TariffPlan implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;
    String _name;

    public TariffPlan(String name) {
        _name = name;
    }


    /**
     * Returns the name of tariff plan
     */
    public String getName() {
        return _name;
    }


    /**
     * Returns the cost of a text communication depending on the client's type
     * @param client (client that made the communication)
     * @param com (text communication)
     */
    public abstract double computeCost(Client client, TextCommunication com);


    /**
     * Returns the cost of a voice communication depending on the client's type
     * @param client (client that made the communication)
     * @param com (voice communication)
     */
    public abstract double computeCost(Client client, VoiceCommunication com);


    /**
     * Returns the cost of a video communication depending on the client's type
     * @param client (client that made the communication)
     * @param com (video communication)
     */
    public abstract double computeCost(Client client, VideoCommunication com);

}