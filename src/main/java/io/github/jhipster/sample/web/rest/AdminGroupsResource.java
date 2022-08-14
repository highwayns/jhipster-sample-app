package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.AdminGroupsRepository;
import io.github.jhipster.sample.service.AdminGroupsService;
import io.github.jhipster.sample.service.dto.AdminGroupsDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.AdminGroups}.
 */
@RestController
@RequestMapping("/api")
public class AdminGroupsResource {

    private final Logger log = LoggerFactory.getLogger(AdminGroupsResource.class);

    private static final String ENTITY_NAME = "adminGroups";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminGroupsService adminGroupsService;

    private final AdminGroupsRepository adminGroupsRepository;

    public AdminGroupsResource(AdminGroupsService adminGroupsService, AdminGroupsRepository adminGroupsRepository) {
        this.adminGroupsService = adminGroupsService;
        this.adminGroupsRepository = adminGroupsRepository;
    }

    /**
     * {@code POST  /admin-groups} : Create a new adminGroups.
     *
     * @param adminGroupsDTO the adminGroupsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adminGroupsDTO, or with status {@code 400 (Bad Request)} if the adminGroups has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin-groups")
    public ResponseEntity<AdminGroupsDTO> createAdminGroups(@Valid @RequestBody AdminGroupsDTO adminGroupsDTO) throws URISyntaxException {
        log.debug("REST request to save AdminGroups : {}", adminGroupsDTO);
        if (adminGroupsDTO.getId() != null) {
            throw new BadRequestAlertException("A new adminGroups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminGroupsDTO result = adminGroupsService.save(adminGroupsDTO);
        return ResponseEntity
            .created(new URI("/api/admin-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admin-groups/:id} : Updates an existing adminGroups.
     *
     * @param id the id of the adminGroupsDTO to save.
     * @param adminGroupsDTO the adminGroupsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminGroupsDTO,
     * or with status {@code 400 (Bad Request)} if the adminGroupsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adminGroupsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin-groups/{id}")
    public ResponseEntity<AdminGroupsDTO> updateAdminGroups(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdminGroupsDTO adminGroupsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdminGroups : {}, {}", id, adminGroupsDTO);
        if (adminGroupsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminGroupsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminGroupsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdminGroupsDTO result = adminGroupsService.update(adminGroupsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminGroupsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /admin-groups/:id} : Partial updates given fields of an existing adminGroups, field will ignore if it is null
     *
     * @param id the id of the adminGroupsDTO to save.
     * @param adminGroupsDTO the adminGroupsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminGroupsDTO,
     * or with status {@code 400 (Bad Request)} if the adminGroupsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the adminGroupsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the adminGroupsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/admin-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdminGroupsDTO> partialUpdateAdminGroups(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdminGroupsDTO adminGroupsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdminGroups partially : {}, {}", id, adminGroupsDTO);
        if (adminGroupsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminGroupsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminGroupsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdminGroupsDTO> result = adminGroupsService.partialUpdate(adminGroupsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminGroupsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /admin-groups} : get all the adminGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adminGroups in body.
     */
    @GetMapping("/admin-groups")
    public ResponseEntity<List<AdminGroupsDTO>> getAllAdminGroups(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AdminGroups");
        Page<AdminGroupsDTO> page = adminGroupsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /admin-groups/:id} : get the "id" adminGroups.
     *
     * @param id the id of the adminGroupsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adminGroupsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin-groups/{id}")
    public ResponseEntity<AdminGroupsDTO> getAdminGroups(@PathVariable Long id) {
        log.debug("REST request to get AdminGroups : {}", id);
        Optional<AdminGroupsDTO> adminGroupsDTO = adminGroupsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adminGroupsDTO);
    }

    /**
     * {@code DELETE  /admin-groups/:id} : delete the "id" adminGroups.
     *
     * @param id the id of the adminGroupsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin-groups/{id}")
    public ResponseEntity<Void> deleteAdminGroups(@PathVariable Long id) {
        log.debug("REST request to delete AdminGroups : {}", id);
        adminGroupsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
