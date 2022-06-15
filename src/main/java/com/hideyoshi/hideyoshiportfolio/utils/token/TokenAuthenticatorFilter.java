package com.hideyoshi.hideyoshiportfolio.utils.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hideyoshi.hideyoshiportfolio.client.Client;
import com.hideyoshi.hideyoshiportfolio.client.ClientDTO;
import com.hideyoshi.hideyoshiportfolio.utils.config.TokenConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.hideyoshi.hideyoshiportfolio.utils.config.TokenConfig.TOKEN_DURATION;
import static com.hideyoshi.hideyoshiportfolio.utils.config.TokenConfig.TOKEN_SECRET;

@Log4j2
public class TokenAuthenticatorFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private TokenConfig tokenConfig;
    private final AuthenticationManager authenticationManager;

    public TokenAuthenticatorFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            ClientDTO client = new ObjectMapper().readValue(request.getInputStream(), ClientDTO.class);
            return this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            client.getUsername(),
                            client.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException("Falha na autenticação do usuário: "+e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {

        ClientDTO client = new ClientDTO((Client) authResult.getPrincipal());

        String token = JWT.create()
                .withSubject(client.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+TOKEN_DURATION))
                .sign(Algorithm.HMAC512(TOKEN_SECRET));

        response.getWriter().write(token);
        response.getWriter().flush();
    }
}
