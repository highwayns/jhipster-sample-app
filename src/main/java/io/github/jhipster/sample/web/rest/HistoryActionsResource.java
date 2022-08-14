package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.HistoryActionsRepository;
import io.github.jhipster.sample.service.HistoryActionsService;
import io.github.jhipster.sample.service.dto.HistoryActionsDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.HistoryActions}.
 */
@RestController
@RequestMapping("/api")
public class HistoryActionsResource {

    private final Logger log = LoggerFactory.getLogger(HistoryActionsResource.class);

    private static final String ENTITY_NAME = "historyActions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HistoryActionsService historyActionsService;

    private final HistoryActionsRepository historyActionsRepository;

    public HistoryActionsResource(HistoryActionsService historyActionsService, HistoryActionsRepository historyActionsRepository) {
        this.historyActionsService = historyActionsService;
        this.historyActionsRepository = historyActionsRepository;
    }

    /**
     * {@code POST  /history-actions} : Create a new historyActions.
     *
     * @param historyActionsDTO the historyActionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historyActionsDTO, or with status {@code 400 (Bad Request)} if the historyActions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/history-actions")
    public ResponseEntity<HistoryActionsDTO> createHistoryActions(@Valid @RequestBody HistoryActionsDTO historyActionsDTO)
        throws URISyntaxException {
        log.debug("REST request to save HistoryActions : {}", historyActionsDTO);
        if (historyActionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new historyActions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HistoryActionsDTO result = historyActionsService.save(historyActionsDTO);
        return ResponseEntity
            .created(new URI("/api/history-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /history-actions/:id} : Updates an existing historyActions.
     *
     * @param id the id of the historyActionsDTO to save.
     * @param historyActionsDTO the historyActionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historyActionsDTO,
     * or with status {@code 400 (Bad Request)} if the historyActionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historyActionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/history-actions/{id}")
    public ResponseEntity<HistoryActionsDTO> updateHistoryActions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HistoryActionsDTO historyActionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HistoryActions : {}, {}", id, historyActionsDTO);
        if (historyActionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historyActionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historyActionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HistoryActionsDTO result = historyActionsService.update(historyActionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historyActionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /history-actions/:id} : Partial updates given fields of an existing historyActions, field will ignore if it is null
     *
     * @param id the id of the historyActionsDTO to save.
     * @param historyActionsDTO the historyActionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historyActionsDTO,
     * or with status {@code 400 (Bad Request)} if the historyActionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the historyActionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the historyActionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/history-actions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HistoryActionsDTO> partialUpdateHistoryActions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HistoryActionsDTO historyActionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HistoryActions partially : {}, {}", id, historyActionsDTO);
        if (historyActionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historyActionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historyActionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HistoryActionsDTO> result = historyActionsService.partialUpdate(historyActionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historyActionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /history-actions} : get all the historyActions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historyActions in body.
     */
    @GetMapping("/history-actions")
    public ResponseEntity<List<HistoryActionsDTO>> getAllHistoryActions(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of HistoryActions");
        Page<HistoryActionsDTO> page = historyActionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /history-actions/:id} : get the "id" historyActions.
     *
     * @param id the id of the historyActionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historyActionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/history-actions/{id}")
    public ResponseEntity<HistoryActionsDTO> getHistoryActions(@PathVariable Long id) {
        log.debug("REST request to get HistoryActions : {}", id);
        Optional<HistoryActionsDTO> historyActionsDTO = historyActionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(historyActionsDTO);
    }

    /**
     * {@code DELETE  /history-actions/:id} : delete the "id" historyActions.
     *
     * @param id the id of the historyActionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/history-actions/{id}")
    public ResponseEntity<Void> deleteHistoryActions(@PathVariable Long id) {
        log.debug("REST request to delete HistoryActions : {}", id);
        historyActionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
