package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.RecurrenceCriteria;
import io.github.jhipster.sample.repository.RecurrenceCriteriaRepository;
import io.github.jhipster.sample.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link io.github.jhipster.sample.domain.RecurrenceCriteria}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RecurrenceCriteriaResource {

    private final Logger log = LoggerFactory.getLogger(RecurrenceCriteriaResource.class);

    private static final String ENTITY_NAME = "recurrenceCriteria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecurrenceCriteriaRepository recurrenceCriteriaRepository;

    public RecurrenceCriteriaResource(RecurrenceCriteriaRepository recurrenceCriteriaRepository) {
        this.recurrenceCriteriaRepository = recurrenceCriteriaRepository;
    }

    /**
     * {@code POST  /recurrence-criteria} : Create a new recurrenceCriteria.
     *
     * @param recurrenceCriteria the recurrenceCriteria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recurrenceCriteria, or with status {@code 400 (Bad Request)} if the recurrenceCriteria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recurrence-criteria")
    public ResponseEntity<RecurrenceCriteria> createRecurrenceCriteria(@RequestBody RecurrenceCriteria recurrenceCriteria)
        throws URISyntaxException {
        log.debug("REST request to save RecurrenceCriteria : {}", recurrenceCriteria);
        if (recurrenceCriteria.getId() != null) {
            throw new BadRequestAlertException("A new recurrenceCriteria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecurrenceCriteria result = recurrenceCriteriaRepository.save(recurrenceCriteria);
        return ResponseEntity
            .created(new URI("/api/recurrence-criteria/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recurrence-criteria/:id} : Updates an existing recurrenceCriteria.
     *
     * @param id the id of the recurrenceCriteria to save.
     * @param recurrenceCriteria the recurrenceCriteria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recurrenceCriteria,
     * or with status {@code 400 (Bad Request)} if the recurrenceCriteria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recurrenceCriteria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recurrence-criteria/{id}")
    public ResponseEntity<RecurrenceCriteria> updateRecurrenceCriteria(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RecurrenceCriteria recurrenceCriteria
    ) throws URISyntaxException {
        log.debug("REST request to update RecurrenceCriteria : {}, {}", id, recurrenceCriteria);
        if (recurrenceCriteria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recurrenceCriteria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recurrenceCriteriaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RecurrenceCriteria result = recurrenceCriteriaRepository.save(recurrenceCriteria);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recurrenceCriteria.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /recurrence-criteria/:id} : Partial updates given fields of an existing recurrenceCriteria, field will ignore if it is null
     *
     * @param id the id of the recurrenceCriteria to save.
     * @param recurrenceCriteria the recurrenceCriteria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recurrenceCriteria,
     * or with status {@code 400 (Bad Request)} if the recurrenceCriteria is not valid,
     * or with status {@code 404 (Not Found)} if the recurrenceCriteria is not found,
     * or with status {@code 500 (Internal Server Error)} if the recurrenceCriteria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/recurrence-criteria/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RecurrenceCriteria> partialUpdateRecurrenceCriteria(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RecurrenceCriteria recurrenceCriteria
    ) throws URISyntaxException {
        log.debug("REST request to partial update RecurrenceCriteria partially : {}, {}", id, recurrenceCriteria);
        if (recurrenceCriteria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recurrenceCriteria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recurrenceCriteriaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RecurrenceCriteria> result = recurrenceCriteriaRepository
            .findById(recurrenceCriteria.getId())
            .map(existingRecurrenceCriteria -> {
                if (recurrenceCriteria.getRecurrenceType() != null) {
                    existingRecurrenceCriteria.setRecurrenceType(recurrenceCriteria.getRecurrenceType());
                }
                if (recurrenceCriteria.getRecurringExpiry() != null) {
                    existingRecurrenceCriteria.setRecurringExpiry(recurrenceCriteria.getRecurringExpiry());
                }
                if (recurrenceCriteria.getRecurringFrequency() != null) {
                    existingRecurrenceCriteria.setRecurringFrequency(recurrenceCriteria.getRecurringFrequency());
                }
                if (recurrenceCriteria.getInstalments() != null) {
                    existingRecurrenceCriteria.setInstalments(recurrenceCriteria.getInstalments());
                }

                return existingRecurrenceCriteria;
            })
            .map(recurrenceCriteriaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recurrenceCriteria.getId().toString())
        );
    }

    /**
     * {@code GET  /recurrence-criteria} : get all the recurrenceCriteria.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recurrenceCriteria in body.
     */
    @GetMapping("/recurrence-criteria")
    public List<RecurrenceCriteria> getAllRecurrenceCriteria() {
        log.debug("REST request to get all RecurrenceCriteria");
        return recurrenceCriteriaRepository.findAll();
    }

    /**
     * {@code GET  /recurrence-criteria/:id} : get the "id" recurrenceCriteria.
     *
     * @param id the id of the recurrenceCriteria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recurrenceCriteria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recurrence-criteria/{id}")
    public ResponseEntity<RecurrenceCriteria> getRecurrenceCriteria(@PathVariable Long id) {
        log.debug("REST request to get RecurrenceCriteria : {}", id);
        Optional<RecurrenceCriteria> recurrenceCriteria = recurrenceCriteriaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(recurrenceCriteria);
    }

    /**
     * {@code DELETE  /recurrence-criteria/:id} : delete the "id" recurrenceCriteria.
     *
     * @param id the id of the recurrenceCriteria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recurrence-criteria/{id}")
    public ResponseEntity<Void> deleteRecurrenceCriteria(@PathVariable Long id) {
        log.debug("REST request to delete RecurrenceCriteria : {}", id);
        recurrenceCriteriaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
