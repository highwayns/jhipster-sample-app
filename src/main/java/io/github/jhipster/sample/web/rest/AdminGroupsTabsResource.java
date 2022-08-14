package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.AdminGroupsTabsRepository;
import io.github.jhipster.sample.service.AdminGroupsTabsService;
import io.github.jhipster.sample.service.dto.AdminGroupsTabsDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.AdminGroupsTabs}.
 */
@RestController
@RequestMapping("/api")
public class AdminGroupsTabsResource {

    private final Logger log = LoggerFactory.getLogger(AdminGroupsTabsResource.class);

    private static final String ENTITY_NAME = "adminGroupsTabs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminGroupsTabsService adminGroupsTabsService;

    private final AdminGroupsTabsRepository adminGroupsTabsRepository;

    public AdminGroupsTabsResource(AdminGroupsTabsService adminGroupsTabsService, AdminGroupsTabsRepository adminGroupsTabsRepository) {
        this.adminGroupsTabsService = adminGroupsTabsService;
        this.adminGroupsTabsRepository = adminGroupsTabsRepository;
    }

    /**
     * {@code POST  /admin-groups-tabs} : Create a new adminGroupsTabs.
     *
     * @param adminGroupsTabsDTO the adminGroupsTabsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adminGroupsTabsDTO, or with status {@code 400 (Bad Request)} if the adminGroupsTabs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin-groups-tabs")
    public ResponseEntity<AdminGroupsTabsDTO> createAdminGroupsTabs(@Valid @RequestBody AdminGroupsTabsDTO adminGroupsTabsDTO)
        throws URISyntaxException {
        log.debug("REST request to save AdminGroupsTabs : {}", adminGroupsTabsDTO);
        if (adminGroupsTabsDTO.getId() != null) {
            throw new BadRequestAlertException("A new adminGroupsTabs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminGroupsTabsDTO result = adminGroupsTabsService.save(adminGroupsTabsDTO);
        return ResponseEntity
            .created(new URI("/api/admin-groups-tabs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admin-groups-tabs/:id} : Updates an existing adminGroupsTabs.
     *
     * @param id the id of the adminGroupsTabsDTO to save.
     * @param adminGroupsTabsDTO the adminGroupsTabsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminGroupsTabsDTO,
     * or with status {@code 400 (Bad Request)} if the adminGroupsTabsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adminGroupsTabsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin-groups-tabs/{id}")
    public ResponseEntity<AdminGroupsTabsDTO> updateAdminGroupsTabs(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdminGroupsTabsDTO adminGroupsTabsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdminGroupsTabs : {}, {}", id, adminGroupsTabsDTO);
        if (adminGroupsTabsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminGroupsTabsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminGroupsTabsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdminGroupsTabsDTO result = adminGroupsTabsService.update(adminGroupsTabsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminGroupsTabsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /admin-groups-tabs/:id} : Partial updates given fields of an existing adminGroupsTabs, field will ignore if it is null
     *
     * @param id the id of the adminGroupsTabsDTO to save.
     * @param adminGroupsTabsDTO the adminGroupsTabsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminGroupsTabsDTO,
     * or with status {@code 400 (Bad Request)} if the adminGroupsTabsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the adminGroupsTabsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the adminGroupsTabsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/admin-groups-tabs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdminGroupsTabsDTO> partialUpdateAdminGroupsTabs(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdminGroupsTabsDTO adminGroupsTabsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdminGroupsTabs partially : {}, {}", id, adminGroupsTabsDTO);
        if (adminGroupsTabsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminGroupsTabsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminGroupsTabsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdminGroupsTabsDTO> result = adminGroupsTabsService.partialUpdate(adminGroupsTabsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminGroupsTabsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /admin-groups-tabs} : get all the adminGroupsTabs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adminGroupsTabs in body.
     */
    @GetMapping("/admin-groups-tabs")
    public ResponseEntity<List<AdminGroupsTabsDTO>> getAllAdminGroupsTabs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of AdminGroupsTabs");
        Page<AdminGroupsTabsDTO> page = adminGroupsTabsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /admin-groups-tabs/:id} : get the "id" adminGroupsTabs.
     *
     * @param id the id of the adminGroupsTabsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adminGroupsTabsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin-groups-tabs/{id}")
    public ResponseEntity<AdminGroupsTabsDTO> getAdminGroupsTabs(@PathVariable Long id) {
        log.debug("REST request to get AdminGroupsTabs : {}", id);
        Optional<AdminGroupsTabsDTO> adminGroupsTabsDTO = adminGroupsTabsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adminGroupsTabsDTO);
    }

    /**
     * {@code DELETE  /admin-groups-tabs/:id} : delete the "id" adminGroupsTabs.
     *
     * @param id the id of the adminGroupsTabsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin-groups-tabs/{id}")
    public ResponseEntity<Void> deleteAdminGroupsTabs(@PathVariable Long id) {
        log.debug("REST request to delete AdminGroupsTabs : {}", id);
        adminGroupsTabsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
