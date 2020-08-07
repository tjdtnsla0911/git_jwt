package rc.bootsecurity.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;

import rc.bootsecurity.db.UserRepository;
import rc.bootsecurity.model.User;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {

        super(authenticationManager);
    	System.out.println("security.JwtAuthorizationFilter.java의 JwtAuthorizationFilter에 왔고  userRepository = "+userRepository);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    	System.out.println("security.JwtAuthorizationFilter.java의 doFilterInternal에 왔습니다");
        // Read the Authorization header, where the JWT token should be
        String header = request.getHeader(JwtProperties.HEADER_STRING);
    	System.out.println("security.JwtAuthorizationFilter.java의 doFilterInternal의 header = "+header);

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
        	System.out.println("security.JwtAuthorizationFilter.java의 doFilterInternal의 if문");
            return;
        }

        // If header is present, try grab user principal from database and perform authorization
        Authentication authentication = getUsernamePasswordAuthentication(request);
    	System.out.println("security.JwtAuthorizationFilter.java의 doFilterInternal의 authentication ="+authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        // Continue filter execution
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
    	System.out.println("security.JwtAuthorizationFilter.java의 getUsernamePasswordAuthentication에 왔습니다.");
        String token = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX,"");
    	System.out.println("security.JwtAuthorizationFilter.java의 getUsernamePasswordAuthentication의 token = "+token);
        if (token != null) {
            // parse the token and validate it
            String userName = JWT.require(HMAC512(JwtProperties.SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();
        	System.out.println("security.JwtAuthorizationFilter.java의 getUsernamePasswordAuthentication의 token != null userName= "+userName);

            // Search in the DB if we find the user by token subject (username)
            // If so, then grab user details and create spring auth token using username, pass, authorities/roles
            if (userName != null) {
            	System.out.println("security.JwtAuthorizationFilter.java의 getUsernamePasswordAuthentication의 userName != null에 왔습니다.");
                User user = userRepository.findByUsername(userName);
                System.out.println("security.JwtAuthorizationFilter.java의 getUsernamePasswordAuthentication의 userName != null의 user = "+user);
                UserPrincipal principal = new UserPrincipal(user);
                System.out.println("security.JwtAuthorizationFilter.java의 getUsernamePasswordAuthentication의 userName != null의 principal = "+principal);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName, null, principal.getAuthorities());
                System.out.println("security.JwtAuthorizationFilter.java의 getUsernamePasswordAuthentication의 userName != null의 auth = "+auth);
                return auth;
            }
            return null;
        }
        return null;
    }
}
