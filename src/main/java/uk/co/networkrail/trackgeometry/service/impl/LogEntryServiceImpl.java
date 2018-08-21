package uk.co.networkrail.trackgeometry.service.impl;

import uk.co.networkrail.trackgeometry.service.LogEntryService;
import uk.co.networkrail.trackgeometry.domain.LogEntry;
import uk.co.networkrail.trackgeometry.repository.LogEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing LogEntry.
 */
@Service
@Transactional
public class LogEntryServiceImpl implements LogEntryService {

    private final Logger log = LoggerFactory.getLogger(LogEntryServiceImpl.class);

    private final LogEntryRepository logEntryRepository;

    public LogEntryServiceImpl(LogEntryRepository logEntryRepository) {
        this.logEntryRepository = logEntryRepository;
    }

    /**
     * Save a logEntry.
     *
     * @param logEntry the entity to save
     * @return the persisted entity
     */
    @Override
    public LogEntry save(LogEntry logEntry) {
        log.debug("Request to save LogEntry : {}", logEntry);        return logEntryRepository.save(logEntry);
    }

    /**
     * Get all the logEntries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LogEntry> findAll(Pageable pageable) {
        log.debug("Request to get all LogEntries");
        return logEntryRepository.findAll(pageable);
    }


    /**
     * Get one logEntry by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LogEntry> findOne(Long id) {
        log.debug("Request to get LogEntry : {}", id);
        return logEntryRepository.findById(id);
    }

    /**
     * Delete the logEntry by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LogEntry : {}", id);
        logEntryRepository.deleteById(id);
    }
}
