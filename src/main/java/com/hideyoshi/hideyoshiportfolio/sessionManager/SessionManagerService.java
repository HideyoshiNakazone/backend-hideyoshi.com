package com.hideyoshi.hideyoshiportfolio.sessionManager;

import com.hideyoshi.hideyoshiportfolio.client.ClientDTO;
import com.hideyoshi.hideyoshiportfolio.client.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class SessionManagerService implements TomcatConnectorCustomizer {

    private final ClientService clientService;

    public ClientDTO validateSession(HttpSession session) {
        ClientDTO sessionObject = (ClientDTO) session.getAttribute("client");
        if (Objects.nonNull(sessionObject)) {
            log.info(sessionObject.toString());
            return clientService.findByUsername((sessionObject).getUsername());
        } else {
            return null;
        }
    }

    @Override
    public void customize(Connector connector) {
        for (final SSLHostConfig hostConfig : connector.findSslHostConfigs()) {
            hostConfig.setSessionTimeout(2 * 24 * 60 * 60);
        }
    }

}
