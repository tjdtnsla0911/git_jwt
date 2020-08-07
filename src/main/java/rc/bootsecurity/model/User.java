package rc.bootsecurity.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity //이거붙여서 jpa가 관리해줌
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private int active;

    private String roles = "";

    private String permissions = "";

    public User(String username, String password, String roles, String permissions){
    	System.out.println("User.java의 User에 왔습니다");
        this.username = username;
        this.password = password;
        this.roles = roles;
    
        this.permissions = permissions;
        this.active = 1;
    }

    protected User(){
    	System.out.println("User.java의 protected User에 왔습니다");
    }

    public long getId() {
    	System.out.println("User.java의 protected getId에 왔습니다 id값은 = "+id);
        return id;
    }

    public String getUsername() {
     	System.out.println("User.java의 protected getUsername에 왔습니다 username값은 = "+username);
        return username;
    }

    public String getPassword() {
      	System.out.println("User.java의 protected getPassword에 왔습니다 password값은 = "+password);
        return password;
    }

    public int getActive() {
      	System.out.println("User.java의 protected getActive에 왔습니다 active값은 = "+active);
        return active;
    }

    public String getRoles() {
        return roles;
    }

    public String getPermissions() {
        return permissions;
    }

    public List<String> getRoleList(){
    	System.out.println("여긴 model.User.java의 getRoleList 입니다");
        if(this.roles.length() > 0){
        	System.out.println("여긴 model.User.java의 getRoleList의 if문의 Arrays.asList(this.roles.split(\",\")) = "+Arrays.asList(this.roles.split(",")));
            return Arrays.asList(this.roles.split(","));

        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList(){
    	System.out.println("여긴 model.User.java의 getPermissionList 입니다");
        if(this.permissions.length() > 0){
        	System.out.println("여긴 model.User.java의 getPermissionList의 if문의 this.permissions.split(\",\") = "+this.permissions.split(","));
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }
}
