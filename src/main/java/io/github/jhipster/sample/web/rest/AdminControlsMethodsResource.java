package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.AdminControlsMethodsRepository;
import io.github.jhipster.sample.service.AdminControlsMethodsService;
import io.github.jhipster.sample.service.dto.AdminControlsMethodsDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.AdminControlsMethods}.
 */
@RestController
@RequestMapping("/api")
public class AdminControlsMethodsResource {

    private final Logger log = LoggerFactory.getLogger(AdminControlsMethodsResource.class);

    private static final String ENTITY_NAME = "adminControlsMethods";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminControlsMethodsService adminControlsMethodsService;

    private final AdminControlsMethodsRepository adminControlsMethodsRepository;

    public AdminControlsMethodsResource(
        AdminControlsMethodsService adminControlsMethodsService,
        AdminControlsMethodsRepository adminControlsMethodsRepository
    ) {
        this.adminControlsMethodsService = adminControlsMethodsService;
        this.adminControlsMethodsRepository = adminControlsMethodsRepository;
    }

    /**
     * {@code POST  /admin-controls-methods} : Create a new adminControlsMethods.
     *
     * @param adminControlsMethodsDTO the adminControlsMethodsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adminControlsMethodsDTO, or with status {@code 400 (Bad Request)} if the adminControlsMethods has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin-controls-methods")
    public ResponseEntity<AdminControlsMethodsDTO> createAdminControlsMethods(
        @Valid @RequestBody AdminControlsMethodsDTO adminControlsMethodsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save AdminControlsMethods : {}", adminControlsMethodsDTO);
        if (adminControlsMethodsDTO.getId() != null) {
            throw new BadRequestAlertException("A new adminControlsMethods cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminControlsMethodsDTO result = adminControlsMethodsService.save(adminControlsMethodsDTO);
        return ResponseEntity
            .created(new URI("/api/admin-controls-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admin-controls-methods/:id} : Updates an existing adminControlsMethods.
     *
     * @param id the id of the adminControlsMethodsDTO to save.
     * @param adminControlsMethodsDTO the adminControlsMethodsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminControlsMethodsDTO,
     * or with status {@code 400 (Bad Request)} if the adminControlsMethodsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adminControlsMethodsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin-controls-methods/{id}")
    public ResponseEntity<AdminControlsMethodsDTO> updateAdminControlsMethods(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdminControlsMethodsDTO adminControlsMethodsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdminControlsMethods : {}, {}", id, adminControlsMethodsDTO);
        if (adminControlsMethodsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminControlsMethodsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminControlsMethodsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdminControlsMethodsDTO result = adminControlsMethodsService.update(adminControlsMethodsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminControlsMethodsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /admin-controls-methods/:id} : Partial updates given fields of an existing adminControlsMethods, field will ignore if it is null
     *
     * @param id the id of the adminControlsMethodsDTO to save.
     * @param adminControlsMethodsDTO the adminControlsMethodsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminControlsMethodsDTO,
     * or with status {@code 400 (Bad Request)} if the adminControlsMethodsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the adminControlsMethodsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the adminControlsMethodsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/admin-controls-methods/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdminControlsMethodsDTO> partialUpdateAdminControlsMethods(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdminControlsMethodsDTO adminControlsMethodsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdminControlsMethods partially : {}, {}", id, adminControlsMethodsDTO);
        if (adminControlsMethodsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminControlsMethodsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminControlsMethodsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdminControlsMethodsDTO> result = adminControlsMethodsService.partialUpdate(adminControlsMethodsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminControlsMethodsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /admin-controls-methods} : get all the adminControlsMethods.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adminControlsMethods in body.
     */
    @GetMapping("/admin-controls-methods")
    public ResponseEntity<List<AdminControlsMethodsDTO>> getAllAdminControlsMethods(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of AdminControlsMethods");
        Page<AdminControlsMethodsDTO> page = adminControlsMethodsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /admin-controls-methods/:id} : get the "id" adminControlsMethods.
     *
     * @param id the id of the adminControlsMethodsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adminControlsMethodsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin-controls-methods/{id}")
    public ResponseEntity<AdminControlsMethodsDTO> getAdminControlsMethods(@PathVariable Long id) {
        log.debug("REST request to get AdminControlsMethods : {}", id);
        Optional<AdminControlsMethodsDTO> adminControlsMethodsDTO = adminControlsMethodsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adminControlsMethodsDTO);
    }

    /**
     * {@code DELETE  /admin-controls-methods/:id} : delete the "id" adminControlsMethods.
     *
     * @param id the id of the adminControlsMethodsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin-controls-methods/{id}")
    public ResponseEntity<Void> deleteAdminControlsMethods(@PathVariable Long id) {
        log.debug("REST request to delete AdminControlsMethods : {}", id);
        adminControlsMethodsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
