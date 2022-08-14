package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.AdminGroupsPagesRepository;
import io.github.jhipster.sample.service.AdminGroupsPagesService;
import io.github.jhipster.sample.service.dto.AdminGroupsPagesDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.AdminGroupsPages}.
 */
@RestController
@RequestMapping("/api")
public class AdminGroupsPagesResource {

    private final Logger log = LoggerFactory.getLogger(AdminGroupsPagesResource.class);

    private static final String ENTITY_NAME = "adminGroupsPages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminGroupsPagesService adminGroupsPagesService;

    private final AdminGroupsPagesRepository adminGroupsPagesRepository;

    public AdminGroupsPagesResource(
        AdminGroupsPagesService adminGroupsPagesService,
        AdminGroupsPagesRepository adminGroupsPagesRepository
    ) {
        this.adminGroupsPagesService = adminGroupsPagesService;
        this.adminGroupsPagesRepository = adminGroupsPagesRepository;
    }

    /**
     * {@code POST  /admin-groups-pages} : Create a new adminGroupsPages.
     *
     * @param adminGroupsPagesDTO the adminGroupsPagesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adminGroupsPagesDTO, or with status {@code 400 (Bad Request)} if the adminGroupsPages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin-groups-pages")
    public ResponseEntity<AdminGroupsPagesDTO> createAdminGroupsPages(@Valid @RequestBody AdminGroupsPagesDTO adminGroupsPagesDTO)
        throws URISyntaxException {
        log.debug("REST request to save AdminGroupsPages : {}", adminGroupsPagesDTO);
        if (adminGroupsPagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new adminGroupsPages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminGroupsPagesDTO result = adminGroupsPagesService.save(adminGroupsPagesDTO);
        return ResponseEntity
            .created(new URI("/api/admin-groups-pages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admin-groups-pages/:id} : Updates an existing adminGroupsPages.
     *
     * @param id the id of the adminGroupsPagesDTO to save.
     * @param adminGroupsPagesDTO the adminGroupsPagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminGroupsPagesDTO,
     * or with status {@code 400 (Bad Request)} if the adminGroupsPagesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adminGroupsPagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin-groups-pages/{id}")
    public ResponseEntity<AdminGroupsPagesDTO> updateAdminGroupsPages(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdminGroupsPagesDTO adminGroupsPagesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdminGroupsPages : {}, {}", id, adminGroupsPagesDTO);
        if (adminGroupsPagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminGroupsPagesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminGroupsPagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdminGroupsPagesDTO result = adminGroupsPagesService.update(adminGroupsPagesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminGroupsPagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /admin-groups-pages/:id} : Partial updates given fields of an existing adminGroupsPages, field will ignore if it is null
     *
     * @param id the id of the adminGroupsPagesDTO to save.
     * @param adminGroupsPagesDTO the adminGroupsPagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminGroupsPagesDTO,
     * or with status {@code 400 (Bad Request)} if the adminGroupsPagesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the adminGroupsPagesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the adminGroupsPagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/admin-groups-pages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdminGroupsPagesDTO> partialUpdateAdminGroupsPages(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdminGroupsPagesDTO adminGroupsPagesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdminGroupsPages partially : {}, {}", id, adminGroupsPagesDTO);
        if (adminGroupsPagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminGroupsPagesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminGroupsPagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdminGroupsPagesDTO> result = adminGroupsPagesService.partialUpdate(adminGroupsPagesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminGroupsPagesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /admin-groups-pages} : get all the adminGroupsPages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adminGroupsPages in body.
     */
    @GetMapping("/admin-groups-pages")
    public ResponseEntity<List<AdminGroupsPagesDTO>> getAllAdminGroupsPages(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of AdminGroupsPages");
        Page<AdminGroupsPagesDTO> page = adminGroupsPagesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /admin-groups-pages/:id} : get the "id" adminGroupsPages.
     *
     * @param id the id of the adminGroupsPagesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adminGroupsPagesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin-groups-pages/{id}")
    public ResponseEntity<AdminGroupsPagesDTO> getAdminGroupsPages(@PathVariable Long id) {
        log.debug("REST request to get AdminGroupsPages : {}", id);
        Optional<AdminGroupsPagesDTO> adminGroupsPagesDTO = adminGroupsPagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adminGroupsPagesDTO);
    }

    /**
     * {@code DELETE  /admin-groups-pages/:id} : delete the "id" adminGroupsPages.
     *
     * @param id the id of the adminGroupsPagesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin-groups-pages/{id}")
    public ResponseEntity<Void> deleteAdminGroupsPages(@PathVariable Long id) {
        log.debug("REST request to delete AdminGroupsPages : {}", id);
        adminGroupsPagesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
