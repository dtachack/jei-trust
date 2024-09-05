package com.modusoftware.sitic.phoneProtect.user.ws;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modusoftware.commons.util.exception.NoDataFoundException;
import com.modusoftware.commons.ws.rest.MSGenericRestController;
import com.modusoftware.commons.ws.rest.MSWSRestResponseAdapter;
import com.modusoftware.commons.ws.util.exception.MSWSException;
import com.modusoftware.sitic.phoneProtect.domain.PermissionXRoleDTO;
import com.modusoftware.sitic.phoneProtect.user.business.PermissionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/permission")
@Api(value = "Api para gestionar los permisos de los usuarios.")
public class PermissionRestService extends MSGenericRestController {

	private static final Logger logger = LoggerFactory.getLogger(PermissionRestService.class);

	@Autowired
	private MSWSRestResponseAdapter responseAdapter;

	@Autowired
	private PermissionService service;

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/x/role", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Consultar todos los permisos por rol.")
	public ResponseEntity<List<PermissionXRoleDTO>> findAllPermissionsXRole(HttpServletRequest req)
			throws MSWSException {

		// Obtiene el ID de transaccion
		final String ID_TRANSACTION = req.getAttribute(this.idTransaction).toString();

		logger.info("Ejecutando findAllPermissionsXRole para ID_TRANSACTION {}", ID_TRANSACTION);

		try {
			return (ResponseEntity<List<PermissionXRoleDTO>>) responseAdapter.getResponseRest(HttpStatus.OK,
					ID_TRANSACTION, this.service.findAllPermissionsXRole(), null);
		} catch (Throwable e) {
			logger.error("Error in findAllPermissionsXRole.", e);

			return (ResponseEntity<List<PermissionXRoleDTO>>) super.manageException(e, ID_TRANSACTION);
		}
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/x/role", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Crear/actualizar permisos por rol. Retorna el ID del permiso X rol creado/actualizado.")
	@ApiResponses({ @ApiResponse(code = 409, message = "No existe el permiso o el rol asociado a crear/actualizar.") })
	public ResponseEntity<Long> createUpdatePermissionsXRole(HttpServletRequest req,
			@RequestBody(required = true) @Valid PermissionXRoleDTO dto) throws MSWSException {

		// Obtiene el ID de transaccion
		final String ID_TRANSACTION = req.getAttribute(this.idTransaction).toString();

		logger.info("Ejecutando createUpdatePermissionsXRole para PermissionXRoleDTO {} ID_TRANSACTION {}",
				new Object[] { dto, ID_TRANSACTION });

		try {
			return (ResponseEntity<Long>) responseAdapter.getResponseRest(HttpStatus.OK, ID_TRANSACTION,
					this.service.createUpdatePermissionsXRole(dto), null);
		} catch (NoDataFoundException e) {
			logger.error("Error in createUpdatePermissionsXRole.", e);

			return (ResponseEntity<Long>) responseAdapter.getResponseRest(HttpStatus.CONFLICT, ID_TRANSACTION, null,
					super.getMessageBundle("pp.ws.msg.warn.permissionOrRole.notFound"), e);
		} catch (Throwable e) {
			logger.error("Error in createUpdatePermissionsXRole.", e);

			return (ResponseEntity<Long>) super.manageException(e, ID_TRANSACTION);
		}
	}
}
