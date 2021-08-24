package blockchain.message;

import java.util.EventListener;
import java.util.Random;
import java.util.function.Predicate;

/**
 * Generates random messages
 * Has pool of senders and pool of messages
 */
public class MessageGenerator {
    private static final Random random = new Random();

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
    public static Message next() {
        return new Message(getSender(), getMessage());
    }

    private static String getSender() {
        return senderPool[Math.abs(random.nextInt()) % senderPool.length];
    }

    private static String getMessage() {
        return messagePool[Math.abs(random.nextInt()) % messagePool.length];
    }


}
