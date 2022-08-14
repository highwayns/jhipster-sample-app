package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.SiteUsersBalancesRepository;
import io.github.jhipster.sample.service.SiteUsersBalancesService;
import io.github.jhipster.sample.service.dto.SiteUsersBalancesDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.SiteUsersBalances}.
 */
@RestController
@RequestMapping("/api")
public class SiteUsersBalancesResource {

    private final Logger log = LoggerFactory.getLogger(SiteUsersBalancesResource.class);

    private static final String ENTITY_NAME = "siteUsersBalances";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SiteUsersBalancesService siteUsersBalancesService;

    private final SiteUsersBalancesRepository siteUsersBalancesRepository;

    public SiteUsersBalancesResource(
        SiteUsersBalancesService siteUsersBalancesService,
        SiteUsersBalancesRepository siteUsersBalancesRepository
    ) {
        this.siteUsersBalancesService = siteUsersBalancesService;
        this.siteUsersBalancesRepository = siteUsersBalancesRepository;
    }

    /**
     * {@code POST  /site-users-balances} : Create a new siteUsersBalances.
     *
     * @param siteUsersBalancesDTO the siteUsersBalancesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new siteUsersBalancesDTO, or with status {@code 400 (Bad Request)} if the siteUsersBalances has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/site-users-balances")
    public ResponseEntity<SiteUsersBalancesDTO> createSiteUsersBalances(@Valid @RequestBody SiteUsersBalancesDTO siteUsersBalancesDTO)
        throws URISyntaxException {
        log.debug("REST request to save SiteUsersBalances : {}", siteUsersBalancesDTO);
        if (siteUsersBalancesDTO.getId() != null) {
            throw new BadRequestAlertException("A new siteUsersBalances cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SiteUsersBalancesDTO result = siteUsersBalancesService.save(siteUsersBalancesDTO);
        return ResponseEntity
            .created(new URI("/api/site-users-balances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /site-users-balances/:id} : Updates an existing siteUsersBalances.
     *
     * @param id the id of the siteUsersBalancesDTO to save.
     * @param siteUsersBalancesDTO the siteUsersBalancesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siteUsersBalancesDTO,
     * or with status {@code 400 (Bad Request)} if the siteUsersBalancesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the siteUsersBalancesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/site-users-balances/{id}")
    public ResponseEntity<SiteUsersBalancesDTO> updateSiteUsersBalances(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SiteUsersBalancesDTO siteUsersBalancesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SiteUsersBalances : {}, {}", id, siteUsersBalancesDTO);
        if (siteUsersBalancesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, siteUsersBalancesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!siteUsersBalancesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SiteUsersBalancesDTO result = siteUsersBalancesService.update(siteUsersBalancesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siteUsersBalancesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /site-users-balances/:id} : Partial updates given fields of an existing siteUsersBalances, field will ignore if it is null
     *
     * @param id the id of the siteUsersBalancesDTO to save.
     * @param siteUsersBalancesDTO the siteUsersBalancesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siteUsersBalancesDTO,
     * or with status {@code 400 (Bad Request)} if the siteUsersBalancesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the siteUsersBalancesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the siteUsersBalancesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/site-users-balances/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SiteUsersBalancesDTO> partialUpdateSiteUsersBalances(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SiteUsersBalancesDTO siteUsersBalancesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SiteUsersBalances partially : {}, {}", id, siteUsersBalancesDTO);
        if (siteUsersBalancesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, siteUsersBalancesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!siteUsersBalancesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SiteUsersBalancesDTO> result = siteUsersBalancesService.partialUpdate(siteUsersBalancesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siteUsersBalancesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /site-users-balances} : get all the siteUsersBalances.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of siteUsersBalances in body.
     */
    @GetMapping("/site-users-balances")
    public ResponseEntity<List<SiteUsersBalancesDTO>> getAllSiteUsersBalances(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of SiteUsersBalances");
        Page<SiteUsersBalancesDTO> page = siteUsersBalancesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /site-users-balances/:id} : get the "id" siteUsersBalances.
     *
     * @param id the id of the siteUsersBalancesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the siteUsersBalancesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/site-users-balances/{id}")
    public ResponseEntity<SiteUsersBalancesDTO> getSiteUsersBalances(@PathVariable Long id) {
        log.debug("REST request to get SiteUsersBalances : {}", id);
        Optional<SiteUsersBalancesDTO> siteUsersBalancesDTO = siteUsersBalancesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(siteUsersBalancesDTO);
    }

    /**
     * {@code DELETE  /site-users-balances/:id} : delete the "id" siteUsersBalances.
     *
     * @param id the id of the siteUsersBalancesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/site-users-balances/{id}")
    public ResponseEntity<Void> deleteSiteUsersBalances(@PathVariable Long id) {
        log.debug("REST request to delete SiteUsersBalances : {}", id);
        siteUsersBalancesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
