package com.hideyoshi.hideyoshiportfolio.client;

import com.hideyoshi.hideyoshiportfolio.utils.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClientService implements UserDetailsService {

    private final ClientRepository clientRepository;

    public List<ClientDTO> findAll() {
        return this.clientRepository.listAll();
    }

    public ClientDTO findByUsername(String username) {
        return Optional.of(clientRepository.findByUsername(username))
                .orElseThrow(() -> new BadRequestException("User not Found"));
    }

    public ClientDTO findByUsernameForValidation(String username) {
        ClientDTO clientSaved = clientRepository.findByUsername(username);
        clientSaved.setId(null);
        clientSaved.setPasswordRaw(null);
        clientSaved.setRoles(null);
        return Optional.of(clientSaved)
                .orElseThrow(() -> new BadRequestException("User not Found"));
    }

    public ClientDTO findByEmail(String email) {
        return Optional.of(this.clientRepository.findByEmail(email))
                .orElseThrow(() -> new BadRequestException("User not Found"));
    }

    public ClientDTO save(ClientDTO clientPost) {
        if (!Objects.nonNull(this.findByEmail(clientPost.getEmail()))) {
            return clientRepository.save(clientPost.toEntity());
        } else {
            throw new BadRequestException("Client Account Already Exists");
        }
    }

    public void alter(ClientDTO clientPut) {

        ClientDTO clientSavedOnDB = this.findByEmail(clientPut.getEmail());

        if(!Objects.nonNull(clientPut.getId())) {
            clientPut.setId(clientSavedOnDB.getId());
        }
        if (!Objects.nonNull(clientPut.getFullName())) {
            clientPut.setFullName(clientSavedOnDB.getFullName());
        }
        if (!Objects.nonNull(clientPut.getEmail())) {
            clientPut.setEmail(clientSavedOnDB.getEmail());
        }
        if (!Objects.nonNull(clientPut.getUsername())) {
            clientPut.setUsername(clientSavedOnDB.getUsername());
        }
        if (!Objects.nonNull(clientPut.getPassword())) {
            clientPut.setPasswordRaw(clientSavedOnDB.getPassword());
        }
        if (!Objects.nonNull(clientPut.getRoles())) {
            clientPut.setRoles(String.join("$",clientSavedOnDB.getRoles()));
        }
        log.info(clientPut.toString());
        this.clientRepository.alter(clientPut.toEntity());

    }

    public void delete(String email) {
        this.clientRepository.delete(this.findByEmail(email).toEntity());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(this.clientRepository.findByUsername(username).toEntity())
                .orElseThrow(() -> new UsernameNotFoundException("Client not found"));
    }
}
