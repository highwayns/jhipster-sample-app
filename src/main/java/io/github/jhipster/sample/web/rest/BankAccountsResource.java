package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.BankAccountsRepository;
import io.github.jhipster.sample.service.BankAccountsService;
import io.github.jhipster.sample.service.dto.BankAccountsDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.BankAccounts}.
 */
@RestController
@RequestMapping("/api")
public class BankAccountsResource {

    private final Logger log = LoggerFactory.getLogger(BankAccountsResource.class);

    private static final String ENTITY_NAME = "bankAccounts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankAccountsService bankAccountsService;

    private final BankAccountsRepository bankAccountsRepository;

    public BankAccountsResource(BankAccountsService bankAccountsService, BankAccountsRepository bankAccountsRepository) {
        this.bankAccountsService = bankAccountsService;
        this.bankAccountsRepository = bankAccountsRepository;
    }

    /**
     * {@code POST  /bank-accounts} : Create a new bankAccounts.
     *
     * @param bankAccountsDTO the bankAccountsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankAccountsDTO, or with status {@code 400 (Bad Request)} if the bankAccounts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-accounts")
    public ResponseEntity<BankAccountsDTO> createBankAccounts(@Valid @RequestBody BankAccountsDTO bankAccountsDTO)
        throws URISyntaxException {
        log.debug("REST request to save BankAccounts : {}", bankAccountsDTO);
        if (bankAccountsDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankAccounts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankAccountsDTO result = bankAccountsService.save(bankAccountsDTO);
        return ResponseEntity
            .created(new URI("/api/bank-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-accounts/:id} : Updates an existing bankAccounts.
     *
     * @param id the id of the bankAccountsDTO to save.
     * @param bankAccountsDTO the bankAccountsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankAccountsDTO,
     * or with status {@code 400 (Bad Request)} if the bankAccountsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankAccountsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-accounts/{id}")
    public ResponseEntity<BankAccountsDTO> updateBankAccounts(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BankAccountsDTO bankAccountsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankAccounts : {}, {}", id, bankAccountsDTO);
        if (bankAccountsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankAccountsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankAccountsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankAccountsDTO result = bankAccountsService.update(bankAccountsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankAccountsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-accounts/:id} : Partial updates given fields of an existing bankAccounts, field will ignore if it is null
     *
     * @param id the id of the bankAccountsDTO to save.
     * @param bankAccountsDTO the bankAccountsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankAccountsDTO,
     * or with status {@code 400 (Bad Request)} if the bankAccountsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankAccountsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankAccountsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-accounts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankAccountsDTO> partialUpdateBankAccounts(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BankAccountsDTO bankAccountsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankAccounts partially : {}, {}", id, bankAccountsDTO);
        if (bankAccountsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankAccountsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankAccountsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankAccountsDTO> result = bankAccountsService.partialUpdate(bankAccountsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankAccountsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-accounts} : get all the bankAccounts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankAccounts in body.
     */
    @GetMapping("/bank-accounts")
    public ResponseEntity<List<BankAccountsDTO>> getAllBankAccounts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BankAccounts");
        Page<BankAccountsDTO> page = bankAccountsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bank-accounts/:id} : get the "id" bankAccounts.
     *
     * @param id the id of the bankAccountsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankAccountsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-accounts/{id}")
    public ResponseEntity<BankAccountsDTO> getBankAccounts(@PathVariable Long id) {
        log.debug("REST request to get BankAccounts : {}", id);
        Optional<BankAccountsDTO> bankAccountsDTO = bankAccountsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankAccountsDTO);
    }

    /**
     * {@code DELETE  /bank-accounts/:id} : delete the "id" bankAccounts.
     *
     * @param id the id of the bankAccountsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-accounts/{id}")
    public ResponseEntity<Void> deleteBankAccounts(@PathVariable Long id) {
        log.debug("REST request to delete BankAccounts : {}", id);
        bankAccountsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
