package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.CurrentStatsRepository;
import io.github.jhipster.sample.service.CurrentStatsService;
import io.github.jhipster.sample.service.dto.CurrentStatsDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.CurrentStats}.
 */
@RestController
@RequestMapping("/api")
public class CurrentStatsResource {

    private final Logger log = LoggerFactory.getLogger(CurrentStatsResource.class);

    private static final String ENTITY_NAME = "currentStats";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CurrentStatsService currentStatsService;

    private final CurrentStatsRepository currentStatsRepository;

    public CurrentStatsResource(CurrentStatsService currentStatsService, CurrentStatsRepository currentStatsRepository) {
        this.currentStatsService = currentStatsService;
        this.currentStatsRepository = currentStatsRepository;
    }

    /**
     * {@code POST  /current-stats} : Create a new currentStats.
     *
     * @param currentStatsDTO the currentStatsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new currentStatsDTO, or with status {@code 400 (Bad Request)} if the currentStats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/current-stats")
    public ResponseEntity<CurrentStatsDTO> createCurrentStats(@Valid @RequestBody CurrentStatsDTO currentStatsDTO)
        throws URISyntaxException {
        log.debug("REST request to save CurrentStats : {}", currentStatsDTO);
        if (currentStatsDTO.getId() != null) {
            throw new BadRequestAlertException("A new currentStats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CurrentStatsDTO result = currentStatsService.save(currentStatsDTO);
        return ResponseEntity
            .created(new URI("/api/current-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /current-stats/:id} : Updates an existing currentStats.
     *
     * @param id the id of the currentStatsDTO to save.
     * @param currentStatsDTO the currentStatsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currentStatsDTO,
     * or with status {@code 400 (Bad Request)} if the currentStatsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the currentStatsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/current-stats/{id}")
    public ResponseEntity<CurrentStatsDTO> updateCurrentStats(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CurrentStatsDTO currentStatsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CurrentStats : {}, {}", id, currentStatsDTO);
        if (currentStatsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, currentStatsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!currentStatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CurrentStatsDTO result = currentStatsService.update(currentStatsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, currentStatsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /current-stats/:id} : Partial updates given fields of an existing currentStats, field will ignore if it is null
     *
     * @param id the id of the currentStatsDTO to save.
     * @param currentStatsDTO the currentStatsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currentStatsDTO,
     * or with status {@code 400 (Bad Request)} if the currentStatsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the currentStatsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the currentStatsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/current-stats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CurrentStatsDTO> partialUpdateCurrentStats(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CurrentStatsDTO currentStatsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CurrentStats partially : {}, {}", id, currentStatsDTO);
        if (currentStatsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, currentStatsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!currentStatsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CurrentStatsDTO> result = currentStatsService.partialUpdate(currentStatsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, currentStatsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /current-stats} : get all the currentStats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of currentStats in body.
     */
    @GetMapping("/current-stats")
    public ResponseEntity<List<CurrentStatsDTO>> getAllCurrentStats(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CurrentStats");
        Page<CurrentStatsDTO> page = currentStatsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /current-stats/:id} : get the "id" currentStats.
     *
     * @param id the id of the currentStatsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the currentStatsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/current-stats/{id}")
    public ResponseEntity<CurrentStatsDTO> getCurrentStats(@PathVariable Long id) {
        log.debug("REST request to get CurrentStats : {}", id);
        Optional<CurrentStatsDTO> currentStatsDTO = currentStatsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(currentStatsDTO);
    }

    /**
     * {@code DELETE  /current-stats/:id} : delete the "id" currentStats.
     *
     * @param id the id of the currentStatsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/current-stats/{id}")
    public ResponseEntity<Void> deleteCurrentStats(@PathVariable Long id) {
        log.debug("REST request to delete CurrentStats : {}", id);
        currentStatsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
