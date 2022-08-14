package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.SiteUsersAccessRepository;
import io.github.jhipster.sample.service.SiteUsersAccessService;
import io.github.jhipster.sample.service.dto.SiteUsersAccessDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.SiteUsersAccess}.
 */
@RestController
@RequestMapping("/api")
public class SiteUsersAccessResource {

    private final Logger log = LoggerFactory.getLogger(SiteUsersAccessResource.class);

    private static final String ENTITY_NAME = "siteUsersAccess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SiteUsersAccessService siteUsersAccessService;

    private final SiteUsersAccessRepository siteUsersAccessRepository;

    public SiteUsersAccessResource(SiteUsersAccessService siteUsersAccessService, SiteUsersAccessRepository siteUsersAccessRepository) {
        this.siteUsersAccessService = siteUsersAccessService;
        this.siteUsersAccessRepository = siteUsersAccessRepository;
    }

    /**
     * {@code POST  /site-users-accesses} : Create a new siteUsersAccess.
     *
     * @param siteUsersAccessDTO the siteUsersAccessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new siteUsersAccessDTO, or with status {@code 400 (Bad Request)} if the siteUsersAccess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/site-users-accesses")
    public ResponseEntity<SiteUsersAccessDTO> createSiteUsersAccess(@Valid @RequestBody SiteUsersAccessDTO siteUsersAccessDTO)
        throws URISyntaxException {
        log.debug("REST request to save SiteUsersAccess : {}", siteUsersAccessDTO);
        if (siteUsersAccessDTO.getId() != null) {
            throw new BadRequestAlertException("A new siteUsersAccess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SiteUsersAccessDTO result = siteUsersAccessService.save(siteUsersAccessDTO);
        return ResponseEntity
            .created(new URI("/api/site-users-accesses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /site-users-accesses/:id} : Updates an existing siteUsersAccess.
     *
     * @param id the id of the siteUsersAccessDTO to save.
     * @param siteUsersAccessDTO the siteUsersAccessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siteUsersAccessDTO,
     * or with status {@code 400 (Bad Request)} if the siteUsersAccessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the siteUsersAccessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/site-users-accesses/{id}")
    public ResponseEntity<SiteUsersAccessDTO> updateSiteUsersAccess(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SiteUsersAccessDTO siteUsersAccessDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SiteUsersAccess : {}, {}", id, siteUsersAccessDTO);
        if (siteUsersAccessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, siteUsersAccessDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!siteUsersAccessRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SiteUsersAccessDTO result = siteUsersAccessService.update(siteUsersAccessDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siteUsersAccessDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /site-users-accesses/:id} : Partial updates given fields of an existing siteUsersAccess, field will ignore if it is null
     *
     * @param id the id of the siteUsersAccessDTO to save.
     * @param siteUsersAccessDTO the siteUsersAccessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siteUsersAccessDTO,
     * or with status {@code 400 (Bad Request)} if the siteUsersAccessDTO is not valid,
     * or with status {@code 404 (Not Found)} if the siteUsersAccessDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the siteUsersAccessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/site-users-accesses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SiteUsersAccessDTO> partialUpdateSiteUsersAccess(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SiteUsersAccessDTO siteUsersAccessDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SiteUsersAccess partially : {}, {}", id, siteUsersAccessDTO);
        if (siteUsersAccessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, siteUsersAccessDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!siteUsersAccessRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SiteUsersAccessDTO> result = siteUsersAccessService.partialUpdate(siteUsersAccessDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siteUsersAccessDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /site-users-accesses} : get all the siteUsersAccesses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of siteUsersAccesses in body.
     */
    @GetMapping("/site-users-accesses")
    public ResponseEntity<List<SiteUsersAccessDTO>> getAllSiteUsersAccesses(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of SiteUsersAccesses");
        Page<SiteUsersAccessDTO> page = siteUsersAccessService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /site-users-accesses/:id} : get the "id" siteUsersAccess.
     *
     * @param id the id of the siteUsersAccessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the siteUsersAccessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/site-users-accesses/{id}")
    public ResponseEntity<SiteUsersAccessDTO> getSiteUsersAccess(@PathVariable Long id) {
        log.debug("REST request to get SiteUsersAccess : {}", id);
        Optional<SiteUsersAccessDTO> siteUsersAccessDTO = siteUsersAccessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(siteUsersAccessDTO);
    }

    /**
     * {@code DELETE  /site-users-accesses/:id} : delete the "id" siteUsersAccess.
     *
     * @param id the id of the siteUsersAccessDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/site-users-accesses/{id}")
    public ResponseEntity<Void> deleteSiteUsersAccess(@PathVariable Long id) {
        log.debug("REST request to delete SiteUsersAccess : {}", id);
        siteUsersAccessService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
