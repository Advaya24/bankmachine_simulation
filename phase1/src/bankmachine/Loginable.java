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
}
