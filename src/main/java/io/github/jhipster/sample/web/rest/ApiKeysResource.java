package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.ApiKeysRepository;
import io.github.jhipster.sample.service.ApiKeysService;
import io.github.jhipster.sample.service.dto.ApiKeysDTO;
import io.github.jhipster.sample.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link io.github.jhipster.sample.domain.ApiKeys}.
 */
@RestController
@RequestMapping("/api")
public class ApiKeysResource {

    private final Logger log = LoggerFactory.getLogger(ApiKeysResource.class);

    private static final String ENTITY_NAME = "apiKeys";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiKeysService apiKeysService;

    private final ApiKeysRepository apiKeysRepository;

    public ApiKeysResource(ApiKeysService apiKeysService, ApiKeysRepository apiKeysRepository) {
        this.apiKeysService = apiKeysService;
        this.apiKeysRepository = apiKeysRepository;
    }

    /**
     * {@code POST  /api-keys} : Create a new apiKeys.
     *
     * @param apiKeysDTO the apiKeysDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiKeysDTO, or with status {@code 400 (Bad Request)} if the apiKeys has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/api-keys")
    public ResponseEntity<ApiKeysDTO> createApiKeys(@Valid @RequestBody ApiKeysDTO apiKeysDTO) throws URISyntaxException {
        log.debug("REST request to save ApiKeys : {}", apiKeysDTO);
        if (apiKeysDTO.getId() != null) {
            throw new BadRequestAlertException("A new apiKeys cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiKeysDTO result = apiKeysService.save(apiKeysDTO);
        return ResponseEntity
            .created(new URI("/api/api-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /api-keys/:id} : Updates an existing apiKeys.
     *
     * @param id the id of the apiKeysDTO to save.
     * @param apiKeysDTO the apiKeysDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiKeysDTO,
     * or with status {@code 400 (Bad Request)} if the apiKeysDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiKeysDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/api-keys/{id}")
    public ResponseEntity<ApiKeysDTO> updateApiKeys(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ApiKeysDTO apiKeysDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ApiKeys : {}, {}", id, apiKeysDTO);
        if (apiKeysDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apiKeysDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apiKeysRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ApiKeysDTO result = apiKeysService.update(apiKeysDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apiKeysDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /api-keys/:id} : Partial updates given fields of an existing apiKeys, field will ignore if it is null
     *
     * @param id the id of the apiKeysDTO to save.
     * @param apiKeysDTO the apiKeysDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiKeysDTO,
     * or with status {@code 400 (Bad Request)} if the apiKeysDTO is not valid,
     * or with status {@code 404 (Not Found)} if the apiKeysDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the apiKeysDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/api-keys/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApiKeysDTO> partialUpdateApiKeys(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ApiKeysDTO apiKeysDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ApiKeys partially : {}, {}", id, apiKeysDTO);
        if (apiKeysDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apiKeysDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apiKeysRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApiKeysDTO> result = apiKeysService.partialUpdate(apiKeysDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apiKeysDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /api-keys} : get all the apiKeys.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiKeys in body.
     */
    @GetMapping("/api-keys")
    public ResponseEntity<List<ApiKeysDTO>> getAllApiKeys(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ApiKeys");
        Page<ApiKeysDTO> page = apiKeysService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /api-keys/:id} : get the "id" apiKeys.
     *
     * @param id the id of the apiKeysDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiKeysDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/api-keys/{id}")
    public ResponseEntity<ApiKeysDTO> getApiKeys(@PathVariable Long id) {
        log.debug("REST request to get ApiKeys : {}", id);
        Optional<ApiKeysDTO> apiKeysDTO = apiKeysService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiKeysDTO);
    }

    /**
     * {@code DELETE  /api-keys/:id} : delete the "id" apiKeys.
     *
     * @param id the id of the apiKeysDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/api-keys/{id}")
    public ResponseEntity<Void> deleteApiKeys(@PathVariable Long id) {
        log.debug("REST request to delete ApiKeys : {}", id);
        apiKeysService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
