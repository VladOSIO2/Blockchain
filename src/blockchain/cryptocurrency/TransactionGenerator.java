package blockchain.cryptocurrency;

import blockchain.Blockchain;
import blockchain.security.CryptographyManager;

import java.util.*;

public class TransactionGenerator {

    private static final Random random = new Random();
    private static final Blockchain blockchain = Blockchain.getInstance();

    private static final String[] customUsers = new String[] {
            "McDonalds", "Mann co.", "Toshiba", "Moderna"
    };

    private static final Set<String> users = new HashSet<>(Arrays.asList(customUsers));

    /** initiates TransactionGenera */
    public static void init() {
        Thread.getAllStackTraces().forEach(
                (thread, stack) -> users.add("miner" + thread.getId()));
        users.addAll(blockchain.getMiners());
    }

    /** generates next transaction and encrypts it with sender's private key
     * (simulation that sender encrypts transaction, adds public key and wraps all in one object)
     * @return randomly generated Transaction, filled with data */
    public static Transaction next() throws Exception {
        String receiver = getRandomUser();
        String sender;
        int balance; //sender's balance: how much VCs he has
        do {
            sender = getRandomUser();
            balance = TransactionHandler.getInstance().checkBalance(sender);
        } while (balance == 0 || sender.equals(receiver));

        int amount = Math.abs(random.nextInt()) % balance + 1;
/*
        String amountEncrypted = CryptographyManager.encryptText(
                Integer.toString(amount),
                CryptographyManager.getPrivate(sender)
        );*/

        return new Transaction(sender, receiver, amount/*, CryptographyManager.getPublic(sender)*/);
    }

    private static String getRandomUser() {
        int pick = Math.abs(random.nextInt()) % users.size();
        Object[] userArr =  users.toArray();
        return (String) userArr[pick];
    }

    //debug method
    public static void printUsers() {
        users.forEach(user -> System.out.println(user + " - " + blockchain.getMinerMoney(user)));
    }
}
