package bankmachine;

import bankmachine.FileManager.FileSearcher;
import bankmachine.FileManager.TimeInfo;
import bankmachine.account.Account;
import bankmachine.account.SavingsAccount;

import java.io.File;

public class BankMachine {
    final public static String fileManagerPath = findFileManagerPath();
    final public static UserManager<Client> clientManager = new UserManager<>(fileManagerPath + "/data/clientData.ser");
    final public static UserManager<BankManager> bankManagerUserManager = new UserManager<>(fileManagerPath + "/data/bankManagerData.ser");
    final public static TimeInfo timeInfo = new TimeInfo();

    private static void executeEveryMonth() {
        int oldmonth = timeInfo.getLastMonth();
        int currmonth = timeInfo.getCurrentMonth();
        if (currmonth != oldmonth) {
            for (Account a : accountManager.getAccounts()) {
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
    private static AccountManager accountManager;
    // static TransactionManager transactionManager

    public static void main(String[] args) {

        billManager = new BillManager();
        accountManager = new AccountManager();
        executeEveryMonth();

        InputManager inputManager = new InputManager();
        inputManager.mainLoop();

    }

    public static BillManager getBillManager() {
        return billManager;
    }

    public static AccountManager getAccountManager() {
        return accountManager;
    }

    public static TimeInfo getTimeInfo() {
        return timeInfo;
    }

    public static String findFileManagerPath() {
        FileSearcher fileSearcher = new FileSearcher();
        fileSearcher.setFileNameToSearch("FileManager");
        fileSearcher.searchForDirectory(new File(System.getProperty("user.dir")));
        return fileSearcher.getResult().get(0);
    }

    public static UserManager<Client> getClientManager() {
        return clientManager;
    }
    public static UserManager<BankManager> getBankManagerUserManager() {
        return bankManagerUserManager;
    }
}
