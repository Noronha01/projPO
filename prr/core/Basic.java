package prr.core;


/**
 * Basic terminal
 */
class Basic extends Terminal {
    Basic(String idTerminal, Client client){
        super(idTerminal, client);
    }


    /**
     * Returns the string that represents a Basic terminal
     */
    public String toString() {
        return "BASIC|" + super.toString();
    }
}
