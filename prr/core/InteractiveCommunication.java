package prr.core;


/**
 * Interactive communication
 */
public abstract class InteractiveCommunication extends Communication {
    private int _duration;
    private boolean _onGoing;

    public InteractiveCommunication(int id, Terminal sender, Terminal receiver) {
        super(id, sender, receiver);
        _onGoing = true;
    }


    /**
     * Returns the size of the interactive communication
     */
    public int getSize() {
        return _duration;
    }


    /**
     * Returns the _onGoing variable of the interactive communication
     */
    public boolean getOnGoing() { return _onGoing; }


    /*
    * Changes the _onGoing variable to false when an interactive communication ends
    */
    public void notOnGoing() {
        _onGoing = false;
    }


    /**
     * Ends an interactive communication
     * @param duration (size of a communication)
     * @param sender   (terminal that made the communication)
     */
    public void endCom(int duration, Terminal sender) {
        notOnGoing();
        setDuration(duration);
        super.setCost(computeCost(sender.getClient().getTariffPlan()));
        /* increments the debts of a terminal */
        sender.incrementDebts(super.getCost());
        /* increments the debts of a client */
        sender.getClient().incrementDebts(super.getCost());
    }


    /**
     * Sets the duration of an interactive communication
     * @param duration (duration of an interactive communication
     */
    public void setDuration(int duration) {
        _duration = duration;
    }
}
