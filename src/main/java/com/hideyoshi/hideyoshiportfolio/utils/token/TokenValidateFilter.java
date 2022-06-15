package com.hideyoshi.hideyoshiportfolio.utils.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hideyoshi.hideyoshiportfolio.utils.config.TokenConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static com.hideyoshi.hideyoshiportfolio.utils.config.TokenConfig.TOKEN_SECRET;

@Log4j2
public class TokenValidateFilter extends BasicAuthenticationFilter {

    @Autowired
    private TokenConfig tokenConfig;

    private static final String HEADER_ATRIBUTE = "Authorization";

    private static final String HEADER_PREFIX = "Bearer ";

    public TokenValidateFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        String attribute = request.getHeader(HEADER_ATRIBUTE);
        if (!Objects.nonNull(attribute)) {
            chain.doFilter(request, response);
        } else if (!attribute.startsWith(HEADER_PREFIX)) {
            chain.doFilter(request, response);
        } else {
            UsernamePasswordAuthenticationToken authenticationToken =
                    this.getAuthenticationToken(attribute.replace(HEADER_PREFIX, ""));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);
        }
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        String username = JWT.require(Algorithm.HMAC512(TOKEN_SECRET)).build().verify(token).getSubject();
        log.info(username);
        return Objects.nonNull(username)
                ? new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>())
                : null;
    }

}
