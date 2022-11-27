package prr.core;

public class VoiceCommunication extends InteractiveCommunication {

    public VoiceCommunication(int id, Terminal sender, Terminal receiver) {
        super(id, sender, receiver);
    }


    /**
     * Returns the cost of a voice communication
     * @param tariffPlan (Tariff plan of the client that made the communication)
     */
    public double computeCost(TariffPlan tariffPlan) {
        if(getOnGoing())
            return 0;
        else if (super.getSender().isFriend(super.getReceiver()))
            return 0.5 * tariffPlan.computeCost(super.getClientSender(), this);

        return tariffPlan.computeCost(super.getClientSender(), this);
    }


    /**
     * Returns the type of communication in string
     */
    public String typeToString() {
        return "VOICE";
    }


    /**
     * Returns the string that represents a voice communication
     */
    public String toString() {
        return "VOICE|" + super.getId() + "|" + super.getSender().getID() + "|" + super.getReceiver().getID()
                + "|" +  super.getSize() + "|" + Math.round(super.getCost()) + "|" +
                (getOnGoing() ? "ONGOING" : "FINISHED");
    }
}
