package uk.co.networkrail.trackgeometry.service.impl;

import uk.co.networkrail.trackgeometry.service.ELRService;
import uk.co.networkrail.trackgeometry.domain.ELR;
import uk.co.networkrail.trackgeometry.repository.ELRRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing ELR.
 */
@Service
@Transactional
public class ELRServiceImpl implements ELRService {

    private final Logger log = LoggerFactory.getLogger(ELRServiceImpl.class);

    private final ELRRepository eLRRepository;

    public ELRServiceImpl(ELRRepository eLRRepository) {
        this.eLRRepository = eLRRepository;
    }

    /**
     * Save a eLR.
     *
     * @param eLR the entity to save
     * @return the persisted entity
     */
    @Override
    public ELR save(ELR eLR) {
        log.debug("Request to save ELR : {}", eLR);        return eLRRepository.save(eLR);
    }

    /**
     * Get all the eLRS.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ELR> findAll() {
        log.debug("Request to get all ELRS");
        return eLRRepository.findAll();
    }


    /**
     * Get one eLR by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ELR> findOne(Long id) {
        log.debug("Request to get ELR : {}", id);
        return eLRRepository.findById(id);
    }

    /**
     * Delete the eLR by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ELR : {}", id);
        eLRRepository.deleteById(id);
    }
}
