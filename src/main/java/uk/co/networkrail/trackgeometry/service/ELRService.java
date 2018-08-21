package uk.co.networkrail.trackgeometry.service;

import uk.co.networkrail.trackgeometry.domain.ELR;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ELR.
 */
public interface ELRService {

    /**
     * Save a eLR.
     *
     * @param eLR the entity to save
     * @return the persisted entity
     */
    ELR save(ELR eLR);

    /**
     * Get all the eLRS.
     *
     * @return the list of entities
     */
    List<ELR> findAll();


    /**
     * Get the "id" eLR.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ELR> findOne(Long id);

    /**
     * Delete the "id" eLR.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
