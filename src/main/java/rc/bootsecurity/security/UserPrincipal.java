package rc.bootsecurity.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import rc.bootsecurity.model.User;

public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 1L;

	private User user;

    public UserPrincipal(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	System.out.println("여긴 security.UserPrincipal.getAuthorities입니다");
        List<GrantedAuthority> authorities = new ArrayList<>();
    	System.out.println("여긴 security.UserPrincipal.getAuthorities의 authorities = "+authorities);

        // Extract list of permissions (name)
        this.user.getPermissionList().forEach(p -> {
            GrantedAuthority authority = new SimpleGrantedAuthority(p);
        	System.out.println("여긴 security.UserPrincipal.getAuthorities의 authorities가 forEach p->뭐시기 되고나서 = "+authorities);
            authorities.add(authority);
        });

        // Extract list of roles (ROLE_name)
        this.user.getRoleList().forEach(r -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + r);
        	System.out.println("여긴 security.UserPrincipal.getAuthorities의 authorities가 forEach r->뭐시기 되고나서 = "+authorities);
            authorities.add(authority);
        });
    	System.out.println("여긴 security.UserPrincipal.getAuthorities의 authorities의 최종 =  "+authorities);

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
    	System.out.println("여긴 security.UserPrincipal.isEnabled입니다");
        return this.user.getActive() == 1;
    }
}
