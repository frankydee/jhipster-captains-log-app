package uk.co.networkrail.trackgeometry.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.co.networkrail.trackgeometry.domain.CaptainsLog;
import uk.co.networkrail.trackgeometry.service.CaptainsLogService;
import uk.co.networkrail.trackgeometry.web.rest.errors.BadRequestAlertException;
import uk.co.networkrail.trackgeometry.web.rest.util.HeaderUtil;
import uk.co.networkrail.trackgeometry.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CaptainsLog.
 */
@RestController
@RequestMapping("/api")
public class CaptainsLogResource {

    private final Logger log = LoggerFactory.getLogger(CaptainsLogResource.class);

    private static final String ENTITY_NAME = "captainsLog";

    private final CaptainsLogService captainsLogService;

    public CaptainsLogResource(CaptainsLogService captainsLogService) {
        this.captainsLogService = captainsLogService;
    }

    /**
     * POST  /captains-logs : Create a new captainsLog.
     *
     * @param captainsLog the captainsLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new captainsLog, or with status 400 (Bad Request) if the captainsLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/captains-logs")
    @Timed
    public ResponseEntity<CaptainsLog> createCaptainsLog(@Valid @RequestBody CaptainsLog captainsLog) throws URISyntaxException {
        log.debug("REST request to save CaptainsLog : {}", captainsLog);
        if (captainsLog.getId() != null) {
            throw new BadRequestAlertException("A new captainsLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CaptainsLog result = captainsLogService.save(captainsLog);
        return ResponseEntity.created(new URI("/api/captains-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /captains-logs : Updates an existing captainsLog.
     *
     * @param captainsLog the captainsLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated captainsLog,
     * or with status 400 (Bad Request) if the captainsLog is not valid,
     * or with status 500 (Internal Server Error) if the captainsLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/captains-logs")
    @Timed
    public ResponseEntity<CaptainsLog> updateCaptainsLog(@Valid @RequestBody CaptainsLog captainsLog) throws URISyntaxException {
        log.debug("REST request to update CaptainsLog : {}", captainsLog);
        if (captainsLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CaptainsLog result = captainsLogService.save(captainsLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, captainsLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /captains-logs : get all the captainsLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of captainsLogs in body
     */
    @GetMapping("/captains-logs")
    @Timed
    public ResponseEntity<List<CaptainsLog>> getAllCaptainsLogs(Pageable pageable) {
        log.debug("REST request to get a page of CaptainsLogs");
        Page<CaptainsLog> page = captainsLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/captains-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /captains-logs/:id : get the "id" captainsLog.
     *
     * @param id the id of the captainsLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the captainsLog, or with status 404 (Not Found)
     */
    @GetMapping("/captains-logs/{id}")
    @Timed
    public ResponseEntity<CaptainsLog> getCaptainsLog(@PathVariable Long id) {
        log.debug("REST request to get CaptainsLog : {}", id);
        Optional<CaptainsLog> captainsLog = captainsLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(captainsLog);
    }

    /**
     * DELETE  /captains-logs/:id : delete the "id" captainsLog.
     *
     * @param id the id of the captainsLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/captains-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCaptainsLog(@PathVariable Long id) {
        log.debug("REST request to delete CaptainsLog : {}", id);
        captainsLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
