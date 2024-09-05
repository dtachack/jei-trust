package com.modusoftware.sitic.phoneProtect.user.persistent.sp;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

@Repository
public class RegistrarLoginStoredProcedure extends StoredProcedure implements InitializingBean{		
	
	private final String P_USERNAME = "P_USERNAME";
	private final String P_VALIDA_DIFF_MINS = "P_VALIDA_DIFF_MINS";
	private final String P_IP = "P_IP";
	private final String P_RESULT = "P_RESULT";
	
	public RegistrarLoginStoredProcedure(
			@Value("${pp.ws.user.persistent.sp.registrarLogin.timeout}") int timeout,
			@Value("${pp.ws.user.persistent.sp.registrarLogin.sql}") String sql,
			@Autowired DataSource ds) {
		super();
		super.setDataSource(ds);
		super.setFunction(false);
		super.setQueryTimeout(timeout);
		super.setSql(sql);
		
		//IN
		super.declareParameter(new SqlParameter(P_USERNAME, Types.VARCHAR));
		super.declareParameter(new SqlParameter(P_VALIDA_DIFF_MINS, Types.NUMERIC));
		super.declareParameter(new SqlParameter(P_IP, Types.VARCHAR));	
		
		//OUT
		super.declareParameter(new SqlOutParameter(P_RESULT, Types.NUMERIC));
	}
	
	public BigDecimal execute(String username, long validaDiffMins, String ip){
		MapSqlParameterSource pm = new MapSqlParameterSource();
		pm.addValue(P_USERNAME, username);
		pm.addValue(P_VALIDA_DIFF_MINS, validaDiffMins);
		pm.addValue(P_IP, ip);
		
		Map<String, Object> res = super.execute(pm.getValues());
		
		return (BigDecimal) res.get(P_RESULT); 
	}
}
