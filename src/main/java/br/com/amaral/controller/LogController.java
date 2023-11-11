package br.com.amaral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.amaral.service.ImplementationUserDetailsService;
import br.com.amaral.service.LogService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class LogController<T> {

    private static final Logger LOGGER = Logger.getLogger(LogController.class.getName());

    @Autowired
    private LogService logService;
    
    private ImplementationUserDetailsService security;

    private final Class<T> entityClass;

    public LogController(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    @ResponseBody
	@PostMapping(value = "**/log")
    public ResponseEntity<String> logEntity(@RequestBody T entity) {
    	
    	String userId = security.getCurrentUserId();
        long timestamp = System.currentTimeMillis();

        LOGGER.log(Level.INFO, "Received request to log {0}. User ID: {1}, Timestamp: {2}, Request: {3}",
                new Object[]{entityClass.getSimpleName(), userId, timestamp, entity});

        logService.logEntity(userId, timestamp, entityClass.getSimpleName(), entity);

        return new ResponseEntity<>(entityClass.getSimpleName() + " logged successfully", HttpStatus.OK);
    }
    
    @ResponseBody
	@PostMapping(value = "**/log-list")
    public ResponseEntity<String> logEntityList(@RequestBody List<T> entityList) {
    	
    	String userId = security.getCurrentUserId();
        long timestamp = System.currentTimeMillis();

        LOGGER.log(Level.INFO, "Received request to log {0} list. User ID: {1}, Timestamp: {2}, Request: {3}",
                new Object[]{userId, timestamp, entityList});

        logService.logEntityList(userId, timestamp, entityList);

        return new ResponseEntity<>(entityClass.getSimpleName() + " list logged successfully", HttpStatus.OK);
    }
}