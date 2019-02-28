package bankmachine;

// TODO: find better name for this
// managed by: Advaya
public class Loginable {
    private String userName;
    private String password;

    Loginable(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUsername() { return userName; }
    public String getPassword() { return password; }
    public void setUserName(String new_username) {
        userName = new_username;
    }
    /**Allows the user to change their password if necessary**/
    public void setPassword(String new_password){
        password = new_password;
    }
}
