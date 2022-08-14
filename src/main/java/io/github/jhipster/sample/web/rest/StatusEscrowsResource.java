package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.StatusEscrowsRepository;
import io.github.jhipster.sample.service.StatusEscrowsService;
import io.github.jhipster.sample.service.dto.StatusEscrowsDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.StatusEscrows}.
 */
@RestController
@RequestMapping("/api")
public class StatusEscrowsResource {

    private final Logger log = LoggerFactory.getLogger(StatusEscrowsResource.class);

    private static final String ENTITY_NAME = "statusEscrows";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatusEscrowsService statusEscrowsService;

    private final StatusEscrowsRepository statusEscrowsRepository;

    public StatusEscrowsResource(StatusEscrowsService statusEscrowsService, StatusEscrowsRepository statusEscrowsRepository) {
        this.statusEscrowsService = statusEscrowsService;
        this.statusEscrowsRepository = statusEscrowsRepository;
    }

    /**
     * {@code POST  /status-escrows} : Create a new statusEscrows.
     *
     * @param statusEscrowsDTO the statusEscrowsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statusEscrowsDTO, or with status {@code 400 (Bad Request)} if the statusEscrows has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/status-escrows")
    public ResponseEntity<StatusEscrowsDTO> createStatusEscrows(@Valid @RequestBody StatusEscrowsDTO statusEscrowsDTO)
        throws URISyntaxException {
        log.debug("REST request to save StatusEscrows : {}", statusEscrowsDTO);
        if (statusEscrowsDTO.getId() != null) {
            throw new BadRequestAlertException("A new statusEscrows cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatusEscrowsDTO result = statusEscrowsService.save(statusEscrowsDTO);
        return ResponseEntity
            .created(new URI("/api/status-escrows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /status-escrows/:id} : Updates an existing statusEscrows.
     *
     * @param id the id of the statusEscrowsDTO to save.
     * @param statusEscrowsDTO the statusEscrowsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statusEscrowsDTO,
     * or with status {@code 400 (Bad Request)} if the statusEscrowsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statusEscrowsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/status-escrows/{id}")
    public ResponseEntity<StatusEscrowsDTO> updateStatusEscrows(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StatusEscrowsDTO statusEscrowsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StatusEscrows : {}, {}", id, statusEscrowsDTO);
        if (statusEscrowsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statusEscrowsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statusEscrowsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StatusEscrowsDTO result = statusEscrowsService.update(statusEscrowsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statusEscrowsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /status-escrows/:id} : Partial updates given fields of an existing statusEscrows, field will ignore if it is null
     *
     * @param id the id of the statusEscrowsDTO to save.
     * @param statusEscrowsDTO the statusEscrowsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statusEscrowsDTO,
     * or with status {@code 400 (Bad Request)} if the statusEscrowsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the statusEscrowsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the statusEscrowsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/status-escrows/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StatusEscrowsDTO> partialUpdateStatusEscrows(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StatusEscrowsDTO statusEscrowsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StatusEscrows partially : {}, {}", id, statusEscrowsDTO);
        if (statusEscrowsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statusEscrowsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statusEscrowsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StatusEscrowsDTO> result = statusEscrowsService.partialUpdate(statusEscrowsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statusEscrowsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /status-escrows} : get all the statusEscrows.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statusEscrows in body.
     */
    @GetMapping("/status-escrows")
    public ResponseEntity<List<StatusEscrowsDTO>> getAllStatusEscrows(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of StatusEscrows");
        Page<StatusEscrowsDTO> page = statusEscrowsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /status-escrows/:id} : get the "id" statusEscrows.
     *
     * @param id the id of the statusEscrowsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statusEscrowsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/status-escrows/{id}")
    public ResponseEntity<StatusEscrowsDTO> getStatusEscrows(@PathVariable Long id) {
        log.debug("REST request to get StatusEscrows : {}", id);
        Optional<StatusEscrowsDTO> statusEscrowsDTO = statusEscrowsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statusEscrowsDTO);
    }

    /**
     * {@code DELETE  /status-escrows/:id} : delete the "id" statusEscrows.
     *
     * @param id the id of the statusEscrowsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/status-escrows/{id}")
    public ResponseEntity<Void> deleteStatusEscrows(@PathVariable Long id) {
        log.debug("REST request to delete StatusEscrows : {}", id);
        statusEscrowsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
