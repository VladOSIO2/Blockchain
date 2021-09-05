package blockchain.message;

import blockchain.Blockchain;
import blockchain.security.CryptographyManager;

import java.util.Random;

/**
 * Generates random messages
 * Has pool of senders and pool of messages
 */
public class MessageGenerator {
    private static final Random random = new Random();
    private static final Blockchain blockchain = Blockchain.getInstance();

    private static final String[] senderPool = new String[]{
            "Liam", "Noah", "Oliver", "Elijah", "William", "James", "Nick", "Benjamin",
            "Lucas", "Henry", "Alexander", "TF2 Heavy", "Michael", "Ethan", "Daniel",
            "Felix", "Preston", "Marcus", "Holden", "Emilio", "Remington", "Jeremy", "Vlad"
    };

    private static final String[] messagePool = new String[]{
            "Hello there", "Hello, World!",
            "A cake is a lie",
            "Hexagons are bestagons",
            "Lorem ipsum dolor sir amet",
            "Deus Vult!", "Ave Maria!",
            "Tell me: where did we got so wrong?",
            "Вы продаете рыбов?", "Нет, только показываю", "Красивое...",
            "теперь мы все слоники",
            "Yes", "No", "Да.", "Нет.", "Нааавеееерное",
            "Goodbye!", "GoodBye, cruel World..."
};

    /** generates next message and encrypts it with sender's private key
     * (simulation that sender encrypts message, adds public key and wraps all in one object)
     * @return randomly generated Message, filled with data */
    public static Message next() throws Exception {
        String sender = getSender();
        String message = CryptographyManager.encryptText(
                getMessage(),
                CryptographyManager.getPrivate(sender));
        return new Message(sender, message, blockchain.getNextMsgID(), CryptographyManager.getPublic(sender));
    }

    private static String getSender() {
        return senderPool[Math.abs(random.nextInt()) % senderPool.length];
    }

    private static String getMessage() {
        return messagePool[Math.abs(random.nextInt()) % messagePool.length];
    }

}
