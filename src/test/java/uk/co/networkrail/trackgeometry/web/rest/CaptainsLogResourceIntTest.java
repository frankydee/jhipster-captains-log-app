package uk.co.networkrail.trackgeometry.web.rest;

import uk.co.networkrail.trackgeometry.CaptainsLogApp;

import uk.co.networkrail.trackgeometry.domain.CaptainsLog;
import uk.co.networkrail.trackgeometry.repository.CaptainsLogRepository;
import uk.co.networkrail.trackgeometry.service.CaptainsLogService;
import uk.co.networkrail.trackgeometry.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static uk.co.networkrail.trackgeometry.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CaptainsLogResource REST controller.
 *
 * @see CaptainsLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaptainsLogApp.class)
public class CaptainsLogResourceIntTest {

    private static final Instant DEFAULT_RUN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RUN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RST = "AAAAAAAAAA";
    private static final String UPDATED_RST = "BBBBBBBBBB";

    private static final Instant DEFAULT_LOAD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOAD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CaptainsLogRepository captainsLogRepository;

    

    @Autowired
    private CaptainsLogService captainsLogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCaptainsLogMockMvc;

    private CaptainsLog captainsLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CaptainsLogResource captainsLogResource = new CaptainsLogResource(captainsLogService);
        this.restCaptainsLogMockMvc = MockMvcBuilders.standaloneSetup(captainsLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CaptainsLog createEntity(EntityManager em) {
        CaptainsLog captainsLog = new CaptainsLog()
            .runDate(DEFAULT_RUN_DATE)
            .rst(DEFAULT_RST)
            .loadDate(DEFAULT_LOAD_DATE);
        return captainsLog;
    }

    @Before
    public void initTest() {
        captainsLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createCaptainsLog() throws Exception {
        int databaseSizeBeforeCreate = captainsLogRepository.findAll().size();

        // Create the CaptainsLog
        restCaptainsLogMockMvc.perform(post("/api/captains-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(captainsLog)))
            .andExpect(status().isCreated());

        // Validate the CaptainsLog in the database
        List<CaptainsLog> captainsLogList = captainsLogRepository.findAll();
        assertThat(captainsLogList).hasSize(databaseSizeBeforeCreate + 1);
        CaptainsLog testCaptainsLog = captainsLogList.get(captainsLogList.size() - 1);
        assertThat(testCaptainsLog.getRunDate()).isEqualTo(DEFAULT_RUN_DATE);
        assertThat(testCaptainsLog.getRst()).isEqualTo(DEFAULT_RST);
        assertThat(testCaptainsLog.getLoadDate()).isEqualTo(DEFAULT_LOAD_DATE);
    }

    @Test
    @Transactional
    public void createCaptainsLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = captainsLogRepository.findAll().size();

        // Create the CaptainsLog with an existing ID
        captainsLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaptainsLogMockMvc.perform(post("/api/captains-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(captainsLog)))
            .andExpect(status().isBadRequest());

        // Validate the CaptainsLog in the database
        List<CaptainsLog> captainsLogList = captainsLogRepository.findAll();
        assertThat(captainsLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRunDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = captainsLogRepository.findAll().size();
        // set the field null
        captainsLog.setRunDate(null);

        // Create the CaptainsLog, which fails.

        restCaptainsLogMockMvc.perform(post("/api/captains-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(captainsLog)))
            .andExpect(status().isBadRequest());

        List<CaptainsLog> captainsLogList = captainsLogRepository.findAll();
        assertThat(captainsLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCaptainsLogs() throws Exception {
        // Initialize the database
        captainsLogRepository.saveAndFlush(captainsLog);

        // Get all the captainsLogList
        restCaptainsLogMockMvc.perform(get("/api/captains-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(captainsLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].runDate").value(hasItem(DEFAULT_RUN_DATE.toString())))
            .andExpect(jsonPath("$.[*].rst").value(hasItem(DEFAULT_RST.toString())))
            .andExpect(jsonPath("$.[*].loadDate").value(hasItem(DEFAULT_LOAD_DATE.toString())));
    }
    

    @Test
    @Transactional
    public void getCaptainsLog() throws Exception {
        // Initialize the database
        captainsLogRepository.saveAndFlush(captainsLog);

        // Get the captainsLog
        restCaptainsLogMockMvc.perform(get("/api/captains-logs/{id}", captainsLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(captainsLog.getId().intValue()))
            .andExpect(jsonPath("$.runDate").value(DEFAULT_RUN_DATE.toString()))
            .andExpect(jsonPath("$.rst").value(DEFAULT_RST.toString()))
            .andExpect(jsonPath("$.loadDate").value(DEFAULT_LOAD_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCaptainsLog() throws Exception {
        // Get the captainsLog
        restCaptainsLogMockMvc.perform(get("/api/captains-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCaptainsLog() throws Exception {
        // Initialize the database
        captainsLogService.save(captainsLog);

        int databaseSizeBeforeUpdate = captainsLogRepository.findAll().size();

        // Update the captainsLog
        CaptainsLog updatedCaptainsLog = captainsLogRepository.findById(captainsLog.getId()).get();
        // Disconnect from session so that the updates on updatedCaptainsLog are not directly saved in db
        em.detach(updatedCaptainsLog);
        updatedCaptainsLog
            .runDate(UPDATED_RUN_DATE)
            .rst(UPDATED_RST)
            .loadDate(UPDATED_LOAD_DATE);

        restCaptainsLogMockMvc.perform(put("/api/captains-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCaptainsLog)))
            .andExpect(status().isOk());

        // Validate the CaptainsLog in the database
        List<CaptainsLog> captainsLogList = captainsLogRepository.findAll();
        assertThat(captainsLogList).hasSize(databaseSizeBeforeUpdate);
        CaptainsLog testCaptainsLog = captainsLogList.get(captainsLogList.size() - 1);
        assertThat(testCaptainsLog.getRunDate()).isEqualTo(UPDATED_RUN_DATE);
        assertThat(testCaptainsLog.getRst()).isEqualTo(UPDATED_RST);
        assertThat(testCaptainsLog.getLoadDate()).isEqualTo(UPDATED_LOAD_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCaptainsLog() throws Exception {
        int databaseSizeBeforeUpdate = captainsLogRepository.findAll().size();

        // Create the CaptainsLog

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCaptainsLogMockMvc.perform(put("/api/captains-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(captainsLog)))
            .andExpect(status().isBadRequest());

        // Validate the CaptainsLog in the database
        List<CaptainsLog> captainsLogList = captainsLogRepository.findAll();
        assertThat(captainsLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCaptainsLog() throws Exception {
        // Initialize the database
        captainsLogService.save(captainsLog);

        int databaseSizeBeforeDelete = captainsLogRepository.findAll().size();

        // Get the captainsLog
        restCaptainsLogMockMvc.perform(delete("/api/captains-logs/{id}", captainsLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CaptainsLog> captainsLogList = captainsLogRepository.findAll();
        assertThat(captainsLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaptainsLog.class);
        CaptainsLog captainsLog1 = new CaptainsLog();
        captainsLog1.setId(1L);
        CaptainsLog captainsLog2 = new CaptainsLog();
        captainsLog2.setId(captainsLog1.getId());
        assertThat(captainsLog1).isEqualTo(captainsLog2);
        captainsLog2.setId(2L);
        assertThat(captainsLog1).isNotEqualTo(captainsLog2);
        captainsLog1.setId(null);
        assertThat(captainsLog1).isNotEqualTo(captainsLog2);
    }
}
