package blockchain;

import java.io.*;
import java.util.ArrayList;

public class Main implements Serializable {
    private static final long serialVersionUID = -6335623287823730179L;

    public static void main(String[] args) {

        final Blockchain blockchain = Blockchain.init("blockchain.txt");
        for (int i = 0; i < 5; i++) {
            blockchain.createBlock();
        }
        blockchain.printInfo();
    }
}
