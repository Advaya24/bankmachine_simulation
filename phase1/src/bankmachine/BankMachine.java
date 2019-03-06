package bankmachine;


import bankmachine.FileManager.FileSearcher;
import bankmachine.FileManager.TimeInfo;
import bankmachine.account.Account;
import bankmachine.account.SavingsAccount;

import java.io.File;


public class BankMachine {
    final public static String fileManagerPath = findFileManagerPath();
    final public static TimeInfo timeInfo = new TimeInfo();

    private static void executeEveryMonth() {
        int oldmonth = timeInfo.getLastMonth();
        int currmonth = timeInfo.getCurrentMonth();
        if (currmonth != oldmonth) {
            for (Account a : Account.accounts) {
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
        new DataLoader(fileManagerPath).loadData();
        System.out.println(Client.clients.size());
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
    public static String findFileManagerPath() {
        FileSearcher fileSearcher = new FileSearcher();
        fileSearcher.setFileNameToSearch("FileManager");
        fileSearcher.searchForDirectory(new File(System.getProperty("user.dir")));
        return fileSearcher.getResult().get(0);
    }
}
