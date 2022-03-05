package com.hideyoshi.hideyoshiportfolio.client;

import com.hideyoshi.hideyoshiportfolio.client.Client;
import com.hideyoshi.hideyoshiportfolio.client.ClientRepository;
import com.hideyoshi.hideyoshiportfolio.utils.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<ClientDTO> findAll() {
        return this.clientRepository.listAll();
    }

    public ClientDTO findByUsername(String username) {
        try {
            return clientRepository.findByUsername(username);
        } catch(Exception e) {
            throw(new BadRequestException("User not Found"));
        }
    }

    public ClientDTO save(ClientDTO clientPost) {
        return clientRepository.save(clientPost.toEntity());
    }
}
