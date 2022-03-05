package com.hideyoshi.hideyoshiportfolio.service;

import com.hideyoshi.hideyoshiportfolio.domain.Client;
import com.hideyoshi.hideyoshiportfolio.repository.ClientRepository;
import com.hideyoshi.hideyoshiportfolio.utils.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> findAll() {
        return this.clientRepository.findAll();
    }

    public Client findByIndex(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User Not Found"));
    }
}
