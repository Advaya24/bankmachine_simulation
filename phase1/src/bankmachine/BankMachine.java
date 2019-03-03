package bankmachine;

import bankmachine.FileManager.TimeInfo;

public class BankMachine {
    void executeEveryMonth(){
        // TODO: Increment all savings accounts
    }
    void executeEveryDay(){}
    private static BillManager billManager;
    private static ClientManager clientManager;
    private static AccountManager accountManager;
    // static TransactionManager transactionManager
    private static TimeInfo timeInfo;

    public static void main(String[] args){
        billManager = new BillManager();
        clientManager = new ClientManager();
        accountManager = new AccountManager();
        timeInfo = new TimeInfo();
        InputManager.mainLoop();
    }

    public static BillManager getBillManager() {
        return billManager;
    }

    public static ClientManager getClientManager(){
        return clientManager;
    }
    public static AccountManager getAccountManager(){
        return accountManager;
    }
    public static TimeInfo getTimeInfo(){
        return timeInfo;
    }
}
