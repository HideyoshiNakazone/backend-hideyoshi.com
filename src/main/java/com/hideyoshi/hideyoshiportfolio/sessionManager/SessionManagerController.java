package com.hideyoshi.hideyoshiportfolio.sessionManager;

import com.hideyoshi.hideyoshiportfolio.client.Client;
import com.hideyoshi.hideyoshiportfolio.client.ClientDTO;
import com.hideyoshi.hideyoshiportfolio.client.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Log4j2
@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
public class SessionManagerController {

    private final ClientService clientService;

    @GetMapping("/validate")
    public ResponseEntity<ClientDTO> persistClientOnSession(HttpServletRequest request) {

        ClientDTO sessionObject = (ClientDTO) request.getSession(true).getAttribute("client");
        if (Objects.nonNull(sessionObject)) {
            ClientDTO client = clientService.findByUsername(sessionObject.getUsername());

            client.setId(null);
            client.setRoles(null);
            client.setPasswordRaw(null);

            log.info(client.toString());

            return ResponseEntity.ok(client);
        } else {
            return null;
        }
    }

    @GetMapping("/destroy")
    public ResponseEntity<Void> destroyCurrentSession(HttpServletRequest request) {
        request.getSession(false).invalidate();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
