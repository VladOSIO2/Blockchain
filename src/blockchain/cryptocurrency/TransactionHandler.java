package blockchain.cryptocurrency;

import blockchain.message.Message;

import java.util.ArrayList;
import java.util.List;

public class TransactionHandler {
    private static TransactionHandler instance;
    List<blockchain.cryptocurrency.Transaction> transactionList;


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
        return tr.isEmpty() ? "No transactions\n" : "\n" + tr;
    }
}
