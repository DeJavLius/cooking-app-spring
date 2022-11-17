package teamproject.capstone.recipe.utils.login;

import com.google.firebase.auth.*;
import lombok.AllArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.NoSuchElementException;

@AllArgsConstructor
public class FirebaseTokenFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final FirebaseAuth firebaseAuth;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        FirebaseToken decodedToken;
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            setUnauthorizedResponse(response, "INVALID_HEADER");
            return;
        }
        String token = header.substring(7);

        try{
            decodedToken = firebaseAuth.verifyIdToken(token);
        } catch (FirebaseAuthException e) {
            setUnauthorizedResponse(response, "INVALID_TOKEN");
            return;
        }

        try{
            UserDetails user = userDetailsService.loadUserByUsername(decodedToken.getUid());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch(NoSuchElementException e){
            setUnauthorizedResponse(response, "USER_NOT_FOUND");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void setUnauthorizedResponse(HttpServletResponse response, String code) throws IOException {
        response.setStatus(HttpStatus.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"code\":\""+code+"\"}");
    }
}
