package com.modusoftware.sitic.phoneProtect.user.persistent.sp;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

@Repository
public class RegistrarLogoutStoredProcedure extends StoredProcedure implements InitializingBean{		
	
	private final String P_USERNAME = "P_USERNAME";
	
	public RegistrarLogoutStoredProcedure(
			@Value("${pp.ws.user.persistent.sp.registrarLogout.timeout}") int timeout,
			@Value("${pp.ws.user.persistent.sp.registrarLogout.sql}") String sql,
			@Autowired DataSource ds) {
		super();
		super.setDataSource(ds);
		super.setFunction(false);
		super.setQueryTimeout(timeout);
		super.setSql(sql);
		
		//IN
		super.declareParameter(new SqlParameter(P_USERNAME, Types.VARCHAR));
	}
	
	public void execute(String username, long validaDiffMins){
		MapSqlParameterSource pm = new MapSqlParameterSource();
		pm.addValue(P_USERNAME, username);		
		
		super.execute(pm.getValues()); 
	}
}
