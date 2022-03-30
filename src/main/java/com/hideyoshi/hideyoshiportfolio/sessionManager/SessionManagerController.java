package com.hideyoshi.hideyoshiportfolio.sessionManager;

import com.hideyoshi.hideyoshiportfolio.client.ClientDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionManagerController {

    private final SessionManagerService sessionManagerService;

    @GetMapping("/validate")
    public ResponseEntity<ClientDTO> validateCurrentSession(HttpSession session) {
        log.info(session.getId());
        return ResponseEntity.ok(this.sessionManagerService.validateSession(session));
    }

    @GetMapping("/destroy")
    public ResponseEntity<Void> destroyCurrentSession(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
