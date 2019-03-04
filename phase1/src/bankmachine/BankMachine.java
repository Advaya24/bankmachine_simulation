package bankmachine;

import bankmachine.FileManager.TimeInfo;
import bankmachine.account.Account;
import bankmachine.account.SavingsAccount;

public class BankMachine {
    void executeEveryMonth(){
        //TODO: WRITE TO FILE YAY
        int oldmonth = 0;
        int currmonth = 5;
        if(currmonth != oldmonth){
            for(Account a:accountManager.getAccounts()){
                if (a instanceof SavingsAccount){
                    ((SavingsAccount) a).applyInterest();
                }
            }
        }
        oldmonth = currmonth;
        //then write it to file
    }
    void executeEveryDay(){}
    private static BillManager billManager;
    private static ClientManager clientManager;
    private static AccountManager accountManager;
    // static TransactionManager transactionManager
    private static TimeInfo timeInfo;
    private static InputManager inputManager;

    public static void main(String[] args){
        billManager = new BillManager();
        clientManager = new ClientManager();
        accountManager = new AccountManager();
        timeInfo = new TimeInfo();
        inputManager = new InputManager();
        inputManager.mainLoop();
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
