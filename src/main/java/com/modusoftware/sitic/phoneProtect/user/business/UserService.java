package com.modusoftware.sitic.phoneProtect.user.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.modusoftware.commons.ws.util.exception.NoDataFoundException;
import com.modusoftware.sitic.phoneProtect.domain.UserDTO;

@Service
public interface UserService {

	/**Metodo que registra un usuario
	 * 
	 * @param dto
	 * 
	 * @return Long ID del usuario
	 * 
	 * @throws NoDataFoundException No se encontro el rol
	 */
	public Long register(UserDTO dto) throws NoDataFoundException;
    
	/**Metodo que consulta un usuario por username
	 * 
	 * @param username
	 * 
	 * @return UserDTO
	 * 
	 * @throws NoDataFoundException Usuario no encontrado
	 */
    public UserDTO findByUsername(String username) throws NoDataFoundException;
    
    /**Metodo que consulta los permisos por usuario
     * 
     * @param username
     * 
     * @return List<String> Listado de permisos
     */
    public List<String> findPermissionByUsername(String username);
}
