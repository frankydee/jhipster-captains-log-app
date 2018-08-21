package uk.co.networkrail.trackgeometry.service.impl;

import uk.co.networkrail.trackgeometry.service.CaptainsLogService;
import uk.co.networkrail.trackgeometry.domain.CaptainsLog;
import uk.co.networkrail.trackgeometry.repository.CaptainsLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing CaptainsLog.
 */
@Service
@Transactional
public class CaptainsLogServiceImpl implements CaptainsLogService {

    private final Logger log = LoggerFactory.getLogger(CaptainsLogServiceImpl.class);

    private final CaptainsLogRepository captainsLogRepository;

    public CaptainsLogServiceImpl(CaptainsLogRepository captainsLogRepository) {
        this.captainsLogRepository = captainsLogRepository;
    }

    /**
     * Save a captainsLog.
     *
     * @param captainsLog the entity to save
     * @return the persisted entity
     */
    @Override
    public CaptainsLog save(CaptainsLog captainsLog) {
        log.debug("Request to save CaptainsLog : {}", captainsLog);        return captainsLogRepository.save(captainsLog);
    }

    /**
     * Get all the captainsLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CaptainsLog> findAll(Pageable pageable) {
        log.debug("Request to get all CaptainsLogs");
        return captainsLogRepository.findAll(pageable);
    }


    /**
     * Get one captainsLog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CaptainsLog> findOne(Long id) {
        log.debug("Request to get CaptainsLog : {}", id);
        return captainsLogRepository.findById(id);
    }

    /**
     * Delete the captainsLog by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CaptainsLog : {}", id);
        captainsLogRepository.deleteById(id);
    }
}
