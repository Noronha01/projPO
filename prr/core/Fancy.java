package prr.core;


/**
 * Fancy terminal
 */
class Fancy extends Terminal {
    Fancy(String idTerminal, Client client){
        super(idTerminal, client);
    }


    /**
     * Returns the string that represents a Fancy terminal
     */
    public String toString() {
        return "FANCY|" + super.toString();
    }
}
