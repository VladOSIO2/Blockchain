package blockchain;

public class Main {
    public static void main(String[] args) {
        final Blockchain blockchain = new Blockchain();
        for (int i = 0; i < 5; i++) {
            blockchain.createBlock();
        }
        blockchain.printInfo();
    }
}
