package com.app.auth.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.auth.persistence.entities.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByUsername(String username);

}
