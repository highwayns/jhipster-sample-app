package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.AdminPagesRepository;
import io.github.jhipster.sample.service.AdminPagesService;
import io.github.jhipster.sample.service.dto.AdminPagesDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.AdminPages}.
 */
@RestController
@RequestMapping("/api")
public class AdminPagesResource {

    private final Logger log = LoggerFactory.getLogger(AdminPagesResource.class);

    private static final String ENTITY_NAME = "adminPages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminPagesService adminPagesService;

    private final AdminPagesRepository adminPagesRepository;

    public AdminPagesResource(AdminPagesService adminPagesService, AdminPagesRepository adminPagesRepository) {
        this.adminPagesService = adminPagesService;
        this.adminPagesRepository = adminPagesRepository;
    }

    /**
     * {@code POST  /admin-pages} : Create a new adminPages.
     *
     * @param adminPagesDTO the adminPagesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adminPagesDTO, or with status {@code 400 (Bad Request)} if the adminPages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin-pages")
    public ResponseEntity<AdminPagesDTO> createAdminPages(@Valid @RequestBody AdminPagesDTO adminPagesDTO) throws URISyntaxException {
        log.debug("REST request to save AdminPages : {}", adminPagesDTO);
        if (adminPagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new adminPages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminPagesDTO result = adminPagesService.save(adminPagesDTO);
        return ResponseEntity
            .created(new URI("/api/admin-pages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admin-pages/:id} : Updates an existing adminPages.
     *
     * @param id the id of the adminPagesDTO to save.
     * @param adminPagesDTO the adminPagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminPagesDTO,
     * or with status {@code 400 (Bad Request)} if the adminPagesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adminPagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin-pages/{id}")
    public ResponseEntity<AdminPagesDTO> updateAdminPages(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdminPagesDTO adminPagesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdminPages : {}, {}", id, adminPagesDTO);
        if (adminPagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminPagesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminPagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdminPagesDTO result = adminPagesService.update(adminPagesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminPagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /admin-pages/:id} : Partial updates given fields of an existing adminPages, field will ignore if it is null
     *
     * @param id the id of the adminPagesDTO to save.
     * @param adminPagesDTO the adminPagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminPagesDTO,
     * or with status {@code 400 (Bad Request)} if the adminPagesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the adminPagesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the adminPagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/admin-pages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdminPagesDTO> partialUpdateAdminPages(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdminPagesDTO adminPagesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdminPages partially : {}, {}", id, adminPagesDTO);
        if (adminPagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminPagesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminPagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdminPagesDTO> result = adminPagesService.partialUpdate(adminPagesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminPagesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /admin-pages} : get all the adminPages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adminPages in body.
     */
    @GetMapping("/admin-pages")
    public ResponseEntity<List<AdminPagesDTO>> getAllAdminPages(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AdminPages");
        Page<AdminPagesDTO> page = adminPagesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /admin-pages/:id} : get the "id" adminPages.
     *
     * @param id the id of the adminPagesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adminPagesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin-pages/{id}")
    public ResponseEntity<AdminPagesDTO> getAdminPages(@PathVariable Long id) {
        log.debug("REST request to get AdminPages : {}", id);
        Optional<AdminPagesDTO> adminPagesDTO = adminPagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adminPagesDTO);
    }

    /**
     * {@code DELETE  /admin-pages/:id} : delete the "id" adminPages.
     *
     * @param id the id of the adminPagesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin-pages/{id}")
    public ResponseEntity<Void> deleteAdminPages(@PathVariable Long id) {
        log.debug("REST request to delete AdminPages : {}", id);
        adminPagesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
