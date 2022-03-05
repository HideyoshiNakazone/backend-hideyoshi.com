package com.hideyoshi.hideyoshiportfolio.client;

import antlr.StringUtils;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;

public class ClientDTO {

    @NotEmpty
    @Column(name = "full_name")
    private String fullName;

    @NotEmpty
    private String email;

    @NotEmpty
    private String username;

    private String password;

    private List<String> roles;

    public ClientDTO() {}

    public ClientDTO(Client client) {
        this.setFullName(client.getFullName());
        this.setEmail(client.getEmail());
        this.setUsername(client.getUsername());
        this.password = client.getPassword();
        this.setRoles(client.getRoles());
    }

    public ClientDTO(String fullName, String email, String username, String password, String roles) {
        this.setFullName(fullName);
        this.setEmail(email);
        this.setUsername(username);
        this.setPassword(password);
        this.setRoles(roles);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = Arrays.asList(roles.split("$"));;
    }

    public void toEntity(Client entity) {
        entity.setFullName(this.getFullName());
        entity.setEmail(this.getEmail());
        entity.setUsername(this.getUsername());
        entity.setPassword(this.getPassword());
        entity.setRoles(String.join("$",this.getRoles()));
    }

    public Client toEntity() {
        Client entity = new Client();

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
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
