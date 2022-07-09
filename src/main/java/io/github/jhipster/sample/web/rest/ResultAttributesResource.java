package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.ResultAttributes;
import io.github.jhipster.sample.repository.ResultAttributesRepository;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.ResultAttributes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResultAttributesResource {

    private final Logger log = LoggerFactory.getLogger(ResultAttributesResource.class);

    private static final String ENTITY_NAME = "resultAttributes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultAttributesRepository resultAttributesRepository;

    public ResultAttributesResource(ResultAttributesRepository resultAttributesRepository) {
        this.resultAttributesRepository = resultAttributesRepository;
    }

    /**
     * {@code POST  /result-attributes} : Create a new resultAttributes.
     *
     * @param resultAttributes the resultAttributes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultAttributes, or with status {@code 400 (Bad Request)} if the resultAttributes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/result-attributes")
    public ResponseEntity<ResultAttributes> createResultAttributes(@RequestBody ResultAttributes resultAttributes)
        throws URISyntaxException {
        log.debug("REST request to save ResultAttributes : {}", resultAttributes);
        if (resultAttributes.getId() != null) {
            throw new BadRequestAlertException("A new resultAttributes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultAttributes result = resultAttributesRepository.save(resultAttributes);
        return ResponseEntity
            .created(new URI("/api/result-attributes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /result-attributes/:id} : Updates an existing resultAttributes.
     *
     * @param id the id of the resultAttributes to save.
     * @param resultAttributes the resultAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultAttributes,
     * or with status {@code 400 (Bad Request)} if the resultAttributes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resultAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/result-attributes/{id}")
    public ResponseEntity<ResultAttributes> updateResultAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResultAttributes resultAttributes
    ) throws URISyntaxException {
        log.debug("REST request to update ResultAttributes : {}, {}", id, resultAttributes);
        if (resultAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resultAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resultAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResultAttributes result = resultAttributesRepository.save(resultAttributes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resultAttributes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /result-attributes/:id} : Partial updates given fields of an existing resultAttributes, field will ignore if it is null
     *
     * @param id the id of the resultAttributes to save.
     * @param resultAttributes the resultAttributes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultAttributes,
     * or with status {@code 400 (Bad Request)} if the resultAttributes is not valid,
     * or with status {@code 404 (Not Found)} if the resultAttributes is not found,
     * or with status {@code 500 (Internal Server Error)} if the resultAttributes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/result-attributes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResultAttributes> partialUpdateResultAttributes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResultAttributes resultAttributes
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResultAttributes partially : {}, {}", id, resultAttributes);
        if (resultAttributes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, resultAttributes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!resultAttributesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResultAttributes> result = resultAttributesRepository
            .findById(resultAttributes.getId())
            .map(existingResultAttributes -> {
                if (resultAttributes.getKey() != null) {
                    existingResultAttributes.setKey(resultAttributes.getKey());
                }
                if (resultAttributes.getValue() != null) {
                    existingResultAttributes.setValue(resultAttributes.getValue());
                }

                return existingResultAttributes;
            })
            .map(resultAttributesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resultAttributes.getId().toString())
        );
    }

    /**
     * {@code GET  /result-attributes} : get all the resultAttributes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resultAttributes in body.
     */
    @GetMapping("/result-attributes")
    public List<ResultAttributes> getAllResultAttributes() {
        log.debug("REST request to get all ResultAttributes");
        return resultAttributesRepository.findAll();
    }

    /**
     * {@code GET  /result-attributes/:id} : get the "id" resultAttributes.
     *
     * @param id the id of the resultAttributes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultAttributes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/result-attributes/{id}")
    public ResponseEntity<ResultAttributes> getResultAttributes(@PathVariable Long id) {
        log.debug("REST request to get ResultAttributes : {}", id);
        Optional<ResultAttributes> resultAttributes = resultAttributesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resultAttributes);
    }

    /**
     * {@code DELETE  /result-attributes/:id} : delete the "id" resultAttributes.
     *
     * @param id the id of the resultAttributes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/result-attributes/{id}")
    public ResponseEntity<Void> deleteResultAttributes(@PathVariable Long id) {
        log.debug("REST request to delete ResultAttributes : {}", id);
        resultAttributesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
