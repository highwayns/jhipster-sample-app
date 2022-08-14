package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.AdminCronRepository;
import io.github.jhipster.sample.service.AdminCronService;
import io.github.jhipster.sample.service.dto.AdminCronDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.AdminCron}.
 */
@RestController
@RequestMapping("/api")
public class AdminCronResource {

    private final Logger log = LoggerFactory.getLogger(AdminCronResource.class);

    private static final String ENTITY_NAME = "adminCron";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminCronService adminCronService;

    private final AdminCronRepository adminCronRepository;

    public AdminCronResource(AdminCronService adminCronService, AdminCronRepository adminCronRepository) {
        this.adminCronService = adminCronService;
        this.adminCronRepository = adminCronRepository;
    }

    /**
     * {@code POST  /admin-crons} : Create a new adminCron.
     *
     * @param adminCronDTO the adminCronDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adminCronDTO, or with status {@code 400 (Bad Request)} if the adminCron has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin-crons")
    public ResponseEntity<AdminCronDTO> createAdminCron(@Valid @RequestBody AdminCronDTO adminCronDTO) throws URISyntaxException {
        log.debug("REST request to save AdminCron : {}", adminCronDTO);
        if (adminCronDTO.getId() != null) {
            throw new BadRequestAlertException("A new adminCron cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminCronDTO result = adminCronService.save(adminCronDTO);
        return ResponseEntity
            .created(new URI("/api/admin-crons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admin-crons/:id} : Updates an existing adminCron.
     *
     * @param id the id of the adminCronDTO to save.
     * @param adminCronDTO the adminCronDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminCronDTO,
     * or with status {@code 400 (Bad Request)} if the adminCronDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adminCronDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin-crons/{id}")
    public ResponseEntity<AdminCronDTO> updateAdminCron(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdminCronDTO adminCronDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdminCron : {}, {}", id, adminCronDTO);
        if (adminCronDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminCronDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminCronRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdminCronDTO result = adminCronService.update(adminCronDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminCronDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /admin-crons/:id} : Partial updates given fields of an existing adminCron, field will ignore if it is null
     *
     * @param id the id of the adminCronDTO to save.
     * @param adminCronDTO the adminCronDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminCronDTO,
     * or with status {@code 400 (Bad Request)} if the adminCronDTO is not valid,
     * or with status {@code 404 (Not Found)} if the adminCronDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the adminCronDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/admin-crons/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdminCronDTO> partialUpdateAdminCron(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdminCronDTO adminCronDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdminCron partially : {}, {}", id, adminCronDTO);
        if (adminCronDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminCronDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminCronRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdminCronDTO> result = adminCronService.partialUpdate(adminCronDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminCronDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /admin-crons} : get all the adminCrons.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adminCrons in body.
     */
    @GetMapping("/admin-crons")
    public ResponseEntity<List<AdminCronDTO>> getAllAdminCrons(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AdminCrons");
        Page<AdminCronDTO> page = adminCronService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /admin-crons/:id} : get the "id" adminCron.
     *
     * @param id the id of the adminCronDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adminCronDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin-crons/{id}")
    public ResponseEntity<AdminCronDTO> getAdminCron(@PathVariable Long id) {
        log.debug("REST request to get AdminCron : {}", id);
        Optional<AdminCronDTO> adminCronDTO = adminCronService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adminCronDTO);
    }

    /**
     * {@code DELETE  /admin-crons/:id} : delete the "id" adminCron.
     *
     * @param id the id of the adminCronDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin-crons/{id}")
    public ResponseEntity<Void> deleteAdminCron(@PathVariable Long id) {
        log.debug("REST request to delete AdminCron : {}", id);
        adminCronService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
