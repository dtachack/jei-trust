package com.modusoftware.sitic.phoneProtect.user.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.modusoftware.sitic.phoneProtect.domain.RoleDTO;

@Service
public interface RoleService {
    
    /**Metodo que consulta todos los roles
     *  
     * @return List<RoleDTO>
     */
    public List<RoleDTO> findAllRoles();
}
