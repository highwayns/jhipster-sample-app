package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.FeesRepository;
import io.github.jhipster.sample.service.FeesService;
import io.github.jhipster.sample.service.dto.FeesDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.Fees}.
 */
@RestController
@RequestMapping("/api")
public class FeesResource {

    private final Logger log = LoggerFactory.getLogger(FeesResource.class);

    private static final String ENTITY_NAME = "fees";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeesService feesService;

    private final FeesRepository feesRepository;

    public FeesResource(FeesService feesService, FeesRepository feesRepository) {
        this.feesService = feesService;
        this.feesRepository = feesRepository;
    }

    /**
     * {@code POST  /fees} : Create a new fees.
     *
     * @param feesDTO the feesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feesDTO, or with status {@code 400 (Bad Request)} if the fees has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fees")
    public ResponseEntity<FeesDTO> createFees(@Valid @RequestBody FeesDTO feesDTO) throws URISyntaxException {
        log.debug("REST request to save Fees : {}", feesDTO);
        if (feesDTO.getId() != null) {
            throw new BadRequestAlertException("A new fees cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeesDTO result = feesService.save(feesDTO);
        return ResponseEntity
            .created(new URI("/api/fees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fees/:id} : Updates an existing fees.
     *
     * @param id the id of the feesDTO to save.
     * @param feesDTO the feesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feesDTO,
     * or with status {@code 400 (Bad Request)} if the feesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fees/{id}")
    public ResponseEntity<FeesDTO> updateFees(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FeesDTO feesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Fees : {}, {}", id, feesDTO);
        if (feesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FeesDTO result = feesService.update(feesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fees/:id} : Partial updates given fields of an existing fees, field will ignore if it is null
     *
     * @param id the id of the feesDTO to save.
     * @param feesDTO the feesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feesDTO,
     * or with status {@code 400 (Bad Request)} if the feesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the feesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the feesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fees/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FeesDTO> partialUpdateFees(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FeesDTO feesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Fees partially : {}, {}", id, feesDTO);
        if (feesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FeesDTO> result = feesService.partialUpdate(feesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fees} : get all the fees.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fees in body.
     */
    @GetMapping("/fees")
    public ResponseEntity<List<FeesDTO>> getAllFees(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Fees");
        Page<FeesDTO> page = feesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fees/:id} : get the "id" fees.
     *
     * @param id the id of the feesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fees/{id}")
    public ResponseEntity<FeesDTO> getFees(@PathVariable Long id) {
        log.debug("REST request to get Fees : {}", id);
        Optional<FeesDTO> feesDTO = feesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feesDTO);
    }

    /**
     * {@code DELETE  /fees/:id} : delete the "id" fees.
     *
     * @param id the id of the feesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fees/{id}")
    public ResponseEntity<Void> deleteFees(@PathVariable Long id) {
        log.debug("REST request to delete Fees : {}", id);
        feesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
