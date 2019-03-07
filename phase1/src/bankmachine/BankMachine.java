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
    final public static UserManager USER_MANAGER = new UserManager("/clientData.ser");
    final public static AccountFactory accFactory = new AccountFactory(USER_MANAGER);
    final public static TransactionFactory transFactory = new TransactionFactory(accFactory);
    private static void executeEveryMonth() {
        int oldmonth = timeInfo.getLastMonth();
        int currmonth = timeInfo.getCurrentMonth();
        if (currmonth != oldmonth) {
            for (Account a : accFactory.getInstances()) {
                if (a instanceof SavingsAccount) {
                    ((SavingsAccount) a).applyInterest();
                }
            }
        }
        timeInfo.setLastMonth(currmonth);
    }

    void executeEveryDay() {
    }

    private static BillManager billManager;

    public static void main(String[] args) {
        billManager = new BillManager();
        executeEveryMonth();

        InputManager inputManager = new InputManager();
        inputManager.mainLoop();

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
