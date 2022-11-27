package prr.core;

import java.util.HashMap;


/**
 * Basic plan
 */
public class BasicPlan extends TariffPlan {

    /*

               textPrices:                      voicePrices
          NORMAL    GOLD   PLATINUM    |  NORMAL    GOLD    PLATINUM
       0    10       10       0        |   20        10        10
       1    16       10       4        |-----------------------------
       2    *2       *2       4        |         videoPrices
                                       |  NORMAL    GOLD    PLATINUM
       0 -> size < 50                  |   30        20        10
       1 -> size < 100
       2 -> size >= 100
      (NORMAL, 2) and (GOLD, 2) store the number 2, but to compute the price we multiply by size of com

    */

    HashMap<String, HashMap<Integer, Integer>> _textPrices = new HashMap<>();
    HashMap<String, Integer> _voicePrices = new HashMap<>();
    HashMap<String, Integer> _videoPrices = new HashMap<>();


    public BasicPlan() {
        super("BASIC");
        initTable();
    }


    /**
     * Initializes the hash tables of prices
     */
    private void initTable() {
        textPricesInit();
        voicePricesInit();
        videoPricesInit();
    }


    /**
     * Initializes the hash table of text prices
     */
    private void textPricesInit() {
        HashMap<Integer, Integer> price0 = new HashMap<>();
        HashMap<Integer, Integer> price1 = new HashMap<>();
        HashMap<Integer, Integer> price2 = new HashMap<>();
        price0.put(0, 10);
        price0.put(1, 16);
        price0.put(2, 2);
        price1.put(0, 10);
        price1.put(1, 10);
        price1.put(2, 2);
        price2.put(0, 0);
        price2.put(1, 4);
        price2.put(2, 4);
        _textPrices.put("NORMAL", price0);
        _textPrices.put("GOLD", price1);
        _textPrices.put("PLATINUM", price2);
    }


    /**
     * Initializes the hash table of voice communication prices
     */
    private void voicePricesInit() {
        _voicePrices.put("NORMAL", 20);
        _voicePrices.put("GOLD", 10);
        _voicePrices.put("PLATINUM", 10);
    }


    /**
     * Initializes the hash table of video communication prices
     */
    private void videoPricesInit() {
        _videoPrices.put("NORMAL", 30);
        _videoPrices.put("GOLD", 20);
        _videoPrices.put("PLATINUM", 10);
    }


    /**
     * Splits the size of text communication in 3 categories
     * Below 50, between 50 and 100 and more than 100, returns 0, 1, 2.
     * @param size (size of text communication)
     */
    private int getSizeCategory(double size) {
        if (size < 50)
            return 0;
        else if (size < 100)
            return 1;
        return 2;
    }


    /**
     * Calculates the cost of a text communication
     * @param client (client that made the communication)
     * @param com (text communication)
     */
    @Override
    public double computeCost(Client client, TextCommunication com) {
        double size = com.getSize();
        int sizeCategory = getSizeCategory(size);
        if(sizeCategory < 2 || client.getClientType().equals("PLATINUM"))
            return _textPrices.get(client.getClientType()).get(sizeCategory);
        return size * _textPrices.get(client.getClientType()).get(sizeCategory);
    }


    /**
     * Calculates the cost of a voice communication
     * @param client (client that made the communication)
     * @param com (voice communication)
     */
    @Override
    public double computeCost(Client client, VoiceCommunication com) {
        return com.getSize() * _voicePrices.get(client.getClientType());
    }


    /**
     * Calculates the cost of a video communication
     * @param client (client that made the communication)
     * @param com (video communication)
     */
    @Override
    public double computeCost(Client client, VideoCommunication com) {
        return com.getSize() * _videoPrices.get(client.getClientType());
    }

}

