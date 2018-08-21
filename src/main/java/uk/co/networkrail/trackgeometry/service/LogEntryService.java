package uk.co.networkrail.trackgeometry.service;

import uk.co.networkrail.trackgeometry.domain.LogEntry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing LogEntry.
 */
public interface LogEntryService {

    /**
     * Save a logEntry.
     *
     * @param logEntry the entity to save
     * @return the persisted entity
     */
    LogEntry save(LogEntry logEntry);

    /**
     * Get all the logEntries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LogEntry> findAll(Pageable pageable);


    /**
     * Get the "id" logEntry.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LogEntry> findOne(Long id);

    /**
     * Delete the "id" logEntry.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
