package bankmachine.exception;

/**
 * An exception used by bankmachine.
 * Should only instantiate subclasses
 */
public abstract class BankMachineException extends Throwable {
    private String info;
    public BankMachineException(String info){
        this.info = info;
    }

    @Override
    public String toString() {
        return info;
    }
}
