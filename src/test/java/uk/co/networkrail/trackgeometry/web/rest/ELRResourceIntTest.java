package uk.co.networkrail.trackgeometry.web.rest;

import uk.co.networkrail.trackgeometry.CaptainsLogApp;

import uk.co.networkrail.trackgeometry.domain.ELR;
import uk.co.networkrail.trackgeometry.repository.ELRRepository;
import uk.co.networkrail.trackgeometry.service.ELRService;
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
import java.util.List;


import static uk.co.networkrail.trackgeometry.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ELRResource REST controller.
 *
 * @see ELRResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaptainsLogApp.class)
public class ELRResourceIntTest {

    private static final String DEFAULT_ELR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ELR_CODE = "BBBBBBBBBB";

    @Autowired
    private ELRRepository eLRRepository;

    

    @Autowired
    private ELRService eLRService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restELRMockMvc;

    private ELR eLR;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ELRResource eLRResource = new ELRResource(eLRService);
        this.restELRMockMvc = MockMvcBuilders.standaloneSetup(eLRResource)
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
    public static ELR createEntity(EntityManager em) {
        ELR eLR = new ELR()
            .elrCode(DEFAULT_ELR_CODE);
        return eLR;
    }

    @Before
    public void initTest() {
        eLR = createEntity(em);
    }

    @Test
    @Transactional
    public void createELR() throws Exception {
        int databaseSizeBeforeCreate = eLRRepository.findAll().size();

        // Create the ELR
        restELRMockMvc.perform(post("/api/elrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eLR)))
            .andExpect(status().isCreated());

        // Validate the ELR in the database
        List<ELR> eLRList = eLRRepository.findAll();
        assertThat(eLRList).hasSize(databaseSizeBeforeCreate + 1);
        ELR testELR = eLRList.get(eLRList.size() - 1);
        assertThat(testELR.getElrCode()).isEqualTo(DEFAULT_ELR_CODE);
    }

    @Test
    @Transactional
    public void createELRWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eLRRepository.findAll().size();

        // Create the ELR with an existing ID
        eLR.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restELRMockMvc.perform(post("/api/elrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eLR)))
            .andExpect(status().isBadRequest());

        // Validate the ELR in the database
        List<ELR> eLRList = eLRRepository.findAll();
        assertThat(eLRList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkElrCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = eLRRepository.findAll().size();
        // set the field null
        eLR.setElrCode(null);

        // Create the ELR, which fails.

        restELRMockMvc.perform(post("/api/elrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eLR)))
            .andExpect(status().isBadRequest());

        List<ELR> eLRList = eLRRepository.findAll();
        assertThat(eLRList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllELRS() throws Exception {
        // Initialize the database
        eLRRepository.saveAndFlush(eLR);

        // Get all the eLRList
        restELRMockMvc.perform(get("/api/elrs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eLR.getId().intValue())))
            .andExpect(jsonPath("$.[*].elrCode").value(hasItem(DEFAULT_ELR_CODE.toString())));
    }
    

    @Test
    @Transactional
    public void getELR() throws Exception {
        // Initialize the database
        eLRRepository.saveAndFlush(eLR);

        // Get the eLR
        restELRMockMvc.perform(get("/api/elrs/{id}", eLR.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eLR.getId().intValue()))
            .andExpect(jsonPath("$.elrCode").value(DEFAULT_ELR_CODE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingELR() throws Exception {
        // Get the eLR
        restELRMockMvc.perform(get("/api/elrs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateELR() throws Exception {
        // Initialize the database
        eLRService.save(eLR);

        int databaseSizeBeforeUpdate = eLRRepository.findAll().size();

        // Update the eLR
        ELR updatedELR = eLRRepository.findById(eLR.getId()).get();
        // Disconnect from session so that the updates on updatedELR are not directly saved in db
        em.detach(updatedELR);
        updatedELR
            .elrCode(UPDATED_ELR_CODE);

        restELRMockMvc.perform(put("/api/elrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedELR)))
            .andExpect(status().isOk());

        // Validate the ELR in the database
        List<ELR> eLRList = eLRRepository.findAll();
        assertThat(eLRList).hasSize(databaseSizeBeforeUpdate);
        ELR testELR = eLRList.get(eLRList.size() - 1);
        assertThat(testELR.getElrCode()).isEqualTo(UPDATED_ELR_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingELR() throws Exception {
        int databaseSizeBeforeUpdate = eLRRepository.findAll().size();

        // Create the ELR

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restELRMockMvc.perform(put("/api/elrs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eLR)))
            .andExpect(status().isBadRequest());

        // Validate the ELR in the database
        List<ELR> eLRList = eLRRepository.findAll();
        assertThat(eLRList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteELR() throws Exception {
        // Initialize the database
        eLRService.save(eLR);

        int databaseSizeBeforeDelete = eLRRepository.findAll().size();

        // Get the eLR
        restELRMockMvc.perform(delete("/api/elrs/{id}", eLR.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ELR> eLRList = eLRRepository.findAll();
        assertThat(eLRList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ELR.class);
        ELR eLR1 = new ELR();
        eLR1.setId(1L);
        ELR eLR2 = new ELR();
        eLR2.setId(eLR1.getId());
        assertThat(eLR1).isEqualTo(eLR2);
        eLR2.setId(2L);
        assertThat(eLR1).isNotEqualTo(eLR2);
        eLR1.setId(null);
        assertThat(eLR1).isNotEqualTo(eLR2);
    }
}
