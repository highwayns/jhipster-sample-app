package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.AdminSessionsRepository;
import io.github.jhipster.sample.service.AdminSessionsService;
import io.github.jhipster.sample.service.dto.AdminSessionsDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.AdminSessions}.
 */
@RestController
@RequestMapping("/api")
public class AdminSessionsResource {

    private final Logger log = LoggerFactory.getLogger(AdminSessionsResource.class);

    private static final String ENTITY_NAME = "adminSessions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminSessionsService adminSessionsService;

    private final AdminSessionsRepository adminSessionsRepository;

    public AdminSessionsResource(AdminSessionsService adminSessionsService, AdminSessionsRepository adminSessionsRepository) {
        this.adminSessionsService = adminSessionsService;
        this.adminSessionsRepository = adminSessionsRepository;
    }

    /**
     * {@code POST  /admin-sessions} : Create a new adminSessions.
     *
     * @param adminSessionsDTO the adminSessionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adminSessionsDTO, or with status {@code 400 (Bad Request)} if the adminSessions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin-sessions")
    public ResponseEntity<AdminSessionsDTO> createAdminSessions(@Valid @RequestBody AdminSessionsDTO adminSessionsDTO)
        throws URISyntaxException {
        log.debug("REST request to save AdminSessions : {}", adminSessionsDTO);
        if (adminSessionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new adminSessions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminSessionsDTO result = adminSessionsService.save(adminSessionsDTO);
        return ResponseEntity
            .created(new URI("/api/admin-sessions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admin-sessions/:id} : Updates an existing adminSessions.
     *
     * @param id the id of the adminSessionsDTO to save.
     * @param adminSessionsDTO the adminSessionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminSessionsDTO,
     * or with status {@code 400 (Bad Request)} if the adminSessionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adminSessionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin-sessions/{id}")
    public ResponseEntity<AdminSessionsDTO> updateAdminSessions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdminSessionsDTO adminSessionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdminSessions : {}, {}", id, adminSessionsDTO);
        if (adminSessionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminSessionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminSessionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdminSessionsDTO result = adminSessionsService.update(adminSessionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminSessionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /admin-sessions/:id} : Partial updates given fields of an existing adminSessions, field will ignore if it is null
     *
     * @param id the id of the adminSessionsDTO to save.
     * @param adminSessionsDTO the adminSessionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminSessionsDTO,
     * or with status {@code 400 (Bad Request)} if the adminSessionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the adminSessionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the adminSessionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/admin-sessions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdminSessionsDTO> partialUpdateAdminSessions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdminSessionsDTO adminSessionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdminSessions partially : {}, {}", id, adminSessionsDTO);
        if (adminSessionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminSessionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminSessionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdminSessionsDTO> result = adminSessionsService.partialUpdate(adminSessionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminSessionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /admin-sessions} : get all the adminSessions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adminSessions in body.
     */
    @GetMapping("/admin-sessions")
    public ResponseEntity<List<AdminSessionsDTO>> getAllAdminSessions(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AdminSessions");
        Page<AdminSessionsDTO> page = adminSessionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /admin-sessions/:id} : get the "id" adminSessions.
     *
     * @param id the id of the adminSessionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adminSessionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin-sessions/{id}")
    public ResponseEntity<AdminSessionsDTO> getAdminSessions(@PathVariable Long id) {
        log.debug("REST request to get AdminSessions : {}", id);
        Optional<AdminSessionsDTO> adminSessionsDTO = adminSessionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adminSessionsDTO);
    }

    /**
     * {@code DELETE  /admin-sessions/:id} : delete the "id" adminSessions.
     *
     * @param id the id of the adminSessionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin-sessions/{id}")
    public ResponseEntity<Void> deleteAdminSessions(@PathVariable Long id) {
        log.debug("REST request to delete AdminSessions : {}", id);
        adminSessionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
