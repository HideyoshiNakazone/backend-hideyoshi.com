package com.hideyoshi.hideyoshiportfolio.client;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class ClientDTO {

    private Long id;

    private String fullName;

    @NotEmpty
    private String email;

    private String username;

    private String password;

    private List<String> roles;

    public ClientDTO(Client client) {
        this.setId(client.getId());
        this.setFullName(client.getFullName());
        this.setEmail(client.getEmail());
        this.setUsername(client.getUsername());
        this.setPasswordHashed(client.getPassword());
        this.setRoles(client.getRoles());
    }

    public ClientDTO(String fullName, String email, String username, String password, String roles) {
        if (Objects.nonNull(fullName)) {
            this.setFullName(fullName);
        }

        this.setEmail(email);

        if (Objects.nonNull(username)) {
            this.setUsername(username);
        }
        if (Objects.nonNull(password)) {
            this.setPassword(password);
        }
        if (Objects.nonNull(roles)) {
            this.setRoles(roles);
        }
    }

    public void setPassword(String password) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public void setPasswordHashed(String password) {
        if (password.contains("{bcrypt}")) {
            this.password = password;
        }
    }

    public void setRoles(String roles) {
        this.roles = new ArrayList<>(Arrays.asList(roles.split("\\$")));
    }

    public Client toEntity() {
        Client entity = new Client();

        entity.setId(this.getId());
        entity.setFullName(this.getFullName());
        entity.setEmail(this.getEmail());
        entity.setUsername(this.getUsername());
        entity.setPassword(this.getPassword());
        entity.setRoles(String.join("$",this.getRoles()));

        return entity;
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
