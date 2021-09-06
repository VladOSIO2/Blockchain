package blockchain.cryptocurrency;

import blockchain.Blockchain;
import blockchain.message.Message;

import java.util.ArrayList;
import java.util.List;

public class TransactionHandler {
    private static TransactionHandler instance;
    List<blockchain.cryptocurrency.Transaction> transactionList;
    private static final Blockchain blockchain = Blockchain.getInstance();


    private TransactionHandler() {
        transactionList = new ArrayList<>();
    }

    public static TransactionHandler getInstance() {
        if (instance == null) {
            instance = new TransactionHandler();
        }
        return instance;
    }

    public synchronized void addTransaction(Transaction transaction) {
        transactionList.add(transaction);
    }

    public synchronized List<Transaction> collectTransactions() {
        List<Transaction> collectedTransactions = new ArrayList<>(transactionList);
        transactionList.clear();
        return collectedTransactions;
    }

    public static String groupTransactionsToString(List<Transaction> transactionList) {
        StringBuilder transactions = new StringBuilder();
        transactionList.forEach(tr -> transactions.append(tr.toString()).append("\n"));
        String tr = transactions.toString();
        return tr.isEmpty() ? "\nNo transactions\n" : "\n" + tr;
    }

    public int checkBalance(String miner) {
        //sums balance info from previous blocks in blockchain
        //and done but didn't saved to a block transactions
        int balance = blockchain.getMinerMoney(miner) + unsavedBalanceChanges(miner);
        if (balance < 0) {
            throw new IllegalArgumentException(miner + "got negative balance: " + balance);
        }
        return balance;
    }

    private int unsavedBalanceChanges(String miner) {
        int balance = 0;
        for (Transaction transaction : transactionList) {
            if (transaction.getSender().equals(miner)) {
                balance -= transaction.getAmount();
            }
            if (transaction.getReceiver().equals(miner)) {
                balance += transaction.getAmount();
            }
        }
        return balance;
    }
}
