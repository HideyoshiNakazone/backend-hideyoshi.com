package com.hideyoshi.hideyoshiportfolio.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO  implements Serializable {

    private static final long serialVersionUID = -6547685895783886314L;

    private Long id;

    private String fullName;

    private String email;

    private String username;

    private String password;

    private List<String> roles;

    public ClientDTO(Client client) {
        this.setId(client.getId());
        this.setFullName(client.getFullName());
        this.setEmail(client.getEmail());
        this.setUsername(client.getUsername());
        this.setPasswordRaw(client.getPassword());
        this.setRoles(client.getRoles());
    }

    public ClientDTO(String fullName, String email, String username, String password, String roles) {
        this.setFullName(fullName);
        this.setEmail(email);
        this.setUsername(username);
        this.setPassword(password);
        this.setRoles(roles);
    }

    public ClientDTO(String fullName, String email, String username) {
        this.setFullName(fullName);
        this.setEmail(email);
        this.setUsername(username);
    }

    public void setPassword(String password) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public void setPasswordRaw(String password) {
        this.password = password;
    }

    public void setRoles(String roles) {
        if (Objects.nonNull(roles)) {
            this.roles = new ArrayList<>(Arrays.asList(roles.split("\\$")));
        } else {
            this.roles = null;
        }
    }

    public Client toEntity() {
        Client entity = new Client();

        entity.setId(this.getId());
        entity.setFullName(this.getFullName());
        entity.setEmail(this.getEmail());
        entity.setUsername(this.getUsername());
        entity.setPassword(this.getPassword());
        entity.setRoles(String.join("$",Objects.nonNull(this.getRoles()) ? this.getRoles() : Arrays.asList("ROLE_USER")));

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
