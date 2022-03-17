package com.hideyoshi.hideyoshiportfolio.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/client")
@Log4j2
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll() {
        return ResponseEntity.ok(this.clientService.findAll());
    }

    @GetMapping(path = "/validation")
    public ResponseEntity<ClientDTO> findByUsername(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(this.clientService.findByUsernameForValidation(userDetails.getUsername()));
    }

    @PostMapping("/admin")
    public ResponseEntity<ClientDTO> save(@RequestBody ClientDTO client) {
        return new ResponseEntity<>(this.clientService.save(client), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> alter(@RequestBody ClientDTO client,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        if (this.clientService.findByUsername(userDetails.getUsername())
                .getEmail().equals(client.getEmail())
                    || userDetails.getAuthorities().toString().contains("ROLE_ADMIN")) {
            this.clientService.alter(client);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/admin/{email}")
    public ResponseEntity<Void> delete(@PathVariable final String email) {
        this.clientService.delete(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
