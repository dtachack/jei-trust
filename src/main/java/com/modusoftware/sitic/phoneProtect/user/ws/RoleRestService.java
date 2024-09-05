package com.modusoftware.sitic.phoneProtect.user.ws;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modusoftware.commons.ws.rest.MSGenericRestController;
import com.modusoftware.commons.ws.rest.MSWSRestResponseAdapter;
import com.modusoftware.commons.ws.util.exception.MSWSException;
import com.modusoftware.sitic.phoneProtect.domain.RoleDTO;
import com.modusoftware.sitic.phoneProtect.user.business.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/role")
@Api(value = "Api para gestionar los roles del sistema.")
public class RoleRestService extends MSGenericRestController {

	private static final Logger logger = LoggerFactory.getLogger(RoleRestService.class);

	@Autowired
	private MSWSRestResponseAdapter responseAdapter;

	@Autowired
	private RoleService service;

	@SuppressWarnings("unchecked")
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Consultar todos los roles.")
	public ResponseEntity<List<RoleDTO>> findAll(HttpServletRequest req) throws MSWSException {

		// Obtiene el ID de transaccion
		final String ID_TRANSACTION = req.getAttribute(this.idTransaction).toString();

		logger.info("Ejecutando findAll para ID_TRANSACTION {}", ID_TRANSACTION);

		try {
			return (ResponseEntity<List<RoleDTO>>) responseAdapter.getResponseRest(HttpStatus.OK, ID_TRANSACTION,
					this.service.findAllRoles(), null);
		} catch (Throwable e) {
			logger.error("Error in findAll.", e);

			return (ResponseEntity<List<RoleDTO>>) super.manageException(e, ID_TRANSACTION);
		}
	}
}
