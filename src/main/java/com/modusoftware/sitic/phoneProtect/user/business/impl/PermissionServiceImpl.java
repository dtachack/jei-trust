package com.modusoftware.sitic.phoneProtect.user.business.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.modusoftware.commons.util.exception.NoDataFoundException;
import com.modusoftware.sitic.phoneProtect.domain.PermissionXRoleDTO;
import com.modusoftware.sitic.phoneProtect.entity.PermissionRoleEntity;
import com.modusoftware.sitic.phoneProtect.user.business.PermissionService;
import com.modusoftware.sitic.phoneProtect.user.persistent.PermissionRepository;
import com.modusoftware.sitic.phoneProtect.user.persistent.PermissionRoleRepository;

@Service
@Primary
public class PermissionServiceImpl implements PermissionService {

    public static final Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class);
    
    private final int ID_PERMISSION = 0;
    private final int DESC_PERMISSION = 1;
    private final int ID_ROLE = 2;
    private final int DESC_ROLE = 3;
    private final int ACTIVE = 4;

    @Autowired
    private PermissionRepository permissionRepository;
    
    @Autowired
    private PermissionRoleRepository permissionRoleRepository;
    
    @Override
    public List<PermissionXRoleDTO> findAllPermissionsXRole() {
        List<PermissionXRoleDTO> roles = new ArrayList<>();
        
        for(Object[] reg : this.permissionRepository.findAllPermissionsXRole()) {
        	roles.add(new PermissionXRoleDTO(((BigDecimal)reg[ID_ROLE]).longValue(), (String)reg[DESC_ROLE], ((BigDecimal)reg[ID_PERMISSION]).longValue(), (String)reg[DESC_PERMISSION], ((BigDecimal)reg[ACTIVE]).intValue()));
        }
    	
    	return roles;
    }
    
    @Override
    public Long createUpdatePermissionsXRole(PermissionXRoleDTO dto) {
        try {
	    	PermissionRoleEntity entity = this.permissionRoleRepository.findByPermissionIdAndRoleId(dto.getIdPermission().longValue(), dto.getIdRole().longValue());
	        
	        if(entity == null) {
	        	entity = new PermissionRoleEntity();
	        	entity.setPermissionId(dto.getIdPermission().longValue());
	        	entity.setRoleId(dto.getIdRole().longValue());
	        	entity.setStatus(dto.getActive());
	        }
	        
	        entity.setStatus(dto.getActive());
	    	
	    	entity = this.permissionRoleRepository.save(entity);
	    	
	    	return entity.getId();
        }catch (DataIntegrityViolationException e) {
			if(e.getCause() != null && e.getCause() instanceof ConstraintViolationException) {
				throw new NoDataFoundException(String.format("Permission %d or Role %d not found", dto.getIdPermission().longValue(), dto.getIdRole().longValue()));
			}
			
			throw e;
		}
    }
}
