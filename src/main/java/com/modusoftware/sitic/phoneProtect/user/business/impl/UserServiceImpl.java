package com.modusoftware.sitic.phoneProtect.user.business.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.modusoftware.commons.ws.util.exception.NoDataFoundException;
import com.modusoftware.sitic.phoneProtect.domain.UserDTO;
import com.modusoftware.sitic.phoneProtect.domain.constant.BooleanValues;
import com.modusoftware.sitic.phoneProtect.entity.RoleEntity;
import com.modusoftware.sitic.phoneProtect.entity.UserEntity;
import com.modusoftware.sitic.phoneProtect.user.business.UserService;
import com.modusoftware.sitic.phoneProtect.user.persistent.PermissionRepository;
import com.modusoftware.sitic.phoneProtect.user.persistent.RoleRepository;
import com.modusoftware.sitic.phoneProtect.user.persistent.UserRepository;

@Service
@Primary
public class UserServiceImpl implements UserService {

    public static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Long register(UserDTO dto) throws NoDataFoundException{
    	UserEntity user = this.userRepository.findByUsername(dto.getUsername());
    	
    	//Si no existe lo crea
    	if(user == null) {
    		user = new UserEntity();
    		user.setCreationDate(new Date());
    		user.setStatus(BooleanValues.YES.getCode());
    		user.setUsername(dto.getUsername());
    	}else {
    		//Si el rol no cambia, no debe actualiza nada
    		if(user.getRole().getName().equals(dto.getRoleName())) {
    			return user.getId();
    		}
    	}
    	
    	RoleEntity role = this.roleRepository.findByName(dto.getRoleName());
		
		if(role != null) {
			user.setRole(role);
			
			user = this.userRepository.save(user);
			
			return user.getId();
		}else {
			throw new NoDataFoundException(String.format("Role %s not found", dto.getRoleName()), null);
		}
    }

    @Override
    public UserDTO findByUsername(String username) throws NoDataFoundException {
        UserEntity user = this.userRepository.findByUsername(username);
        
        if(user != null) {
        	UserDTO dto = new UserDTO();
        	dto.setId(user.getId());
        	dto.setUsername(username);
        	dto.setIdRole(user.getRole().getId());
        	dto.setRoleName(user.getRole().getName());
        	dto.setActive(BooleanValues.valueOf(user.getStatus()).getBool());
        	return dto;
        }else {
        	throw new NoDataFoundException(String.format("User %s not found", username), null); 
        }
    }
    
    @Override
    public List<String> findPermissionByUsername(String username) {
        return this.permissionRepository.findPermissionByUsername(username);
    }
}
