package bankmachine;

import bankmachine.fileManager.FileSearcher;
import bankmachine.fileManager.TimeInfo;
import bankmachine.account.Account;
import bankmachine.account.AccountFactory;
import bankmachine.account.SavingsAccount;

import java.io.File;

public class BankMachine {
    final public static String DATA_PATH = findDataPath();
    final public static TimeInfo timeInfo = new TimeInfo();
    final public static UserManager USER_MANAGER = new UserManager(DATA_PATH + "/clientData.ser");
    final public static AccountFactory accFactory = new AccountFactory(USER_MANAGER);
    final public static TransactionFactory transFactory = new TransactionFactory(accFactory);

    private static void executeEveryMonth() {
        int lastMonth = timeInfo.getLastMonth();
        int currentMonth = timeInfo.getCurrentMonth();
        if (currentMonth != lastMonth && timeInfo.getTime().getDayOfMonth() == 1) {
            for (Account a : accFactory.getInstances()) {
                if (a instanceof SavingsAccount) {
                    ((SavingsAccount) a).applyInterest();
                }
            }
        }
        timeInfo.setLastMonth(currentMonth);
    }

    /**
     * A Manager Object for all the bills of the various denominations within the system.
     */
    private static BillManager billManager;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                USER_MANAGER.saveData();
            }
        }, "Shutdown-thread"));
        billManager = new BillManager();
        executeEveryMonth();

        InputManager inputManager = new InputManager();
        try {
            inputManager.mainLoop();
        } finally {
            USER_MANAGER.saveData();
        }
    }

    public static BillManager getBillManager() {
        return billManager;
    }

    public static TimeInfo getTimeInfo() {
        return timeInfo;
    }

    /**
     * Finds the absolute location of the data directory inside fileManager
     *
     * @return the absolute path for the data directory
     */
    public static String findDataPath() {
        FileSearcher fileSearcher = new FileSearcher();
        fileSearcher.setFileNameToSearch("fileManager");
        fileSearcher.searchForDirectoryIn(new File(System.getProperty("user.dir")));
        final String FILE_MANAGER_PATH = fileSearcher.getResult().get(0);
        fileSearcher.clearResults();
        fileSearcher.setFileNameToSearch("data");
        fileSearcher.searchForDirectoryIn(new File(FILE_MANAGER_PATH));
        return fileSearcher.getResult().get(0);
    }
}
