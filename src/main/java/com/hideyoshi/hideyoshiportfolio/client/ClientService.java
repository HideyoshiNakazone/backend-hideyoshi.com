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
        return Optional.ofNullable(this.clientRepository.findByUsername(username))
                .orElseThrow(() -> new BadRequestException("Client not found"));
    }

    public ClientDTO findByEmail(String email) {
        return Optional.of(this.clientRepository.findByEmail(email))
                .orElseThrow(() -> new BadRequestException("User not Found"));
    }

    public ClientDTO save(ClientDTO clientPost) {
        try{
            return clientRepository.save(clientPost.toEntity());
        } catch (Exception e) {
            throw new BadRequestException("Client Account Already Exists");
        }
    }

    public void alter(ClientDTO clientPut) {

        ClientDTO clientSavedOnDB = this.findByEmail(clientPut.getEmail());

        if (Objects.nonNull(clientPut.getFullName())) {
            clientSavedOnDB.setFullName(clientPut.getFullName());
        }
        if (Objects.nonNull(clientPut.getUsername())) {
            clientSavedOnDB.setUsername(clientPut.getUsername());
        }

        log.info(clientSavedOnDB.toString());

        this.clientRepository.alter(clientSavedOnDB.toEntity());

    }

    public void delete(String email) {
        this.clientRepository.delete(this.findByEmail(email).toEntity());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(this.clientRepository.findByUsernameValidation(username).toEntity())
                .orElseThrow(() -> new UsernameNotFoundException("Client not found"));
    }
}
