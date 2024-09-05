package com.modusoftware.sitic.phoneProtect.user.persistent;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.modusoftware.sitic.phoneProtect.entity.PermissionEntity;

@Repository
public interface PermissionRepository extends CrudRepository<PermissionEntity, Long> {

   @Query(nativeQuery=true,
    	   value="SELECT DISTINCT P.NOMBRE " + 
    	   		" FROM PPP_INFORMACIONUSUARIO U, PPP_PERMISOS_ROL PR, PPP_PERMISOS P " + 
    	   		" WHERE U.USUARIO = :USERNAME " + 
    	   		" AND U.ESTADO = 1 " + 
    	   		" AND PR.ROL_ID = U.ID_ROL " + 
    	   		" AND PR.ESTADO = 1 " + 
    	   		" AND P.ID = PR.PERMISO_ID " + 
    	   		" AND P.ESTADO = 1")
   public List<String> findPermissionByUsername(@Param("USERNAME") String username); 
   
   @Query(nativeQuery=true,
		  value=" SELECT P.ID, P.NOMBRE, R.ID_ROL, R.NOMBREROL, " + 
 		        "   CASE" + 
 		        "     WHEN PR.ID IS NOT NULL THEN PR.ESTADO " + 
		 		"     ELSE 0 " + 
		 		"   END AS ACTIVO " + 
		 		"   FROM PPP_PERMISOS P, PPP_ROLES R, (SELECT ID, ROL_ID, PERMISO_ID, ESTADO FROM PPP_PERMISOS_ROL) PR " + 
		 		"   WHERE PR.PERMISO_ID (+) = P.ID " + 
		 		"   AND PR.ROL_ID (+) = R.ID_ROL " + 
		 		" ORDER BY P.NOMBRE ASC, R.NOMBREROL ASC ")
   public List<Object[]> findAllPermissionsXRole();
}
