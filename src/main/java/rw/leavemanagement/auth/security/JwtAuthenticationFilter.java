package rw.leavemanagement.auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * This class is responsible for filtering and authenticating JWT tokens in the incoming requests.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    /**
     * Filters and authenticates the incoming request based on the JWT token.
     *
     * @param httpServletRequest  The incoming HTTP request.
     * @param httpServletResponse The outgoing HTTP response.
     * @param filterChain         The filter chain to continue processing the request.
     * @throws ServletException If an error occurs while processing the request.
     * @throws IOException      If an I/O error occurs while processing the request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (httpServletRequest.getMethod().equals("OPTIONS")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        } else {
            try {
                String jwt = getJwtFromRequest(httpServletRequest);
                if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                    String userID = tokenProvider.getUserIdFromToken(jwt);
                    UserDetails userDetails = customUserDetailsService.loadByUserId(userID);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (Exception e) {
                logger.error("Could not set user authentication in security context", e);
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    /**
     * Retrieves the JWT token from the Authorization header of the request.
     *
     * @param request The incoming HTTP request.
     * @return The JWT token extracted from the request, or null if not found.
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}