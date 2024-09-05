package com.modusoftware.sitic.phoneProtect.user.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.modusoftware.sitic.phoneProtect.domain.RoleDTO;
import com.modusoftware.sitic.phoneProtect.entity.RoleEntity;
import com.modusoftware.sitic.phoneProtect.user.business.RoleService;
import com.modusoftware.sitic.phoneProtect.user.persistent.RoleRepository;

@Service
@Primary
public class RoleServiceImpl implements RoleService {

    public static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository roleRepository;
    
    @Override
    public List<RoleDTO> findAllRoles() {
        List<RoleDTO> roles = new ArrayList<>();
        
        for(RoleEntity role : this.roleRepository.findAll_1()) {
        	roles.add(new RoleDTO(role.getId(), role.getName()));
        }
    	
    	return roles;
    }
}
