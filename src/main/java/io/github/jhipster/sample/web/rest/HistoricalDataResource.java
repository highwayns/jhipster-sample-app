package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.HistoricalDataRepository;
import io.github.jhipster.sample.service.HistoricalDataService;
import io.github.jhipster.sample.service.dto.HistoricalDataDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.HistoricalData}.
 */
@RestController
@RequestMapping("/api")
public class HistoricalDataResource {

    private final Logger log = LoggerFactory.getLogger(HistoricalDataResource.class);

    private static final String ENTITY_NAME = "historicalData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HistoricalDataService historicalDataService;

    private final HistoricalDataRepository historicalDataRepository;

    public HistoricalDataResource(HistoricalDataService historicalDataService, HistoricalDataRepository historicalDataRepository) {
        this.historicalDataService = historicalDataService;
        this.historicalDataRepository = historicalDataRepository;
    }

    /**
     * {@code POST  /historical-data} : Create a new historicalData.
     *
     * @param historicalDataDTO the historicalDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historicalDataDTO, or with status {@code 400 (Bad Request)} if the historicalData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/historical-data")
    public ResponseEntity<HistoricalDataDTO> createHistoricalData(@Valid @RequestBody HistoricalDataDTO historicalDataDTO)
        throws URISyntaxException {
        log.debug("REST request to save HistoricalData : {}", historicalDataDTO);
        if (historicalDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new historicalData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HistoricalDataDTO result = historicalDataService.save(historicalDataDTO);
        return ResponseEntity
            .created(new URI("/api/historical-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /historical-data/:id} : Updates an existing historicalData.
     *
     * @param id the id of the historicalDataDTO to save.
     * @param historicalDataDTO the historicalDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historicalDataDTO,
     * or with status {@code 400 (Bad Request)} if the historicalDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historicalDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/historical-data/{id}")
    public ResponseEntity<HistoricalDataDTO> updateHistoricalData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HistoricalDataDTO historicalDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HistoricalData : {}, {}", id, historicalDataDTO);
        if (historicalDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historicalDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historicalDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HistoricalDataDTO result = historicalDataService.update(historicalDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historicalDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /historical-data/:id} : Partial updates given fields of an existing historicalData, field will ignore if it is null
     *
     * @param id the id of the historicalDataDTO to save.
     * @param historicalDataDTO the historicalDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historicalDataDTO,
     * or with status {@code 400 (Bad Request)} if the historicalDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the historicalDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the historicalDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/historical-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HistoricalDataDTO> partialUpdateHistoricalData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HistoricalDataDTO historicalDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HistoricalData partially : {}, {}", id, historicalDataDTO);
        if (historicalDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historicalDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historicalDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HistoricalDataDTO> result = historicalDataService.partialUpdate(historicalDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historicalDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /historical-data} : get all the historicalData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historicalData in body.
     */
    @GetMapping("/historical-data")
    public ResponseEntity<List<HistoricalDataDTO>> getAllHistoricalData(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of HistoricalData");
        Page<HistoricalDataDTO> page = historicalDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /historical-data/:id} : get the "id" historicalData.
     *
     * @param id the id of the historicalDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historicalDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/historical-data/{id}")
    public ResponseEntity<HistoricalDataDTO> getHistoricalData(@PathVariable Long id) {
        log.debug("REST request to get HistoricalData : {}", id);
        Optional<HistoricalDataDTO> historicalDataDTO = historicalDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(historicalDataDTO);
    }

    /**
     * {@code DELETE  /historical-data/:id} : delete the "id" historicalData.
     *
     * @param id the id of the historicalDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/historical-data/{id}")
    public ResponseEntity<Void> deleteHistoricalData(@PathVariable Long id) {
        log.debug("REST request to delete HistoricalData : {}", id);
        historicalDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
