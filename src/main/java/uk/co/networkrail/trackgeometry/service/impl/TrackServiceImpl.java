package uk.co.networkrail.trackgeometry.service.impl;

import uk.co.networkrail.trackgeometry.service.TrackService;
import uk.co.networkrail.trackgeometry.domain.Track;
import uk.co.networkrail.trackgeometry.repository.TrackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Track.
 */
@Service
@Transactional
public class TrackServiceImpl implements TrackService {

    private final Logger log = LoggerFactory.getLogger(TrackServiceImpl.class);

    private final TrackRepository trackRepository;

    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    /**
     * Save a track.
     *
     * @param track the entity to save
     * @return the persisted entity
     */
    @Override
    public Track save(Track track) {
        log.debug("Request to save Track : {}", track);        return trackRepository.save(track);
    }

    /**
     * Get all the tracks.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Track> findAll() {
        log.debug("Request to get all Tracks");
        return trackRepository.findAll();
    }


    /**
     * Get one track by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Track> findOne(Long id) {
        log.debug("Request to get Track : {}", id);
        return trackRepository.findById(id);
    }

    /**
     * Delete the track by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Track : {}", id);
        trackRepository.deleteById(id);
    }
}
