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
    final public static UserManager USER_MANAGER = new UserManager(DATA_PATH+"/clientData.ser");
    final public static AccountFactory accFactory = new AccountFactory(USER_MANAGER);
    final public static TransactionFactory transFactory = new TransactionFactory(accFactory);
    private static void executeEveryMonth() {
        int oldMonth = timeInfo.getLastMonth();
        int currMonth = timeInfo.getCurrentMonth();
        if (currMonth != oldMonth) {
            for (Account a : accFactory.getInstances()) {
                if (a instanceof SavingsAccount) {
                    ((SavingsAccount) a).applyInterest();
                }
            }
        }
        timeInfo.setLastMonth(currMonth);
    }

    /** A Manager Object for all the bills of the various denominations within the system.*/
    private static BillManager billManager;

    public static void main(String[] args) {
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
