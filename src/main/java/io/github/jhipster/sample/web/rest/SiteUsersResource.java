package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.SiteUsersRepository;
import io.github.jhipster.sample.service.SiteUsersService;
import io.github.jhipster.sample.service.dto.SiteUsersDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.SiteUsers}.
 */
@RestController
@RequestMapping("/api")
public class SiteUsersResource {

    private final Logger log = LoggerFactory.getLogger(SiteUsersResource.class);

    private static final String ENTITY_NAME = "siteUsers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SiteUsersService siteUsersService;

    private final SiteUsersRepository siteUsersRepository;

    public SiteUsersResource(SiteUsersService siteUsersService, SiteUsersRepository siteUsersRepository) {
        this.siteUsersService = siteUsersService;
        this.siteUsersRepository = siteUsersRepository;
    }

    /**
     * {@code POST  /site-users} : Create a new siteUsers.
     *
     * @param siteUsersDTO the siteUsersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new siteUsersDTO, or with status {@code 400 (Bad Request)} if the siteUsers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/site-users")
    public ResponseEntity<SiteUsersDTO> createSiteUsers(@Valid @RequestBody SiteUsersDTO siteUsersDTO) throws URISyntaxException {
        log.debug("REST request to save SiteUsers : {}", siteUsersDTO);
        if (siteUsersDTO.getId() != null) {
            throw new BadRequestAlertException("A new siteUsers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SiteUsersDTO result = siteUsersService.save(siteUsersDTO);
        return ResponseEntity
            .created(new URI("/api/site-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /site-users/:id} : Updates an existing siteUsers.
     *
     * @param id the id of the siteUsersDTO to save.
     * @param siteUsersDTO the siteUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siteUsersDTO,
     * or with status {@code 400 (Bad Request)} if the siteUsersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the siteUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/site-users/{id}")
    public ResponseEntity<SiteUsersDTO> updateSiteUsers(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SiteUsersDTO siteUsersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SiteUsers : {}, {}", id, siteUsersDTO);
        if (siteUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, siteUsersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!siteUsersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SiteUsersDTO result = siteUsersService.update(siteUsersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siteUsersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /site-users/:id} : Partial updates given fields of an existing siteUsers, field will ignore if it is null
     *
     * @param id the id of the siteUsersDTO to save.
     * @param siteUsersDTO the siteUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siteUsersDTO,
     * or with status {@code 400 (Bad Request)} if the siteUsersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the siteUsersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the siteUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/site-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SiteUsersDTO> partialUpdateSiteUsers(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SiteUsersDTO siteUsersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SiteUsers partially : {}, {}", id, siteUsersDTO);
        if (siteUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, siteUsersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!siteUsersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SiteUsersDTO> result = siteUsersService.partialUpdate(siteUsersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siteUsersDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /site-users} : get all the siteUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of siteUsers in body.
     */
    @GetMapping("/site-users")
    public ResponseEntity<List<SiteUsersDTO>> getAllSiteUsers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SiteUsers");
        Page<SiteUsersDTO> page = siteUsersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /site-users/:id} : get the "id" siteUsers.
     *
     * @param id the id of the siteUsersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the siteUsersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/site-users/{id}")
    public ResponseEntity<SiteUsersDTO> getSiteUsers(@PathVariable Long id) {
        log.debug("REST request to get SiteUsers : {}", id);
        Optional<SiteUsersDTO> siteUsersDTO = siteUsersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(siteUsersDTO);
    }

    /**
     * {@code DELETE  /site-users/:id} : delete the "id" siteUsers.
     *
     * @param id the id of the siteUsersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/site-users/{id}")
    public ResponseEntity<Void> deleteSiteUsers(@PathVariable Long id) {
        log.debug("REST request to delete SiteUsers : {}", id);
        siteUsersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
