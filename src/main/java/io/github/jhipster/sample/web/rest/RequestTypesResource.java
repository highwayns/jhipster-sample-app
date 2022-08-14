package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.RequestTypesRepository;
import io.github.jhipster.sample.service.RequestTypesService;
import io.github.jhipster.sample.service.dto.RequestTypesDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.RequestTypes}.
 */
@RestController
@RequestMapping("/api")
public class RequestTypesResource {

    private final Logger log = LoggerFactory.getLogger(RequestTypesResource.class);

    private static final String ENTITY_NAME = "requestTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestTypesService requestTypesService;

    private final RequestTypesRepository requestTypesRepository;

    public RequestTypesResource(RequestTypesService requestTypesService, RequestTypesRepository requestTypesRepository) {
        this.requestTypesService = requestTypesService;
        this.requestTypesRepository = requestTypesRepository;
    }

    /**
     * {@code POST  /request-types} : Create a new requestTypes.
     *
     * @param requestTypesDTO the requestTypesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestTypesDTO, or with status {@code 400 (Bad Request)} if the requestTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/request-types")
    public ResponseEntity<RequestTypesDTO> createRequestTypes(@Valid @RequestBody RequestTypesDTO requestTypesDTO)
        throws URISyntaxException {
        log.debug("REST request to save RequestTypes : {}", requestTypesDTO);
        if (requestTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new requestTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestTypesDTO result = requestTypesService.save(requestTypesDTO);
        return ResponseEntity
            .created(new URI("/api/request-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /request-types/:id} : Updates an existing requestTypes.
     *
     * @param id the id of the requestTypesDTO to save.
     * @param requestTypesDTO the requestTypesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestTypesDTO,
     * or with status {@code 400 (Bad Request)} if the requestTypesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestTypesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/request-types/{id}")
    public ResponseEntity<RequestTypesDTO> updateRequestTypes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RequestTypesDTO requestTypesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RequestTypes : {}, {}", id, requestTypesDTO);
        if (requestTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestTypesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestTypesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequestTypesDTO result = requestTypesService.update(requestTypesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /request-types/:id} : Partial updates given fields of an existing requestTypes, field will ignore if it is null
     *
     * @param id the id of the requestTypesDTO to save.
     * @param requestTypesDTO the requestTypesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestTypesDTO,
     * or with status {@code 400 (Bad Request)} if the requestTypesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requestTypesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestTypesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/request-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestTypesDTO> partialUpdateRequestTypes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RequestTypesDTO requestTypesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestTypes partially : {}, {}", id, requestTypesDTO);
        if (requestTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestTypesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestTypesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestTypesDTO> result = requestTypesService.partialUpdate(requestTypesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestTypesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /request-types} : get all the requestTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestTypes in body.
     */
    @GetMapping("/request-types")
    public ResponseEntity<List<RequestTypesDTO>> getAllRequestTypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of RequestTypes");
        Page<RequestTypesDTO> page = requestTypesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /request-types/:id} : get the "id" requestTypes.
     *
     * @param id the id of the requestTypesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestTypesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/request-types/{id}")
    public ResponseEntity<RequestTypesDTO> getRequestTypes(@PathVariable Long id) {
        log.debug("REST request to get RequestTypes : {}", id);
        Optional<RequestTypesDTO> requestTypesDTO = requestTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestTypesDTO);
    }

    /**
     * {@code DELETE  /request-types/:id} : delete the "id" requestTypes.
     *
     * @param id the id of the requestTypesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/request-types/{id}")
    public ResponseEntity<Void> deleteRequestTypes(@PathVariable Long id) {
        log.debug("REST request to delete RequestTypes : {}", id);
        requestTypesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
