package rc.bootsecurity.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rc.bootsecurity.model.LoginViewModel;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

//로그인시에 JWT가 작동함. SecurityConfiguration에서 설정!!
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    	System.out.println("security.JwtAuthenticationFilter.java의 JwtAuthenticationFilter입니다 ");
        this.authenticationManager = authenticationManager;
    	System.out.println("security.JwtAuthenticationFilter.java의 JwtAuthenticationFilter의 authenticationManager= "+authenticationManager);
    }

    /* Trigger when we issue POST request to /login
    We also need to pass in {"username":"dan", "password":"dan123"} in the request body
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    	System.out.println("security.JwtAuthenticationFilter.java의 attemptAuthentication에 왓습니다.");
        // Grab credentials and map them to login viewmodel
        LoginViewModel credentials = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginViewModel.class);
          	System.out.println("security.JwtAuthenticationFilter.java의 attemptAuthentication의 credentials = "+credentials);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create login token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword(),
                new ArrayList<>());
    	System.out.println("security.JwtAuthenticationFilter.java의 attemptAuthentication의 authenticationToken = "+authenticationToken);

        // Authenticate user
        Authentication auth = authenticationManager.authenticate(authenticationToken);
    	System.out.println("security.JwtAuthenticationFilter.java의 attemptAuthentication의 auth = "+auth);

        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    	System.out.println("security.JwtAuthenticationFilter.java의 successfulAuthentication에 왓습니다.");
    	// Grab principal
        UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();
    	System.out.println("security.JwtAuthenticationFilter.java의 successfulAuthentication의 principal = "+principal);
        // Create JWT Token
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .sign(HMAC512(JwtProperties.SECRET.getBytes()));
    	System.out.println("security.JwtAuthenticationFilter.java의 successfulAuthentication의 token = "+token);
        // Add token in response
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX +  token);

    }
}