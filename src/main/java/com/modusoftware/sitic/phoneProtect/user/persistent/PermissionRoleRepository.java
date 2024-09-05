package com.modusoftware.sitic.phoneProtect.user.persistent;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.modusoftware.sitic.phoneProtect.entity.PermissionRoleEntity;

@Repository
public interface PermissionRoleRepository extends CrudRepository<PermissionRoleEntity, Long> {

	public PermissionRoleEntity findByPermissionIdAndRoleId(Long permissionId, Long roleId);

}
