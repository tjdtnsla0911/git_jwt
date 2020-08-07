package rc.bootsecurity.model;

public class LoginViewModel {
    private String username;
    private String password;

    public String getPassword() {
    	System.out.println("model.LoginViewModel의 getPassword에왔고 password = "+password);
        return password;
    }
    public void setPassword(String password) {
    	System.out.println("model.LoginViewModel의 setPassword에왔고 password = "+password);

        this.password = password;
    }
    public String getUsername() {
     	System.out.println("model.LoginViewModel의 getUsername에왔고 username = "+username);
        return username;
    }
    public void setUsername(String username) {
     	System.out.println("model.LoginViewModel의 setUsername에왔고 username = "+username);
        this.username = username;
    }
}
