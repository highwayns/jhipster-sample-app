package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.RequestStatusRepository;
import io.github.jhipster.sample.service.RequestStatusService;
import io.github.jhipster.sample.service.dto.RequestStatusDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.RequestStatus}.
 */
@RestController
@RequestMapping("/api")
public class RequestStatusResource {

    private final Logger log = LoggerFactory.getLogger(RequestStatusResource.class);

    private static final String ENTITY_NAME = "requestStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestStatusService requestStatusService;

    private final RequestStatusRepository requestStatusRepository;

    public RequestStatusResource(RequestStatusService requestStatusService, RequestStatusRepository requestStatusRepository) {
        this.requestStatusService = requestStatusService;
        this.requestStatusRepository = requestStatusRepository;
    }

    /**
     * {@code POST  /request-statuses} : Create a new requestStatus.
     *
     * @param requestStatusDTO the requestStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestStatusDTO, or with status {@code 400 (Bad Request)} if the requestStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/request-statuses")
    public ResponseEntity<RequestStatusDTO> createRequestStatus(@Valid @RequestBody RequestStatusDTO requestStatusDTO)
        throws URISyntaxException {
        log.debug("REST request to save RequestStatus : {}", requestStatusDTO);
        if (requestStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new requestStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestStatusDTO result = requestStatusService.save(requestStatusDTO);
        return ResponseEntity
            .created(new URI("/api/request-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /request-statuses/:id} : Updates an existing requestStatus.
     *
     * @param id the id of the requestStatusDTO to save.
     * @param requestStatusDTO the requestStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestStatusDTO,
     * or with status {@code 400 (Bad Request)} if the requestStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/request-statuses/{id}")
    public ResponseEntity<RequestStatusDTO> updateRequestStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RequestStatusDTO requestStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RequestStatus : {}, {}", id, requestStatusDTO);
        if (requestStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequestStatusDTO result = requestStatusService.update(requestStatusDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /request-statuses/:id} : Partial updates given fields of an existing requestStatus, field will ignore if it is null
     *
     * @param id the id of the requestStatusDTO to save.
     * @param requestStatusDTO the requestStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestStatusDTO,
     * or with status {@code 400 (Bad Request)} if the requestStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requestStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/request-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestStatusDTO> partialUpdateRequestStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RequestStatusDTO requestStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RequestStatus partially : {}, {}", id, requestStatusDTO);
        if (requestStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestStatusDTO> result = requestStatusService.partialUpdate(requestStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestStatusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /request-statuses} : get all the requestStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requestStatuses in body.
     */
    @GetMapping("/request-statuses")
    public ResponseEntity<List<RequestStatusDTO>> getAllRequestStatuses(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of RequestStatuses");
        Page<RequestStatusDTO> page = requestStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /request-statuses/:id} : get the "id" requestStatus.
     *
     * @param id the id of the requestStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/request-statuses/{id}")
    public ResponseEntity<RequestStatusDTO> getRequestStatus(@PathVariable Long id) {
        log.debug("REST request to get RequestStatus : {}", id);
        Optional<RequestStatusDTO> requestStatusDTO = requestStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestStatusDTO);
    }

    /**
     * {@code DELETE  /request-statuses/:id} : delete the "id" requestStatus.
     *
     * @param id the id of the requestStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/request-statuses/{id}")
    public ResponseEntity<Void> deleteRequestStatus(@PathVariable Long id) {
        log.debug("REST request to delete RequestStatus : {}", id);
        requestStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
