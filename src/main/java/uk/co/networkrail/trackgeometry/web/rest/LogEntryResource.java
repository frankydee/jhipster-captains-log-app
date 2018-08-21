package uk.co.networkrail.trackgeometry.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.co.networkrail.trackgeometry.domain.LogEntry;
import uk.co.networkrail.trackgeometry.service.LogEntryService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LogEntry.
 */
@RestController
@RequestMapping("/api")
public class LogEntryResource {

    private final Logger log = LoggerFactory.getLogger(LogEntryResource.class);

    private static final String ENTITY_NAME = "logEntry";

    private final LogEntryService logEntryService;

    public LogEntryResource(LogEntryService logEntryService) {
        this.logEntryService = logEntryService;
    }

    /**
     * POST  /log-entries : Create a new logEntry.
     *
     * @param logEntry the logEntry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new logEntry, or with status 400 (Bad Request) if the logEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/log-entries")
    @Timed
    public ResponseEntity<LogEntry> createLogEntry(@RequestBody LogEntry logEntry) throws URISyntaxException {
        log.debug("REST request to save LogEntry : {}", logEntry);
        if (logEntry.getId() != null) {
            throw new BadRequestAlertException("A new logEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogEntry result = logEntryService.save(logEntry);
        return ResponseEntity.created(new URI("/api/log-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /log-entries : Updates an existing logEntry.
     *
     * @param logEntry the logEntry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated logEntry,
     * or with status 400 (Bad Request) if the logEntry is not valid,
     * or with status 500 (Internal Server Error) if the logEntry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/log-entries")
    @Timed
    public ResponseEntity<LogEntry> updateLogEntry(@RequestBody LogEntry logEntry) throws URISyntaxException {
        log.debug("REST request to update LogEntry : {}", logEntry);
        if (logEntry.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LogEntry result = logEntryService.save(logEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, logEntry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /log-entries : get all the logEntries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of logEntries in body
     */
    @GetMapping("/log-entries")
    @Timed
    public ResponseEntity<List<LogEntry>> getAllLogEntries(Pageable pageable) {
        log.debug("REST request to get a page of LogEntries");
        Page<LogEntry> page = logEntryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/log-entries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /log-entries/:id : get the "id" logEntry.
     *
     * @param id the id of the logEntry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the logEntry, or with status 404 (Not Found)
     */
    @GetMapping("/log-entries/{id}")
    @Timed
    public ResponseEntity<LogEntry> getLogEntry(@PathVariable Long id) {
        log.debug("REST request to get LogEntry : {}", id);
        Optional<LogEntry> logEntry = logEntryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(logEntry);
    }

    /**
     * DELETE  /log-entries/:id : delete the "id" logEntry.
     *
     * @param id the id of the logEntry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/log-entries/{id}")
    @Timed
    public ResponseEntity<Void> deleteLogEntry(@PathVariable Long id) {
        log.debug("REST request to delete LogEntry : {}", id);
        logEntryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
