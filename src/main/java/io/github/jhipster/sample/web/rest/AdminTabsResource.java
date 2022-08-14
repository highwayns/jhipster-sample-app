package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.AdminTabsRepository;
import io.github.jhipster.sample.service.AdminTabsService;
import io.github.jhipster.sample.service.dto.AdminTabsDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.AdminTabs}.
 */
@RestController
@RequestMapping("/api")
public class AdminTabsResource {

    private final Logger log = LoggerFactory.getLogger(AdminTabsResource.class);

    private static final String ENTITY_NAME = "adminTabs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminTabsService adminTabsService;

    private final AdminTabsRepository adminTabsRepository;

    public AdminTabsResource(AdminTabsService adminTabsService, AdminTabsRepository adminTabsRepository) {
        this.adminTabsService = adminTabsService;
        this.adminTabsRepository = adminTabsRepository;
    }

    /**
     * {@code POST  /admin-tabs} : Create a new adminTabs.
     *
     * @param adminTabsDTO the adminTabsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adminTabsDTO, or with status {@code 400 (Bad Request)} if the adminTabs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin-tabs")
    public ResponseEntity<AdminTabsDTO> createAdminTabs(@Valid @RequestBody AdminTabsDTO adminTabsDTO) throws URISyntaxException {
        log.debug("REST request to save AdminTabs : {}", adminTabsDTO);
        if (adminTabsDTO.getId() != null) {
            throw new BadRequestAlertException("A new adminTabs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminTabsDTO result = adminTabsService.save(adminTabsDTO);
        return ResponseEntity
            .created(new URI("/api/admin-tabs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admin-tabs/:id} : Updates an existing adminTabs.
     *
     * @param id the id of the adminTabsDTO to save.
     * @param adminTabsDTO the adminTabsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminTabsDTO,
     * or with status {@code 400 (Bad Request)} if the adminTabsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adminTabsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin-tabs/{id}")
    public ResponseEntity<AdminTabsDTO> updateAdminTabs(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdminTabsDTO adminTabsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdminTabs : {}, {}", id, adminTabsDTO);
        if (adminTabsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminTabsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminTabsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdminTabsDTO result = adminTabsService.update(adminTabsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminTabsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /admin-tabs/:id} : Partial updates given fields of an existing adminTabs, field will ignore if it is null
     *
     * @param id the id of the adminTabsDTO to save.
     * @param adminTabsDTO the adminTabsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminTabsDTO,
     * or with status {@code 400 (Bad Request)} if the adminTabsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the adminTabsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the adminTabsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/admin-tabs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdminTabsDTO> partialUpdateAdminTabs(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdminTabsDTO adminTabsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdminTabs partially : {}, {}", id, adminTabsDTO);
        if (adminTabsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminTabsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminTabsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdminTabsDTO> result = adminTabsService.partialUpdate(adminTabsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminTabsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /admin-tabs} : get all the adminTabs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adminTabs in body.
     */
    @GetMapping("/admin-tabs")
    public ResponseEntity<List<AdminTabsDTO>> getAllAdminTabs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AdminTabs");
        Page<AdminTabsDTO> page = adminTabsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /admin-tabs/:id} : get the "id" adminTabs.
     *
     * @param id the id of the adminTabsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adminTabsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin-tabs/{id}")
    public ResponseEntity<AdminTabsDTO> getAdminTabs(@PathVariable Long id) {
        log.debug("REST request to get AdminTabs : {}", id);
        Optional<AdminTabsDTO> adminTabsDTO = adminTabsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adminTabsDTO);
    }

    /**
     * {@code DELETE  /admin-tabs/:id} : delete the "id" adminTabs.
     *
     * @param id the id of the adminTabsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin-tabs/{id}")
    public ResponseEntity<Void> deleteAdminTabs(@PathVariable Long id) {
        log.debug("REST request to delete AdminTabs : {}", id);
        adminTabsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
