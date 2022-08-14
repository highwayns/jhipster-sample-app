package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.AdminUsersRepository;
import io.github.jhipster.sample.service.AdminUsersService;
import io.github.jhipster.sample.service.dto.AdminUsersDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.AdminUsers}.
 */
@RestController
@RequestMapping("/api")
public class AdminUsersResource {

    private final Logger log = LoggerFactory.getLogger(AdminUsersResource.class);

    private static final String ENTITY_NAME = "adminUsers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminUsersService adminUsersService;

    private final AdminUsersRepository adminUsersRepository;

    public AdminUsersResource(AdminUsersService adminUsersService, AdminUsersRepository adminUsersRepository) {
        this.adminUsersService = adminUsersService;
        this.adminUsersRepository = adminUsersRepository;
    }

    /**
     * {@code POST  /admin-users} : Create a new adminUsers.
     *
     * @param adminUsersDTO the adminUsersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adminUsersDTO, or with status {@code 400 (Bad Request)} if the adminUsers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin-users")
    public ResponseEntity<AdminUsersDTO> createAdminUsers(@Valid @RequestBody AdminUsersDTO adminUsersDTO) throws URISyntaxException {
        log.debug("REST request to save AdminUsers : {}", adminUsersDTO);
        if (adminUsersDTO.getId() != null) {
            throw new BadRequestAlertException("A new adminUsers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminUsersDTO result = adminUsersService.save(adminUsersDTO);
        return ResponseEntity
            .created(new URI("/api/admin-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admin-users/:id} : Updates an existing adminUsers.
     *
     * @param id the id of the adminUsersDTO to save.
     * @param adminUsersDTO the adminUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminUsersDTO,
     * or with status {@code 400 (Bad Request)} if the adminUsersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adminUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin-users/{id}")
    public ResponseEntity<AdminUsersDTO> updateAdminUsers(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdminUsersDTO adminUsersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdminUsers : {}, {}", id, adminUsersDTO);
        if (adminUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminUsersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminUsersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdminUsersDTO result = adminUsersService.update(adminUsersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminUsersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /admin-users/:id} : Partial updates given fields of an existing adminUsers, field will ignore if it is null
     *
     * @param id the id of the adminUsersDTO to save.
     * @param adminUsersDTO the adminUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminUsersDTO,
     * or with status {@code 400 (Bad Request)} if the adminUsersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the adminUsersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the adminUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/admin-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdminUsersDTO> partialUpdateAdminUsers(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdminUsersDTO adminUsersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdminUsers partially : {}, {}", id, adminUsersDTO);
        if (adminUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminUsersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminUsersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdminUsersDTO> result = adminUsersService.partialUpdate(adminUsersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminUsersDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /admin-users} : get all the adminUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adminUsers in body.
     */
    @GetMapping("/admin-users")
    public ResponseEntity<List<AdminUsersDTO>> getAllAdminUsers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AdminUsers");
        Page<AdminUsersDTO> page = adminUsersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /admin-users/:id} : get the "id" adminUsers.
     *
     * @param id the id of the adminUsersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adminUsersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin-users/{id}")
    public ResponseEntity<AdminUsersDTO> getAdminUsers(@PathVariable Long id) {
        log.debug("REST request to get AdminUsers : {}", id);
        Optional<AdminUsersDTO> adminUsersDTO = adminUsersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adminUsersDTO);
    }

    /**
     * {@code DELETE  /admin-users/:id} : delete the "id" adminUsers.
     *
     * @param id the id of the adminUsersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin-users/{id}")
    public ResponseEntity<Void> deleteAdminUsers(@PathVariable Long id) {
        log.debug("REST request to delete AdminUsers : {}", id);
        adminUsersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
