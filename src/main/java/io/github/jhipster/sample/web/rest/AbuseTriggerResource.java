package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.AbuseTrigger;
import io.github.jhipster.sample.repository.AbuseTriggerRepository;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.AbuseTrigger}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AbuseTriggerResource {

    private final Logger log = LoggerFactory.getLogger(AbuseTriggerResource.class);

    private static final String ENTITY_NAME = "abuseTrigger";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AbuseTriggerRepository abuseTriggerRepository;

    public AbuseTriggerResource(AbuseTriggerRepository abuseTriggerRepository) {
        this.abuseTriggerRepository = abuseTriggerRepository;
    }

    /**
     * {@code POST  /abuse-triggers} : Create a new abuseTrigger.
     *
     * @param abuseTrigger the abuseTrigger to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new abuseTrigger, or with status {@code 400 (Bad Request)} if the abuseTrigger has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/abuse-triggers")
    public ResponseEntity<AbuseTrigger> createAbuseTrigger(@RequestBody AbuseTrigger abuseTrigger) throws URISyntaxException {
        log.debug("REST request to save AbuseTrigger : {}", abuseTrigger);
        if (abuseTrigger.getId() != null) {
            throw new BadRequestAlertException("A new abuseTrigger cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AbuseTrigger result = abuseTriggerRepository.save(abuseTrigger);
        return ResponseEntity
            .created(new URI("/api/abuse-triggers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /abuse-triggers/:id} : Updates an existing abuseTrigger.
     *
     * @param id the id of the abuseTrigger to save.
     * @param abuseTrigger the abuseTrigger to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated abuseTrigger,
     * or with status {@code 400 (Bad Request)} if the abuseTrigger is not valid,
     * or with status {@code 500 (Internal Server Error)} if the abuseTrigger couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/abuse-triggers/{id}")
    public ResponseEntity<AbuseTrigger> updateAbuseTrigger(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AbuseTrigger abuseTrigger
    ) throws URISyntaxException {
        log.debug("REST request to update AbuseTrigger : {}, {}", id, abuseTrigger);
        if (abuseTrigger.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, abuseTrigger.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!abuseTriggerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AbuseTrigger result = abuseTriggerRepository.save(abuseTrigger);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, abuseTrigger.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /abuse-triggers/:id} : Partial updates given fields of an existing abuseTrigger, field will ignore if it is null
     *
     * @param id the id of the abuseTrigger to save.
     * @param abuseTrigger the abuseTrigger to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated abuseTrigger,
     * or with status {@code 400 (Bad Request)} if the abuseTrigger is not valid,
     * or with status {@code 404 (Not Found)} if the abuseTrigger is not found,
     * or with status {@code 500 (Internal Server Error)} if the abuseTrigger couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/abuse-triggers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AbuseTrigger> partialUpdateAbuseTrigger(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AbuseTrigger abuseTrigger
    ) throws URISyntaxException {
        log.debug("REST request to partial update AbuseTrigger partially : {}, {}", id, abuseTrigger);
        if (abuseTrigger.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, abuseTrigger.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!abuseTriggerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AbuseTrigger> result = abuseTriggerRepository
            .findById(abuseTrigger.getId())
            .map(existingAbuseTrigger -> {
                if (abuseTrigger.getScore() != null) {
                    existingAbuseTrigger.setScore(abuseTrigger.getScore());
                }
                if (abuseTrigger.getCode() != null) {
                    existingAbuseTrigger.setCode(abuseTrigger.getCode());
                }
                if (abuseTrigger.getDescription() != null) {
                    existingAbuseTrigger.setDescription(abuseTrigger.getDescription());
                }

                return existingAbuseTrigger;
            })
            .map(abuseTriggerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, abuseTrigger.getId().toString())
        );
    }

    /**
     * {@code GET  /abuse-triggers} : get all the abuseTriggers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of abuseTriggers in body.
     */
    @GetMapping("/abuse-triggers")
    public List<AbuseTrigger> getAllAbuseTriggers() {
        log.debug("REST request to get all AbuseTriggers");
        return abuseTriggerRepository.findAll();
    }

    /**
     * {@code GET  /abuse-triggers/:id} : get the "id" abuseTrigger.
     *
     * @param id the id of the abuseTrigger to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the abuseTrigger, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/abuse-triggers/{id}")
    public ResponseEntity<AbuseTrigger> getAbuseTrigger(@PathVariable Long id) {
        log.debug("REST request to get AbuseTrigger : {}", id);
        Optional<AbuseTrigger> abuseTrigger = abuseTriggerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(abuseTrigger);
    }

    /**
     * {@code DELETE  /abuse-triggers/:id} : delete the "id" abuseTrigger.
     *
     * @param id the id of the abuseTrigger to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/abuse-triggers/{id}")
    public ResponseEntity<Void> deleteAbuseTrigger(@PathVariable Long id) {
        log.debug("REST request to delete AbuseTrigger : {}", id);
        abuseTriggerRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
