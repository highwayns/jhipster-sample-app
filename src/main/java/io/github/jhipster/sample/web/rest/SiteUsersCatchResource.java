package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.SiteUsersCatchRepository;
import io.github.jhipster.sample.service.SiteUsersCatchService;
import io.github.jhipster.sample.service.dto.SiteUsersCatchDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.SiteUsersCatch}.
 */
@RestController
@RequestMapping("/api")
public class SiteUsersCatchResource {

    private final Logger log = LoggerFactory.getLogger(SiteUsersCatchResource.class);

    private static final String ENTITY_NAME = "siteUsersCatch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SiteUsersCatchService siteUsersCatchService;

    private final SiteUsersCatchRepository siteUsersCatchRepository;

    public SiteUsersCatchResource(SiteUsersCatchService siteUsersCatchService, SiteUsersCatchRepository siteUsersCatchRepository) {
        this.siteUsersCatchService = siteUsersCatchService;
        this.siteUsersCatchRepository = siteUsersCatchRepository;
    }

    /**
     * {@code POST  /site-users-catches} : Create a new siteUsersCatch.
     *
     * @param siteUsersCatchDTO the siteUsersCatchDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new siteUsersCatchDTO, or with status {@code 400 (Bad Request)} if the siteUsersCatch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/site-users-catches")
    public ResponseEntity<SiteUsersCatchDTO> createSiteUsersCatch(@Valid @RequestBody SiteUsersCatchDTO siteUsersCatchDTO)
        throws URISyntaxException {
        log.debug("REST request to save SiteUsersCatch : {}", siteUsersCatchDTO);
        if (siteUsersCatchDTO.getId() != null) {
            throw new BadRequestAlertException("A new siteUsersCatch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SiteUsersCatchDTO result = siteUsersCatchService.save(siteUsersCatchDTO);
        return ResponseEntity
            .created(new URI("/api/site-users-catches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /site-users-catches/:id} : Updates an existing siteUsersCatch.
     *
     * @param id the id of the siteUsersCatchDTO to save.
     * @param siteUsersCatchDTO the siteUsersCatchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siteUsersCatchDTO,
     * or with status {@code 400 (Bad Request)} if the siteUsersCatchDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the siteUsersCatchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/site-users-catches/{id}")
    public ResponseEntity<SiteUsersCatchDTO> updateSiteUsersCatch(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SiteUsersCatchDTO siteUsersCatchDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SiteUsersCatch : {}, {}", id, siteUsersCatchDTO);
        if (siteUsersCatchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, siteUsersCatchDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!siteUsersCatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SiteUsersCatchDTO result = siteUsersCatchService.update(siteUsersCatchDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siteUsersCatchDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /site-users-catches/:id} : Partial updates given fields of an existing siteUsersCatch, field will ignore if it is null
     *
     * @param id the id of the siteUsersCatchDTO to save.
     * @param siteUsersCatchDTO the siteUsersCatchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siteUsersCatchDTO,
     * or with status {@code 400 (Bad Request)} if the siteUsersCatchDTO is not valid,
     * or with status {@code 404 (Not Found)} if the siteUsersCatchDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the siteUsersCatchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/site-users-catches/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SiteUsersCatchDTO> partialUpdateSiteUsersCatch(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SiteUsersCatchDTO siteUsersCatchDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SiteUsersCatch partially : {}, {}", id, siteUsersCatchDTO);
        if (siteUsersCatchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, siteUsersCatchDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!siteUsersCatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SiteUsersCatchDTO> result = siteUsersCatchService.partialUpdate(siteUsersCatchDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siteUsersCatchDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /site-users-catches} : get all the siteUsersCatches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of siteUsersCatches in body.
     */
    @GetMapping("/site-users-catches")
    public ResponseEntity<List<SiteUsersCatchDTO>> getAllSiteUsersCatches(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of SiteUsersCatches");
        Page<SiteUsersCatchDTO> page = siteUsersCatchService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /site-users-catches/:id} : get the "id" siteUsersCatch.
     *
     * @param id the id of the siteUsersCatchDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the siteUsersCatchDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/site-users-catches/{id}")
    public ResponseEntity<SiteUsersCatchDTO> getSiteUsersCatch(@PathVariable Long id) {
        log.debug("REST request to get SiteUsersCatch : {}", id);
        Optional<SiteUsersCatchDTO> siteUsersCatchDTO = siteUsersCatchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(siteUsersCatchDTO);
    }

    /**
     * {@code DELETE  /site-users-catches/:id} : delete the "id" siteUsersCatch.
     *
     * @param id the id of the siteUsersCatchDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/site-users-catches/{id}")
    public ResponseEntity<Void> deleteSiteUsersCatch(@PathVariable Long id) {
        log.debug("REST request to delete SiteUsersCatch : {}", id);
        siteUsersCatchService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
