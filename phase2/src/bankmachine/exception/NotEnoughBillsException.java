package bankmachine.exception;

import bankmachine.BillManager;

import java.util.Map;

public class NotEnoughBillsException extends BankMachineException {
    public NotEnoughBillsException(BillManager b) {
        super("The bank machine cannot supply that amount of money. It contains\n");
        StringBuilder entries = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : b.getBills().entrySet()) {
            entries.append(entry.getValue());
            entries.append(" - $");
            entries.append(entry.getKey());
            entries.append(" bills\n");
        }
        this.info += entries;
    }

    public NotEnoughBillsException(String s) {
        super(s);
    }

}
