package br.com.amaral.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class LogService {

    private static final Logger LOGGER = Logger.getLogger(LogService.class.getName());

    public <T> void logEntity(String userId, long timestamp, String entityType, T entity) {
        LOGGER.log(Level.INFO, "{0} logged successfully. User ID: {1}, Timestamp: {2}, Entity: {3}",
                new Object[]{entityType, userId, timestamp, entity});
    }
    
    public <T> void logEntityList(String userId, long timestamp, List<T> entityList) {
        LOGGER.log(Level.INFO, "{0} list logged successfully. User ID: {1}, Timestamp: {2}, Entity List: {3}",
                new Object[]{userId, timestamp, entityList});
    }

}
