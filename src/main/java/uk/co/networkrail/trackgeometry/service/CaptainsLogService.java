package uk.co.networkrail.trackgeometry.service;

import uk.co.networkrail.trackgeometry.domain.CaptainsLog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CaptainsLog.
 */
public interface CaptainsLogService {

    /**
     * Save a captainsLog.
     *
     * @param captainsLog the entity to save
     * @return the persisted entity
     */
    CaptainsLog save(CaptainsLog captainsLog);

    /**
     * Get all the captainsLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CaptainsLog> findAll(Pageable pageable);


    /**
     * Get the "id" captainsLog.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CaptainsLog> findOne(Long id);

    /**
     * Delete the "id" captainsLog.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
