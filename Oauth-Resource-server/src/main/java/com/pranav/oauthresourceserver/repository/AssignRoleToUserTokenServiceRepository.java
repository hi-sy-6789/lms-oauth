package com.pranav.oauthresourceserver.repository;

import com.pranav.oauthresourceserver.entity.AssignRoleToUserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssignRoleToUserTokenServiceRepository extends JpaRepository<AssignRoleToUserToken, Long> {

    Optional<AssignRoleToUserToken> findByToken(String token);
}
