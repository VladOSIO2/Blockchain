package blockchain;

import java.io.*;
import java.util.ArrayList;

public class Main implements Serializable {
    private static final long serialVersionUID = -6335623287823730179L;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        final Blockchain blockchain = Blockchain.init("blockchain.txt");
        blockchain.createBlock();
/*
        for (int i = 0; i < 5; i++) {
            blockchain.createBlock();
        }*/
        blockchain.printInfo();

        ObjectOutputStream os1 = new ObjectOutputStream(new FileOutputStream("test"));
        os1.writeObject(new String("eeee"));
        os1.close();

        ObjectOutputStream os2 = new ObjectOutputStream(new FileOutputStream("test", true)) {
            protected void writeStreamHeader() throws IOException {
                reset();
            }
        };

        os2.writeObject(new ArrayList<String>());
        os2.close();

        ObjectInputStream is = new ObjectInputStream(new FileInputStream("test"));
        System.out.println(is.readObject());
        System.out.println(is.readObject());
    }
}
