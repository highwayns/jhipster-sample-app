package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.BitcoinAddressesRepository;
import io.github.jhipster.sample.service.BitcoinAddressesService;
import io.github.jhipster.sample.service.dto.BitcoinAddressesDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.BitcoinAddresses}.
 */
@RestController
@RequestMapping("/api")
public class BitcoinAddressesResource {

    private final Logger log = LoggerFactory.getLogger(BitcoinAddressesResource.class);

    private static final String ENTITY_NAME = "bitcoinAddresses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BitcoinAddressesService bitcoinAddressesService;

    private final BitcoinAddressesRepository bitcoinAddressesRepository;

    public BitcoinAddressesResource(
        BitcoinAddressesService bitcoinAddressesService,
        BitcoinAddressesRepository bitcoinAddressesRepository
    ) {
        this.bitcoinAddressesService = bitcoinAddressesService;
        this.bitcoinAddressesRepository = bitcoinAddressesRepository;
    }

    /**
     * {@code POST  /bitcoin-addresses} : Create a new bitcoinAddresses.
     *
     * @param bitcoinAddressesDTO the bitcoinAddressesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bitcoinAddressesDTO, or with status {@code 400 (Bad Request)} if the bitcoinAddresses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bitcoin-addresses")
    public ResponseEntity<BitcoinAddressesDTO> createBitcoinAddresses(@Valid @RequestBody BitcoinAddressesDTO bitcoinAddressesDTO)
        throws URISyntaxException {
        log.debug("REST request to save BitcoinAddresses : {}", bitcoinAddressesDTO);
        if (bitcoinAddressesDTO.getId() != null) {
            throw new BadRequestAlertException("A new bitcoinAddresses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BitcoinAddressesDTO result = bitcoinAddressesService.save(bitcoinAddressesDTO);
        return ResponseEntity
            .created(new URI("/api/bitcoin-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bitcoin-addresses/:id} : Updates an existing bitcoinAddresses.
     *
     * @param id the id of the bitcoinAddressesDTO to save.
     * @param bitcoinAddressesDTO the bitcoinAddressesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bitcoinAddressesDTO,
     * or with status {@code 400 (Bad Request)} if the bitcoinAddressesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bitcoinAddressesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bitcoin-addresses/{id}")
    public ResponseEntity<BitcoinAddressesDTO> updateBitcoinAddresses(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BitcoinAddressesDTO bitcoinAddressesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BitcoinAddresses : {}, {}", id, bitcoinAddressesDTO);
        if (bitcoinAddressesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bitcoinAddressesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bitcoinAddressesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BitcoinAddressesDTO result = bitcoinAddressesService.update(bitcoinAddressesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bitcoinAddressesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bitcoin-addresses/:id} : Partial updates given fields of an existing bitcoinAddresses, field will ignore if it is null
     *
     * @param id the id of the bitcoinAddressesDTO to save.
     * @param bitcoinAddressesDTO the bitcoinAddressesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bitcoinAddressesDTO,
     * or with status {@code 400 (Bad Request)} if the bitcoinAddressesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bitcoinAddressesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bitcoinAddressesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bitcoin-addresses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BitcoinAddressesDTO> partialUpdateBitcoinAddresses(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BitcoinAddressesDTO bitcoinAddressesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BitcoinAddresses partially : {}, {}", id, bitcoinAddressesDTO);
        if (bitcoinAddressesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bitcoinAddressesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bitcoinAddressesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BitcoinAddressesDTO> result = bitcoinAddressesService.partialUpdate(bitcoinAddressesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bitcoinAddressesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bitcoin-addresses} : get all the bitcoinAddresses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bitcoinAddresses in body.
     */
    @GetMapping("/bitcoin-addresses")
    public ResponseEntity<List<BitcoinAddressesDTO>> getAllBitcoinAddresses(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of BitcoinAddresses");
        Page<BitcoinAddressesDTO> page = bitcoinAddressesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bitcoin-addresses/:id} : get the "id" bitcoinAddresses.
     *
     * @param id the id of the bitcoinAddressesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bitcoinAddressesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bitcoin-addresses/{id}")
    public ResponseEntity<BitcoinAddressesDTO> getBitcoinAddresses(@PathVariable Long id) {
        log.debug("REST request to get BitcoinAddresses : {}", id);
        Optional<BitcoinAddressesDTO> bitcoinAddressesDTO = bitcoinAddressesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bitcoinAddressesDTO);
    }

    /**
     * {@code DELETE  /bitcoin-addresses/:id} : delete the "id" bitcoinAddresses.
     *
     * @param id the id of the bitcoinAddressesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bitcoin-addresses/{id}")
    public ResponseEntity<Void> deleteBitcoinAddresses(@PathVariable Long id) {
        log.debug("REST request to delete BitcoinAddresses : {}", id);
        bitcoinAddressesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
