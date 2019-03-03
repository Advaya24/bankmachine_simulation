package bankmachine;

import bankmachine.account.Account;

public class BankMachine {
    void executeEveryMonth(){
        // TODO: Increment all savings accounts
    }
    void executeEveryDay(){}
    static BillManager billManager;
    static ClientManager clientManager;
    static Account accountManager;
    // static TransactionManager transactionManagerl

    public static void main(String[] args){
        billManager = new BillManager();
    }

    public static BillManager getBillManager() {
        return billManager;
    }
}
