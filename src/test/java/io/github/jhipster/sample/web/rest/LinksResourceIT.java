package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.Links;
import io.github.jhipster.sample.repository.LinksRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LinksResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LinksResourceIT {

    private static final String ENTITY_API_URL = "/api/links";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LinksRepository linksRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLinksMockMvc;

    private Links links;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Links createEntity(EntityManager em) {
        Links links = new Links();
        return links;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Links createUpdatedEntity(EntityManager em) {
        Links links = new Links();
        return links;
    }

    @BeforeEach
    public void initTest() {
        links = createEntity(em);
    }

    @Test
    @Transactional
    void createLinks() throws Exception {
        int databaseSizeBeforeCreate = linksRepository.findAll().size();
        // Create the Links
        restLinksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(links)))
            .andExpect(status().isCreated());

        // Validate the Links in the database
        List<Links> linksList = linksRepository.findAll();
        assertThat(linksList).hasSize(databaseSizeBeforeCreate + 1);
        Links testLinks = linksList.get(linksList.size() - 1);
    }

    @Test
    @Transactional
    void createLinksWithExistingId() throws Exception {
        // Create the Links with an existing ID
        links.setId(1L);

        int databaseSizeBeforeCreate = linksRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLinksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(links)))
            .andExpect(status().isBadRequest());

        // Validate the Links in the database
        List<Links> linksList = linksRepository.findAll();
        assertThat(linksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLinks() throws Exception {
        // Initialize the database
        linksRepository.saveAndFlush(links);

        // Get all the linksList
        restLinksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(links.getId().intValue())));
    }

    @Test
    @Transactional
    void getLinks() throws Exception {
        // Initialize the database
        linksRepository.saveAndFlush(links);

        // Get the links
        restLinksMockMvc
            .perform(get(ENTITY_API_URL_ID, links.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(links.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingLinks() throws Exception {
        // Get the links
        restLinksMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLinks() throws Exception {
        // Initialize the database
        linksRepository.saveAndFlush(links);

        int databaseSizeBeforeUpdate = linksRepository.findAll().size();

        // Update the links
        Links updatedLinks = linksRepository.findById(links.getId()).get();
        // Disconnect from session so that the updates on updatedLinks are not directly saved in db
        em.detach(updatedLinks);

        restLinksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLinks.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLinks))
            )
            .andExpect(status().isOk());

        // Validate the Links in the database
        List<Links> linksList = linksRepository.findAll();
        assertThat(linksList).hasSize(databaseSizeBeforeUpdate);
        Links testLinks = linksList.get(linksList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingLinks() throws Exception {
        int databaseSizeBeforeUpdate = linksRepository.findAll().size();
        links.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, links.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(links))
            )
            .andExpect(status().isBadRequest());

        // Validate the Links in the database
        List<Links> linksList = linksRepository.findAll();
        assertThat(linksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLinks() throws Exception {
        int databaseSizeBeforeUpdate = linksRepository.findAll().size();
        links.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(links))
            )
            .andExpect(status().isBadRequest());

        // Validate the Links in the database
        List<Links> linksList = linksRepository.findAll();
        assertThat(linksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLinks() throws Exception {
        int databaseSizeBeforeUpdate = linksRepository.findAll().size();
        links.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinksMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(links)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Links in the database
        List<Links> linksList = linksRepository.findAll();
        assertThat(linksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLinksWithPatch() throws Exception {
        // Initialize the database
        linksRepository.saveAndFlush(links);

        int databaseSizeBeforeUpdate = linksRepository.findAll().size();

        // Update the links using partial update
        Links partialUpdatedLinks = new Links();
        partialUpdatedLinks.setId(links.getId());

        restLinksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLinks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLinks))
            )
            .andExpect(status().isOk());

        // Validate the Links in the database
        List<Links> linksList = linksRepository.findAll();
        assertThat(linksList).hasSize(databaseSizeBeforeUpdate);
        Links testLinks = linksList.get(linksList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateLinksWithPatch() throws Exception {
        // Initialize the database
        linksRepository.saveAndFlush(links);

        int databaseSizeBeforeUpdate = linksRepository.findAll().size();

        // Update the links using partial update
        Links partialUpdatedLinks = new Links();
        partialUpdatedLinks.setId(links.getId());

        restLinksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLinks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLinks))
            )
            .andExpect(status().isOk());

        // Validate the Links in the database
        List<Links> linksList = linksRepository.findAll();
        assertThat(linksList).hasSize(databaseSizeBeforeUpdate);
        Links testLinks = linksList.get(linksList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingLinks() throws Exception {
        int databaseSizeBeforeUpdate = linksRepository.findAll().size();
        links.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, links.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(links))
            )
            .andExpect(status().isBadRequest());

        // Validate the Links in the database
        List<Links> linksList = linksRepository.findAll();
        assertThat(linksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLinks() throws Exception {
        int databaseSizeBeforeUpdate = linksRepository.findAll().size();
        links.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(links))
            )
            .andExpect(status().isBadRequest());

        // Validate the Links in the database
        List<Links> linksList = linksRepository.findAll();
        assertThat(linksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLinks() throws Exception {
        int databaseSizeBeforeUpdate = linksRepository.findAll().size();
        links.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLinksMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(links)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Links in the database
        List<Links> linksList = linksRepository.findAll();
        assertThat(linksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLinks() throws Exception {
        // Initialize the database
        linksRepository.saveAndFlush(links);

        int databaseSizeBeforeDelete = linksRepository.findAll().size();

        // Delete the links
        restLinksMockMvc
            .perform(delete(ENTITY_API_URL_ID, links.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Links> linksList = linksRepository.findAll();
        assertThat(linksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
