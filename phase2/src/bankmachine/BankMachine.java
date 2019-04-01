package bankmachine;

import bankmachine.account.Account;
import bankmachine.account.AccountFactory;
import bankmachine.account.RetirementAccount;
import bankmachine.account.SavingsAccount;
import bankmachine.fileManager.FileSearcher;
import bankmachine.fileManager.TimeInfo;
import bankmachine.gui.InputManager;
import bankmachine.transaction.TransactionFactory;
import bankmachine.users.BankEmployee;
import bankmachine.users.BankMachineUser;
import bankmachine.users.UserManager;

import java.io.File;

public class BankMachine {
    /**
     * A String representing where the data required for this information is stored.
     */
    final public static String DATA_PATH = findDataPath();
    /**
     * A TimeInfo object that allows the application to keep track of what time it is.
     */
    final public static TimeInfo timeInfo = new TimeInfo();
    /**
     * A UserManager object that stores all the Users within the System
     */
    final public static UserManager USER_MANAGER = new UserManager(DATA_PATH + "/clientData.ser");
    /**
     * An AccountFactory object that is used to generate and store new accounts
     */
    final public static AccountFactory accFactory = USER_MANAGER.getAccountFactory();
    /**
     * A TransactionFactory object that is used to generate and store new transactions
     */
    final public static TransactionFactory transFactory = new TransactionFactory(accFactory);

    /**
     * Every month, this method is used to execute time-sensitive functionality, such as applying
     * interest and updating all retirement accounts.
     */
    public static void executeEveryMonth() {
        for (Account a : accFactory.getInstances()) {
            if (a instanceof SavingsAccount) {
                ((SavingsAccount) a).applyInterest();
            }
            if (a instanceof RetirementAccount) {
                ((RetirementAccount) a).autoDeposit();
            }
        }
        for (BankMachineUser user : USER_MANAGER.getInstances()) {
            if (user instanceof BankEmployee) {
                ((BankEmployee) user).receivePayment();
            }
        }
    }

    private static void checkMonthlyFunctions() {
        int lastMonth = timeInfo.getLastMonth();
        int currentMonth = timeInfo.getCurrentMonth();
        if (currentMonth != lastMonth && timeInfo.getTime().getDayOfMonth() == 1) {
            executeEveryMonth();
        }
        timeInfo.setLastMonth(currentMonth);
    }

    /**
     * A Manager Object for all the bills of the various denominations within the system.
     */
    private static BillManager billManager = new BillManager(DATA_PATH + "/billData.ser");

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(USER_MANAGER::saveData, "Shutdown-thread"));
        Runtime.getRuntime().addShutdownHook(new Thread(billManager::saveData, "Shutdown-thread"));
        checkMonthlyFunctions();

        InputManager inputManager = new InputManager();
        try {
            inputManager.mainLoop();
        } finally {
            USER_MANAGER.saveData();
            billManager.saveData();
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
        fileSearcher.setFileNameToSearch("phase2");
        fileSearcher.searchForDirectoryIn(new File(System.getProperty("user.dir")));
        final File ROOT_DIR;
        if (fileSearcher.getResult().size() > 0) {
            ROOT_DIR = new File(fileSearcher.getResult().get(0));
        } else {
            ROOT_DIR = new File(System.getProperty("user.dir"));
        }
        fileSearcher.clearResults();
        fileSearcher.setFileNameToSearch("fileManager");
        fileSearcher.searchForDirectoryIn(ROOT_DIR);
        final String FILE_MANAGER_PATH = fileSearcher.getResult().get(0);
        fileSearcher.clearResults();
        fileSearcher.setFileNameToSearch("data");
        fileSearcher.searchForDirectoryIn(new File(FILE_MANAGER_PATH));
        return fileSearcher.getResult().get(0);
    }
}
