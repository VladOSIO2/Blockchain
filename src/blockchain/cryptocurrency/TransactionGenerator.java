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
    }

    /** generates next transaction and encrypts it with sender's private key
     * (simulation that sender encrypts transaction, adds public key and wraps all in one object)
     * @return randomly generated Transaction, filled with data */
    public static Transaction next() throws Exception {
        String receiver = getRandomUser();
        String sender;
        int wallet; //sender's wallet: how much VCs he has
        do {
            sender = getRandomUser();
            wallet = blockchain.getMinerMoney(sender);
        } while (wallet == 0);

        int amount = Math.abs(random.nextInt()) % wallet + 1;

        String amountEncrypted = CryptographyManager.encryptText(
                Integer.toString(amount),
                CryptographyManager.getPrivate(sender)
        );

        return new Transaction(sender, receiver, amountEncrypted, CryptographyManager.getPublic(sender));
    }

    private static String getRandomUser() {
        int pick = Math.abs(random.nextInt()) % users.size();
        String[] userArr = (String[]) users.toArray();
        return userArr[pick];
    }


}
