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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modusoftware.commons.ws.rest.MSGenericRestController;
import com.modusoftware.commons.ws.rest.MSWSRestResponseAdapter;
import com.modusoftware.commons.ws.util.exception.MSWSException;
import com.modusoftware.sitic.phoneProtect.domain.UserDTO;
import com.modusoftware.sitic.phoneProtect.user.business.LoginService;
import com.modusoftware.sitic.phoneProtect.user.business.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/user")
@Api(value = "Api para gestionar los usuarios. ")
public class UserRestService extends MSGenericRestController {

	private static final Logger logger = LoggerFactory.getLogger(UserRestService.class);

	@Autowired
	private MSWSRestResponseAdapter responseAdapter;

	@Autowired
	private UserService service;

	@Autowired
	private LoginService loginService;

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/username/{username:.+}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Consultar por nombre de usuario.")
	public ResponseEntity<UserDTO> findByUsername(HttpServletRequest req,
			@ApiParam("Nombre de usuario (username) a consultar.") @PathVariable(name = "username", required = true) String username)
			throws MSWSException {

		// Obtiene el ID de transaccion
		final String ID_TRANSACTION = req.getAttribute(this.idTransaction).toString();

		logger.info("Ejecutando findByUsername para username {} ID_TRANSACTION {}",
				new Object[] { username, ID_TRANSACTION });

		try {
			return (ResponseEntity<UserDTO>) responseAdapter.getResponseRest(HttpStatus.OK, ID_TRANSACTION,
					this.service.findByUsername(username), null);
		} catch (Throwable e) {
			logger.error("Error in findByUsername.", e);

			return (ResponseEntity<UserDTO>) super.manageException(e, ID_TRANSACTION);
		}
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/permission/username/{username:.+}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Consultar los permisos por nombre de usuario.")
	public ResponseEntity<List<String>> findPermissionByUsername(HttpServletRequest req,
			@ApiParam("Nombre de usuario (username) a consultar.") @PathVariable(name = "username", required = true) String username)
			throws MSWSException {

		// Obtiene el ID de transaccion
		final String ID_TRANSACTION = req.getAttribute(this.idTransaction).toString();

		logger.info("Ejecutando findPermissionByUsername para username {} ID_TRANSACTION {}",
				new Object[] { username, ID_TRANSACTION });

		try {
			return (ResponseEntity<List<String>>) responseAdapter.getResponseRest(HttpStatus.OK, ID_TRANSACTION,
					this.service.findPermissionByUsername(username), null);
		} catch (Throwable e) {
			logger.error("Error in findPermissionByUsername.", e);

			return (ResponseEntity<List<String>>) super.manageException(e, ID_TRANSACTION);
		}
	}

	@SuppressWarnings("unchecked")
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Registrar (crear/actualizar) un usuario. Retorna el ID del usuario registrado.")
	@ApiResponse(code = 204, message = "No existe el rol asignado")
	public ResponseEntity<Long> register(HttpServletRequest req, @RequestBody(required = true) @Valid UserDTO dto)
			throws MSWSException {

		// Obtiene el ID de transaccion
		final String ID_TRANSACTION = req.getAttribute(this.idTransaction).toString();

		logger.info("Ejecutando findPermissionByUsername para user {} ID_TRANSACTION {}",
				new Object[] { dto, ID_TRANSACTION });

		try {
			return (ResponseEntity<Long>) responseAdapter.getResponseRest(HttpStatus.OK, ID_TRANSACTION,
					this.service.register(dto), null);
		} catch (Throwable e) {
			logger.error("Error in register.", e);

			return (ResponseEntity<Long>) super.manageException(e, ID_TRANSACTION);
		}
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/login/username/{username:.+}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Registrar conexión (Login). Retorna 1=Exitoso, 0=Fallido")
	public ResponseEntity<Integer> registrarLogin(HttpServletRequest req,
			@ApiParam("Nombre de usuario (username) a registrar.") @PathVariable(name = "username", required = true) String username)
			throws MSWSException {

		// Obtiene el ID de transaccion
		final String ID_TRANSACTION = req.getAttribute(this.idTransaction).toString();

		logger.info("Ejecutando registro de login para username {} ID_TRANSACTION {}",
				new Object[] { username, ID_TRANSACTION });

		try {
			return (ResponseEntity<Integer>) responseAdapter.getResponseRest(HttpStatus.OK, ID_TRANSACTION,
					this.loginService.registrarLogin(username), null);
		} catch (Throwable e) {
			logger.error("Error in registrarLogin.", e);

			return (ResponseEntity<Integer>) super.manageException(e, ID_TRANSACTION);
		}
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/logout/username/{username:.+}")
	@ApiOperation(value = "Registrar desconexión (Logout).")
	public ResponseEntity<Void> registrarLogout(HttpServletRequest req,
			@ApiParam("Nombre de usuario (username) a registrar.") @PathVariable(name = "username", required = true) String username)
			throws MSWSException {

		// Obtiene el ID de transaccion
		final String ID_TRANSACTION = req.getAttribute(this.idTransaction).toString();

		logger.info("Ejecutando registro de logout para username {} ID_TRANSACTION {}",
				new Object[] { username, ID_TRANSACTION });

		try {
			this.loginService.registrarLogout(username);

			return (ResponseEntity<Void>) responseAdapter.getResponseRest(HttpStatus.OK, ID_TRANSACTION, null, null);
		} catch (Throwable e) {
			logger.error("Error in registrarLogout.", e);

			return (ResponseEntity<Void>) super.manageException(e, ID_TRANSACTION);
		}
	}
}
