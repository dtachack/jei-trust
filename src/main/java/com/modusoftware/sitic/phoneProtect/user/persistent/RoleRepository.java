package com.modusoftware.sitic.phoneProtect.user.persistent;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.modusoftware.sitic.phoneProtect.entity.RoleEntity;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    public RoleEntity findByName(String name);
    
    @Query(nativeQuery=false,
    		value="SELECT new RoleEntity(r.id, r.name) " +
    		      "FROM RoleEntity r " +
    			  "ORDER BY r.name ASC")
    public List<RoleEntity> findAll_1();
    
}
