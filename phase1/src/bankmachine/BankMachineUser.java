package bankmachine;

// TODO: find better name for this
// managed by: Advaya
public class BankMachineUser {
    private String userName;
    private String password;

    BankMachineUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUsername() { return userName; }
    public String getPassword() { return password; }

    public void setUserName(String new_username) {
        userName = new_username;
    }
    /**Allows the user to change their password if necessary**/
    // (UI should ask for old password to authenticate first, or should the user be logged in to access this? What if a bank manager loses their password?)
    public void setPassword(String new_password){
        password = new_password;
    }
}
