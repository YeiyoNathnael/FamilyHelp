package nathnael.yeiyo.adu.ac.ae.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException exception) {
		return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage());
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<Map<String, String>> handleUnauthorized(UnauthorizedException exception) {
		return buildResponse(HttpStatus.UNAUTHORIZED, exception.getMessage());
	}

	@ExceptionHandler(RaftConsensusException.class)
	public ResponseEntity<Map<String, String>> handleRaft(RaftConsensusException exception) {
		return buildResponse(HttpStatus.CONFLICT, exception.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGeneric(Exception exception) {
		return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
	}

	private ResponseEntity<Map<String, String>> buildResponse(HttpStatus status, String message) {
		Map<String, String> body = new HashMap<>();
		body.put("message", message);
		return new ResponseEntity<>(body, status);
	}

}
