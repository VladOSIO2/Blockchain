package blockchain.message;

import java.util.Random;

/**
 * Generates random messages
 * Has pool of senders and pool of messages
 */
public class MessageGenerator {
    private final Random random = new Random();

    private final String[] senderPool = new String[]{
            "Liam", "Noah", "Oliver", "Elijah", "William", "James", "Nick", "Benjamin",
            "Lucas", "Henry", "Alexander", "TF2 Heavy", "Michael", "Ethan", "Daniel",
            "Felix", "Preston", "Marcus", "Holden", "Emilio", "Remington", "Jeremy", "Vlad"
    };

    private final String[] messagePool = new String[]{
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
    public Message next() {
        return new Message(getSender(), getMessage());
    }

    private String getSender() {
        return senderPool[random.nextInt() % senderPool.length];
    }

    private String getMessage() {
        return messagePool[random.nextInt() % messagePool.length];
    }
}
