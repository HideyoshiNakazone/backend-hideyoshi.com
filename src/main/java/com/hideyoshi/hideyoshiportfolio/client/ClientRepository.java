package com.hideyoshi.hideyoshiportfolio.repository;

import com.hideyoshi.hideyoshiportfolio.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByUsername(String username);

}
