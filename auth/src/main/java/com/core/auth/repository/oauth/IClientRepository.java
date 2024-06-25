package com.core.auth.repository.oauth;


import com.core.auth.entity.oauth.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository<Client, Long> {

    Client findOneByClientId(String clientId);

}

