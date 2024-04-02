package com.app.auth.persistence.repositories;

import com.app.auth.persistence.entities.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository1 extends CrudRepository<RoleEntity, Long> {

    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roleNames);
}