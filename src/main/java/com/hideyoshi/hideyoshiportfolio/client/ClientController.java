package com.hideyoshi.hideyoshiportfolio.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(path = "/{username}")
    public ResponseEntity<ClientDTO> findByUsername(@PathVariable String username) {
        return ResponseEntity.ok(this.clientService.findByUsername(username));
    }

    @PostMapping
    public ResponseEntity<ClientDTO> save(@RequestBody ClientDTO client) {
        log.info(client.toEntity().toString());
        return ResponseEntity.ok(client);
    }

}
