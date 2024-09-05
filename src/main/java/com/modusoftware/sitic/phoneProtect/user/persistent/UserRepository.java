package com.modusoftware.sitic.phoneProtect.user.persistent;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.modusoftware.sitic.phoneProtect.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    public UserEntity findByUsername(String username);
    
}
