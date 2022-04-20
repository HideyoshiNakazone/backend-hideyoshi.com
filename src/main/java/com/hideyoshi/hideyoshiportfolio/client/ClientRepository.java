package com.hideyoshi.hideyoshiportfolio.client;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Repository
public class ClientRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private List<Selection<?>> fieldsForQuery(Root<Client> from) {
        return Arrays.asList(
                from.get(Client_.fullName),
                from.get(Client_.email),
                from.get(Client_.username)
        );
    }

    public List<ClientDTO> listAll() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();

        CriteriaQuery<ClientDTO> criteria = cb.createQuery(ClientDTO.class);
        Root<Client> from = criteria.from(Client.class);

        criteria.multiselect(from);

        TypedQuery<ClientDTO> query = this.entityManager.createQuery(criteria);

        return query.getResultList();
    }

    public ClientDTO findByUsername(String username) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientDTO> criteria = cb.createQuery(ClientDTO.class);
        Root<Client> from = criteria.from(Client.class);

        criteria.multiselect(fieldsForQuery(from)).where(cb.equal(from.get(Client_.username), username));

        TypedQuery<ClientDTO> query = this.entityManager.createQuery(criteria);

        return query.getSingleResult();
    }

    public ClientDTO findByUsernameValidation(String username) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientDTO> criteria = cb.createQuery(ClientDTO.class);
        Root<Client> from = criteria.from(Client.class);

        criteria.multiselect(from).where(cb.equal(from.get(Client_.username), username));

        TypedQuery<ClientDTO> query = this.entityManager.createQuery(criteria);

        return query.getSingleResult();
    }

    public ClientDTO findByEmail(String email) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientDTO> criteria = cb.createQuery(ClientDTO.class);
        Root<Client> from = criteria.from(Client.class);

        criteria.multiselect(from).where(cb.equal(from.get(Client_.email), email));

        TypedQuery<ClientDTO> query = this.entityManager.createQuery(criteria);

        return query.getSingleResult();
    }

    @Transactional
    public ClientDTO save(final @Valid Client client) {
        this.entityManager.persist(client);
        this.entityManager.flush();

        ClientDTO clientSaved = new ClientDTO(client);
        
        return findByUsername(clientSaved.getUsername());
    }

    @Transactional
    public void alter(final Client client) {
        this.entityManager.merge(client);
        this.entityManager.flush();
    }

    @Transactional
    public void delete(final Client client) {
        this.entityManager.remove(
                        this.entityManager.contains(client)
                        ? client
                        : entityManager.merge(client));
        this.entityManager.flush();
    }
}
