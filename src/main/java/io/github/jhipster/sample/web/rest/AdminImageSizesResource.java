package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.AdminImageSizesRepository;
import io.github.jhipster.sample.service.AdminImageSizesService;
import io.github.jhipster.sample.service.dto.AdminImageSizesDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.AdminImageSizes}.
 */
@RestController
@RequestMapping("/api")
public class AdminImageSizesResource {

    private final Logger log = LoggerFactory.getLogger(AdminImageSizesResource.class);

    private static final String ENTITY_NAME = "adminImageSizes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminImageSizesService adminImageSizesService;

    private final AdminImageSizesRepository adminImageSizesRepository;

    public AdminImageSizesResource(AdminImageSizesService adminImageSizesService, AdminImageSizesRepository adminImageSizesRepository) {
        this.adminImageSizesService = adminImageSizesService;
        this.adminImageSizesRepository = adminImageSizesRepository;
    }

    /**
     * {@code POST  /admin-image-sizes} : Create a new adminImageSizes.
     *
     * @param adminImageSizesDTO the adminImageSizesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adminImageSizesDTO, or with status {@code 400 (Bad Request)} if the adminImageSizes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin-image-sizes")
    public ResponseEntity<AdminImageSizesDTO> createAdminImageSizes(@Valid @RequestBody AdminImageSizesDTO adminImageSizesDTO)
        throws URISyntaxException {
        log.debug("REST request to save AdminImageSizes : {}", adminImageSizesDTO);
        if (adminImageSizesDTO.getId() != null) {
            throw new BadRequestAlertException("A new adminImageSizes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminImageSizesDTO result = adminImageSizesService.save(adminImageSizesDTO);
        return ResponseEntity
            .created(new URI("/api/admin-image-sizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admin-image-sizes/:id} : Updates an existing adminImageSizes.
     *
     * @param id the id of the adminImageSizesDTO to save.
     * @param adminImageSizesDTO the adminImageSizesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminImageSizesDTO,
     * or with status {@code 400 (Bad Request)} if the adminImageSizesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adminImageSizesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin-image-sizes/{id}")
    public ResponseEntity<AdminImageSizesDTO> updateAdminImageSizes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdminImageSizesDTO adminImageSizesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdminImageSizes : {}, {}", id, adminImageSizesDTO);
        if (adminImageSizesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminImageSizesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminImageSizesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdminImageSizesDTO result = adminImageSizesService.update(adminImageSizesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminImageSizesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /admin-image-sizes/:id} : Partial updates given fields of an existing adminImageSizes, field will ignore if it is null
     *
     * @param id the id of the adminImageSizesDTO to save.
     * @param adminImageSizesDTO the adminImageSizesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminImageSizesDTO,
     * or with status {@code 400 (Bad Request)} if the adminImageSizesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the adminImageSizesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the adminImageSizesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/admin-image-sizes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdminImageSizesDTO> partialUpdateAdminImageSizes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdminImageSizesDTO adminImageSizesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdminImageSizes partially : {}, {}", id, adminImageSizesDTO);
        if (adminImageSizesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminImageSizesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminImageSizesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdminImageSizesDTO> result = adminImageSizesService.partialUpdate(adminImageSizesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminImageSizesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /admin-image-sizes} : get all the adminImageSizes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adminImageSizes in body.
     */
    @GetMapping("/admin-image-sizes")
    public ResponseEntity<List<AdminImageSizesDTO>> getAllAdminImageSizes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of AdminImageSizes");
        Page<AdminImageSizesDTO> page = adminImageSizesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /admin-image-sizes/:id} : get the "id" adminImageSizes.
     *
     * @param id the id of the adminImageSizesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adminImageSizesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin-image-sizes/{id}")
    public ResponseEntity<AdminImageSizesDTO> getAdminImageSizes(@PathVariable Long id) {
        log.debug("REST request to get AdminImageSizes : {}", id);
        Optional<AdminImageSizesDTO> adminImageSizesDTO = adminImageSizesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adminImageSizesDTO);
    }

    /**
     * {@code DELETE  /admin-image-sizes/:id} : delete the "id" adminImageSizes.
     *
     * @param id the id of the adminImageSizesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin-image-sizes/{id}")
    public ResponseEntity<Void> deleteAdminImageSizes(@PathVariable Long id) {
        log.debug("REST request to delete AdminImageSizes : {}", id);
        adminImageSizesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
