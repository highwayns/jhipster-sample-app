package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.Links;
import io.github.jhipster.sample.repository.LinksRepository;
import io.github.jhipster.sample.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link io.github.jhipster.sample.domain.Links}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LinksResource {

    private final Logger log = LoggerFactory.getLogger(LinksResource.class);

    private static final String ENTITY_NAME = "links";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LinksRepository linksRepository;

    public LinksResource(LinksRepository linksRepository) {
        this.linksRepository = linksRepository;
    }

    /**
     * {@code POST  /links} : Create a new links.
     *
     * @param links the links to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new links, or with status {@code 400 (Bad Request)} if the links has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/links")
    public ResponseEntity<Links> createLinks(@RequestBody Links links) throws URISyntaxException {
        log.debug("REST request to save Links : {}", links);
        if (links.getId() != null) {
            throw new BadRequestAlertException("A new links cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Links result = linksRepository.save(links);
        return ResponseEntity
            .created(new URI("/api/links/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /links/:id} : Updates an existing links.
     *
     * @param id the id of the links to save.
     * @param links the links to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated links,
     * or with status {@code 400 (Bad Request)} if the links is not valid,
     * or with status {@code 500 (Internal Server Error)} if the links couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/links/{id}")
    public ResponseEntity<Links> updateLinks(@PathVariable(value = "id", required = false) final Long id, @RequestBody Links links)
        throws URISyntaxException {
        log.debug("REST request to update Links : {}, {}", id, links);
        if (links.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, links.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!linksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Links result = linksRepository.save(links);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, links.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /links/:id} : Partial updates given fields of an existing links, field will ignore if it is null
     *
     * @param id the id of the links to save.
     * @param links the links to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated links,
     * or with status {@code 400 (Bad Request)} if the links is not valid,
     * or with status {@code 404 (Not Found)} if the links is not found,
     * or with status {@code 500 (Internal Server Error)} if the links couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/links/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Links> partialUpdateLinks(@PathVariable(value = "id", required = false) final Long id, @RequestBody Links links)
        throws URISyntaxException {
        log.debug("REST request to partial update Links partially : {}, {}", id, links);
        if (links.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, links.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!linksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Links> result = linksRepository
            .findById(links.getId())
            .map(existingLinks -> {
                return existingLinks;
            })
            .map(linksRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, links.getId().toString())
        );
    }

    /**
     * {@code GET  /links} : get all the links.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of links in body.
     */
    @GetMapping("/links")
    public List<Links> getAllLinks() {
        log.debug("REST request to get all Links");
        return linksRepository.findAll();
    }

    /**
     * {@code GET  /links/:id} : get the "id" links.
     *
     * @param id the id of the links to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the links, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/links/{id}")
    public ResponseEntity<Links> getLinks(@PathVariable Long id) {
        log.debug("REST request to get Links : {}", id);
        Optional<Links> links = linksRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(links);
    }

    /**
     * {@code DELETE  /links/:id} : delete the "id" links.
     *
     * @param id the id of the links to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/links/{id}")
    public ResponseEntity<Void> deleteLinks(@PathVariable Long id) {
        log.debug("REST request to delete Links : {}", id);
        linksRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
