package rc.bootsecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import rc.bootsecurity.db.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserPrincipalDetailsService userPrincipalDetailsService;
    private UserRepository userRepository;

    public SecurityConfiguration(UserPrincipalDetailsService userPrincipalDetailsService, UserRepository userRepository) {
    	System.out.println("여긴 securty.SecurityConfiguration.java의 SecurityConfiguration입니다 ");
        this.userPrincipalDetailsService = userPrincipalDetailsService;
        this.userRepository = userRepository;
        System.out.println("여긴 securty.SecurityConfiguration의 SecurityConfiguration의 userPrincipalDetailsService =  "+userPrincipalDetailsService);
        System.out.println("여긴 securty.SecurityConfiguration의 SecurityConfiguration의 userRepository =  "+userRepository);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
    	System.out.println("여긴 securty.SecurityConfiguration의 configure입니다 ");
        auth.authenticationProvider(authenticationProvider());
        System.out.println("여긴 securty.SecurityConfiguration의  auth.authenticationProvider(authenticationProvider())입니다 =  "+ auth.authenticationProvider(authenticationProvider()));

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	System.out.println("여긴 securty.SecurityConfiguration의 configure입니다(오버로딩된곳) ");
        http
                // remove csrf and state in session because in jwt we do not need them
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // add jwt filters (1. authentication, 2. authorization)
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(),  this.userRepository))
                .authorizeRequests()
                // configure access rules
                //.antMatchers("*").permitAll();
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/api/public/management/*").hasRole("MANAGER")
                .antMatchers("/api/public/admin/*").hasRole("ADMIN")
                .anyRequest().authenticated();
    	System.out.println("여긴 securty.SecurityConfiguration의 configure입니다(오버로딩된곳)가 끝낫고 http = "+http);
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
    	System.out.println("여긴 securty.SecurityConfiguration의 authenticationProvider입니다 ");
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    	System.out.println("여긴 securty.SecurityConfiguration의 daoAuthenticationProvider =  " + daoAuthenticationProvider);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);
     	System.out.println("여긴 securty.SecurityConfiguration의 daoAuthenticationProvider가 다끝나고나서  =  " + daoAuthenticationProvider);

        return daoAuthenticationProvider;
    }
    @Bean
    PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
