package bankmachine.users;

public class BankIntern extends BankEmployee {


    public BankIntern(int id, String name, String email, String phoneNumber, String username, String default_password) {
        super(id, name, email, phoneNumber, username, default_password);
        this.salary = 1320;
    }

    @Override
    public String toString() {
        return "Intern " + this.getName() + " (" + getUsername() + ")";
    }


}
