package uk.co.networkrail.trackgeometry.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.co.networkrail.trackgeometry.domain.ELR;
import uk.co.networkrail.trackgeometry.service.ELRService;
import uk.co.networkrail.trackgeometry.web.rest.errors.BadRequestAlertException;
import uk.co.networkrail.trackgeometry.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ELR.
 */
@RestController
@RequestMapping("/api")
public class ELRResource {

    private final Logger log = LoggerFactory.getLogger(ELRResource.class);

    private static final String ENTITY_NAME = "eLR";

    private final ELRService eLRService;

    public ELRResource(ELRService eLRService) {
        this.eLRService = eLRService;
    }

    /**
     * POST  /elrs : Create a new eLR.
     *
     * @param eLR the eLR to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eLR, or with status 400 (Bad Request) if the eLR has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/elrs")
    @Timed
    public ResponseEntity<ELR> createELR(@Valid @RequestBody ELR eLR) throws URISyntaxException {
        log.debug("REST request to save ELR : {}", eLR);
        if (eLR.getId() != null) {
            throw new BadRequestAlertException("A new eLR cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ELR result = eLRService.save(eLR);
        return ResponseEntity.created(new URI("/api/elrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /elrs : Updates an existing eLR.
     *
     * @param eLR the eLR to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eLR,
     * or with status 400 (Bad Request) if the eLR is not valid,
     * or with status 500 (Internal Server Error) if the eLR couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/elrs")
    @Timed
    public ResponseEntity<ELR> updateELR(@Valid @RequestBody ELR eLR) throws URISyntaxException {
        log.debug("REST request to update ELR : {}", eLR);
        if (eLR.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ELR result = eLRService.save(eLR);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eLR.getId().toString()))
            .body(result);
    }

    /**
     * GET  /elrs : get all the eLRS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of eLRS in body
     */
    @GetMapping("/elrs")
    @Timed
    public List<ELR> getAllELRS() {
        log.debug("REST request to get all ELRS");
        return eLRService.findAll();
    }

    /**
     * GET  /elrs/:id : get the "id" eLR.
     *
     * @param id the id of the eLR to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eLR, or with status 404 (Not Found)
     */
    @GetMapping("/elrs/{id}")
    @Timed
    public ResponseEntity<ELR> getELR(@PathVariable Long id) {
        log.debug("REST request to get ELR : {}", id);
        Optional<ELR> eLR = eLRService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eLR);
    }

    /**
     * DELETE  /elrs/:id : delete the "id" eLR.
     *
     * @param id the id of the eLR to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/elrs/{id}")
    @Timed
    public ResponseEntity<Void> deleteELR(@PathVariable Long id) {
        log.debug("REST request to delete ELR : {}", id);
        eLRService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
