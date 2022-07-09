package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.Parameters;
import io.github.jhipster.sample.repository.ParametersRepository;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.Parameters}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ParametersResource {

    private final Logger log = LoggerFactory.getLogger(ParametersResource.class);

    private static final String ENTITY_NAME = "parameters";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParametersRepository parametersRepository;

    public ParametersResource(ParametersRepository parametersRepository) {
        this.parametersRepository = parametersRepository;
    }

    /**
     * {@code POST  /parameters} : Create a new parameters.
     *
     * @param parameters the parameters to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parameters, or with status {@code 400 (Bad Request)} if the parameters has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parameters")
    public ResponseEntity<Parameters> createParameters(@RequestBody Parameters parameters) throws URISyntaxException {
        log.debug("REST request to save Parameters : {}", parameters);
        if (parameters.getId() != null) {
            throw new BadRequestAlertException("A new parameters cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Parameters result = parametersRepository.save(parameters);
        return ResponseEntity
            .created(new URI("/api/parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parameters/:id} : Updates an existing parameters.
     *
     * @param id the id of the parameters to save.
     * @param parameters the parameters to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parameters,
     * or with status {@code 400 (Bad Request)} if the parameters is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parameters couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parameters/{id}")
    public ResponseEntity<Parameters> updateParameters(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Parameters parameters
    ) throws URISyntaxException {
        log.debug("REST request to update Parameters : {}, {}", id, parameters);
        if (parameters.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parameters.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parametersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Parameters result = parametersRepository.save(parameters);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parameters.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /parameters/:id} : Partial updates given fields of an existing parameters, field will ignore if it is null
     *
     * @param id the id of the parameters to save.
     * @param parameters the parameters to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parameters,
     * or with status {@code 400 (Bad Request)} if the parameters is not valid,
     * or with status {@code 404 (Not Found)} if the parameters is not found,
     * or with status {@code 500 (Internal Server Error)} if the parameters couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/parameters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Parameters> partialUpdateParameters(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Parameters parameters
    ) throws URISyntaxException {
        log.debug("REST request to partial update Parameters partially : {}, {}", id, parameters);
        if (parameters.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parameters.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parametersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Parameters> result = parametersRepository
            .findById(parameters.getId())
            .map(existingParameters -> {
                if (parameters.getParameter() != null) {
                    existingParameters.setParameter(parameters.getParameter());
                }

                return existingParameters;
            })
            .map(parametersRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parameters.getId().toString())
        );
    }

    /**
     * {@code GET  /parameters} : get all the parameters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parameters in body.
     */
    @GetMapping("/parameters")
    public List<Parameters> getAllParameters() {
        log.debug("REST request to get all Parameters");
        return parametersRepository.findAll();
    }

    /**
     * {@code GET  /parameters/:id} : get the "id" parameters.
     *
     * @param id the id of the parameters to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parameters, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parameters/{id}")
    public ResponseEntity<Parameters> getParameters(@PathVariable Long id) {
        log.debug("REST request to get Parameters : {}", id);
        Optional<Parameters> parameters = parametersRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(parameters);
    }

    /**
     * {@code DELETE  /parameters/:id} : delete the "id" parameters.
     *
     * @param id the id of the parameters to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parameters/{id}")
    public ResponseEntity<Void> deleteParameters(@PathVariable Long id) {
        log.debug("REST request to delete Parameters : {}", id);
        parametersRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
