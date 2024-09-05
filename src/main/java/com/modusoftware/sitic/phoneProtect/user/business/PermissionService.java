package com.modusoftware.sitic.phoneProtect.user.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.modusoftware.sitic.phoneProtect.domain.PermissionXRoleDTO;

@Service
public interface PermissionService {
    
    /**Metodo que consulta todos los permisos por rol
     *  
     * @return List<PermissionXRoleDTO>
     */
	public List<PermissionXRoleDTO> findAllPermissionsXRole();
	
	/**Metodo que crea/actualiza un permiso por rol
	 * 
	 * @param dto PermissionXRoleDTO
	 * 
	 * @return ID del permiso por rol
	 */
	public Long createUpdatePermissionsXRole(PermissionXRoleDTO dto);
}
