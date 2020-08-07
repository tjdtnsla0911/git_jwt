package rc.bootsecurity.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rc.bootsecurity.db.UserRepository;
import rc.bootsecurity.model.User;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public UserPrincipalDetailsService(UserRepository userRepository) {
    	System.out.println("security.UserPrincipalDetailsService에 왔습니다  ");
        this.userRepository = userRepository;
    	System.out.println("security.UserPrincipalDetailsService의UserPrincipalDetailsService.  userRepository= "+userRepository);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    	System.out.println("security.UserPrincipalDetailsService.loadUserByUsername에 왔습니다 ");
        User user = this.userRepository.findByUsername(s);
    	System.out.println("security.UserPrincipalDetailsService.loadUserByUsername의 user =  "+user);
        UserPrincipal userPrincipal = new UserPrincipal(user);
    	System.out.println("security.UserPrincipalDetailsService의loadUserByUsername의   userRepository= "+userRepository);
        return userPrincipal;
    }
}
