package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.TransactionTypesRepository;
import io.github.jhipster.sample.service.TransactionTypesService;
import io.github.jhipster.sample.service.dto.TransactionTypesDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.TransactionTypes}.
 */
@RestController
@RequestMapping("/api")
public class TransactionTypesResource {

    private final Logger log = LoggerFactory.getLogger(TransactionTypesResource.class);

    private static final String ENTITY_NAME = "transactionTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionTypesService transactionTypesService;

    private final TransactionTypesRepository transactionTypesRepository;

    public TransactionTypesResource(
        TransactionTypesService transactionTypesService,
        TransactionTypesRepository transactionTypesRepository
    ) {
        this.transactionTypesService = transactionTypesService;
        this.transactionTypesRepository = transactionTypesRepository;
    }

    /**
     * {@code POST  /transaction-types} : Create a new transactionTypes.
     *
     * @param transactionTypesDTO the transactionTypesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionTypesDTO, or with status {@code 400 (Bad Request)} if the transactionTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-types")
    public ResponseEntity<TransactionTypesDTO> createTransactionTypes(@Valid @RequestBody TransactionTypesDTO transactionTypesDTO)
        throws URISyntaxException {
        log.debug("REST request to save TransactionTypes : {}", transactionTypesDTO);
        if (transactionTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionTypesDTO result = transactionTypesService.save(transactionTypesDTO);
        return ResponseEntity
            .created(new URI("/api/transaction-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-types/:id} : Updates an existing transactionTypes.
     *
     * @param id the id of the transactionTypesDTO to save.
     * @param transactionTypesDTO the transactionTypesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionTypesDTO,
     * or with status {@code 400 (Bad Request)} if the transactionTypesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionTypesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-types/{id}")
    public ResponseEntity<TransactionTypesDTO> updateTransactionTypes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TransactionTypesDTO transactionTypesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TransactionTypes : {}, {}", id, transactionTypesDTO);
        if (transactionTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transactionTypesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transactionTypesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransactionTypesDTO result = transactionTypesService.update(transactionTypesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transaction-types/:id} : Partial updates given fields of an existing transactionTypes, field will ignore if it is null
     *
     * @param id the id of the transactionTypesDTO to save.
     * @param transactionTypesDTO the transactionTypesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionTypesDTO,
     * or with status {@code 400 (Bad Request)} if the transactionTypesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the transactionTypesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the transactionTypesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transaction-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TransactionTypesDTO> partialUpdateTransactionTypes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TransactionTypesDTO transactionTypesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TransactionTypes partially : {}, {}", id, transactionTypesDTO);
        if (transactionTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transactionTypesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transactionTypesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransactionTypesDTO> result = transactionTypesService.partialUpdate(transactionTypesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transactionTypesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /transaction-types} : get all the transactionTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionTypes in body.
     */
    @GetMapping("/transaction-types")
    public ResponseEntity<List<TransactionTypesDTO>> getAllTransactionTypes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of TransactionTypes");
        Page<TransactionTypesDTO> page = transactionTypesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transaction-types/:id} : get the "id" transactionTypes.
     *
     * @param id the id of the transactionTypesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionTypesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-types/{id}")
    public ResponseEntity<TransactionTypesDTO> getTransactionTypes(@PathVariable Long id) {
        log.debug("REST request to get TransactionTypes : {}", id);
        Optional<TransactionTypesDTO> transactionTypesDTO = transactionTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionTypesDTO);
    }

    /**
     * {@code DELETE  /transaction-types/:id} : delete the "id" transactionTypes.
     *
     * @param id the id of the transactionTypesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-types/{id}")
    public ResponseEntity<Void> deleteTransactionTypes(@PathVariable Long id) {
        log.debug("REST request to delete TransactionTypes : {}", id);
        transactionTypesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
