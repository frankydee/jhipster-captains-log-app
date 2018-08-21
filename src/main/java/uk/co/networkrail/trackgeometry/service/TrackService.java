package uk.co.networkrail.trackgeometry.service;

import uk.co.networkrail.trackgeometry.domain.Track;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Track.
 */
public interface TrackService {

    /**
     * Save a track.
     *
     * @param track the entity to save
     * @return the persisted entity
     */
    Track save(Track track);

    /**
     * Get all the tracks.
     *
     * @return the list of entities
     */
    List<Track> findAll();


    /**
     * Get the "id" track.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Track> findOne(Long id);

    /**
     * Delete the "id" track.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
