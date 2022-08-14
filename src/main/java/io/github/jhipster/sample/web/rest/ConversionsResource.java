package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.ConversionsRepository;
import io.github.jhipster.sample.service.ConversionsService;
import io.github.jhipster.sample.service.dto.ConversionsDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.Conversions}.
 */
@RestController
@RequestMapping("/api")
public class ConversionsResource {

    private final Logger log = LoggerFactory.getLogger(ConversionsResource.class);

    private static final String ENTITY_NAME = "conversions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConversionsService conversionsService;

    private final ConversionsRepository conversionsRepository;

    public ConversionsResource(ConversionsService conversionsService, ConversionsRepository conversionsRepository) {
        this.conversionsService = conversionsService;
        this.conversionsRepository = conversionsRepository;
    }

    /**
     * {@code POST  /conversions} : Create a new conversions.
     *
     * @param conversionsDTO the conversionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conversionsDTO, or with status {@code 400 (Bad Request)} if the conversions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conversions")
    public ResponseEntity<ConversionsDTO> createConversions(@Valid @RequestBody ConversionsDTO conversionsDTO) throws URISyntaxException {
        log.debug("REST request to save Conversions : {}", conversionsDTO);
        if (conversionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new conversions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConversionsDTO result = conversionsService.save(conversionsDTO);
        return ResponseEntity
            .created(new URI("/api/conversions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conversions/:id} : Updates an existing conversions.
     *
     * @param id the id of the conversionsDTO to save.
     * @param conversionsDTO the conversionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conversionsDTO,
     * or with status {@code 400 (Bad Request)} if the conversionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conversionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conversions/{id}")
    public ResponseEntity<ConversionsDTO> updateConversions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ConversionsDTO conversionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Conversions : {}, {}", id, conversionsDTO);
        if (conversionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conversionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conversionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConversionsDTO result = conversionsService.update(conversionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conversionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /conversions/:id} : Partial updates given fields of an existing conversions, field will ignore if it is null
     *
     * @param id the id of the conversionsDTO to save.
     * @param conversionsDTO the conversionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conversionsDTO,
     * or with status {@code 400 (Bad Request)} if the conversionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the conversionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the conversionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/conversions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConversionsDTO> partialUpdateConversions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ConversionsDTO conversionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Conversions partially : {}, {}", id, conversionsDTO);
        if (conversionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conversionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conversionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConversionsDTO> result = conversionsService.partialUpdate(conversionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conversionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /conversions} : get all the conversions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conversions in body.
     */
    @GetMapping("/conversions")
    public ResponseEntity<List<ConversionsDTO>> getAllConversions(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Conversions");
        Page<ConversionsDTO> page = conversionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /conversions/:id} : get the "id" conversions.
     *
     * @param id the id of the conversionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conversionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conversions/{id}")
    public ResponseEntity<ConversionsDTO> getConversions(@PathVariable Long id) {
        log.debug("REST request to get Conversions : {}", id);
        Optional<ConversionsDTO> conversionsDTO = conversionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conversionsDTO);
    }

    /**
     * {@code DELETE  /conversions/:id} : delete the "id" conversions.
     *
     * @param id the id of the conversionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conversions/{id}")
    public ResponseEntity<Void> deleteConversions(@PathVariable Long id) {
        log.debug("REST request to delete Conversions : {}", id);
        conversionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
