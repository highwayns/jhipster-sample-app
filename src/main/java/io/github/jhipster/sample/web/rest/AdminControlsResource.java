package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.AdminControlsRepository;
import io.github.jhipster.sample.service.AdminControlsService;
import io.github.jhipster.sample.service.dto.AdminControlsDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.AdminControls}.
 */
@RestController
@RequestMapping("/api")
public class AdminControlsResource {

    private final Logger log = LoggerFactory.getLogger(AdminControlsResource.class);

    private static final String ENTITY_NAME = "adminControls";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminControlsService adminControlsService;

    private final AdminControlsRepository adminControlsRepository;

    public AdminControlsResource(AdminControlsService adminControlsService, AdminControlsRepository adminControlsRepository) {
        this.adminControlsService = adminControlsService;
        this.adminControlsRepository = adminControlsRepository;
    }

    /**
     * {@code POST  /admin-controls} : Create a new adminControls.
     *
     * @param adminControlsDTO the adminControlsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adminControlsDTO, or with status {@code 400 (Bad Request)} if the adminControls has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin-controls")
    public ResponseEntity<AdminControlsDTO> createAdminControls(@Valid @RequestBody AdminControlsDTO adminControlsDTO)
        throws URISyntaxException {
        log.debug("REST request to save AdminControls : {}", adminControlsDTO);
        if (adminControlsDTO.getId() != null) {
            throw new BadRequestAlertException("A new adminControls cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminControlsDTO result = adminControlsService.save(adminControlsDTO);
        return ResponseEntity
            .created(new URI("/api/admin-controls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admin-controls/:id} : Updates an existing adminControls.
     *
     * @param id the id of the adminControlsDTO to save.
     * @param adminControlsDTO the adminControlsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminControlsDTO,
     * or with status {@code 400 (Bad Request)} if the adminControlsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adminControlsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin-controls/{id}")
    public ResponseEntity<AdminControlsDTO> updateAdminControls(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdminControlsDTO adminControlsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdminControls : {}, {}", id, adminControlsDTO);
        if (adminControlsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminControlsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminControlsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdminControlsDTO result = adminControlsService.update(adminControlsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminControlsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /admin-controls/:id} : Partial updates given fields of an existing adminControls, field will ignore if it is null
     *
     * @param id the id of the adminControlsDTO to save.
     * @param adminControlsDTO the adminControlsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminControlsDTO,
     * or with status {@code 400 (Bad Request)} if the adminControlsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the adminControlsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the adminControlsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/admin-controls/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdminControlsDTO> partialUpdateAdminControls(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdminControlsDTO adminControlsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdminControls partially : {}, {}", id, adminControlsDTO);
        if (adminControlsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminControlsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminControlsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdminControlsDTO> result = adminControlsService.partialUpdate(adminControlsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminControlsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /admin-controls} : get all the adminControls.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adminControls in body.
     */
    @GetMapping("/admin-controls")
    public ResponseEntity<List<AdminControlsDTO>> getAllAdminControls(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AdminControls");
        Page<AdminControlsDTO> page = adminControlsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /admin-controls/:id} : get the "id" adminControls.
     *
     * @param id the id of the adminControlsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adminControlsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin-controls/{id}")
    public ResponseEntity<AdminControlsDTO> getAdminControls(@PathVariable Long id) {
        log.debug("REST request to get AdminControls : {}", id);
        Optional<AdminControlsDTO> adminControlsDTO = adminControlsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adminControlsDTO);
    }

    /**
     * {@code DELETE  /admin-controls/:id} : delete the "id" adminControls.
     *
     * @param id the id of the adminControlsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin-controls/{id}")
    public ResponseEntity<Void> deleteAdminControls(@PathVariable Long id) {
        log.debug("REST request to delete AdminControls : {}", id);
        adminControlsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
