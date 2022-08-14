package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.IsoCountriesRepository;
import io.github.jhipster.sample.service.IsoCountriesService;
import io.github.jhipster.sample.service.dto.IsoCountriesDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.IsoCountries}.
 */
@RestController
@RequestMapping("/api")
public class IsoCountriesResource {

    private final Logger log = LoggerFactory.getLogger(IsoCountriesResource.class);

    private static final String ENTITY_NAME = "isoCountries";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IsoCountriesService isoCountriesService;

    private final IsoCountriesRepository isoCountriesRepository;

    public IsoCountriesResource(IsoCountriesService isoCountriesService, IsoCountriesRepository isoCountriesRepository) {
        this.isoCountriesService = isoCountriesService;
        this.isoCountriesRepository = isoCountriesRepository;
    }

    /**
     * {@code POST  /iso-countries} : Create a new isoCountries.
     *
     * @param isoCountriesDTO the isoCountriesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new isoCountriesDTO, or with status {@code 400 (Bad Request)} if the isoCountries has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/iso-countries")
    public ResponseEntity<IsoCountriesDTO> createIsoCountries(@Valid @RequestBody IsoCountriesDTO isoCountriesDTO)
        throws URISyntaxException {
        log.debug("REST request to save IsoCountries : {}", isoCountriesDTO);
        if (isoCountriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new isoCountries cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IsoCountriesDTO result = isoCountriesService.save(isoCountriesDTO);
        return ResponseEntity
            .created(new URI("/api/iso-countries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /iso-countries/:id} : Updates an existing isoCountries.
     *
     * @param id the id of the isoCountriesDTO to save.
     * @param isoCountriesDTO the isoCountriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated isoCountriesDTO,
     * or with status {@code 400 (Bad Request)} if the isoCountriesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the isoCountriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/iso-countries/{id}")
    public ResponseEntity<IsoCountriesDTO> updateIsoCountries(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IsoCountriesDTO isoCountriesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update IsoCountries : {}, {}", id, isoCountriesDTO);
        if (isoCountriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, isoCountriesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!isoCountriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IsoCountriesDTO result = isoCountriesService.update(isoCountriesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, isoCountriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /iso-countries/:id} : Partial updates given fields of an existing isoCountries, field will ignore if it is null
     *
     * @param id the id of the isoCountriesDTO to save.
     * @param isoCountriesDTO the isoCountriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated isoCountriesDTO,
     * or with status {@code 400 (Bad Request)} if the isoCountriesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the isoCountriesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the isoCountriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/iso-countries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IsoCountriesDTO> partialUpdateIsoCountries(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IsoCountriesDTO isoCountriesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update IsoCountries partially : {}, {}", id, isoCountriesDTO);
        if (isoCountriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, isoCountriesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!isoCountriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IsoCountriesDTO> result = isoCountriesService.partialUpdate(isoCountriesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, isoCountriesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /iso-countries} : get all the isoCountries.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of isoCountries in body.
     */
    @GetMapping("/iso-countries")
    public ResponseEntity<List<IsoCountriesDTO>> getAllIsoCountries(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of IsoCountries");
        Page<IsoCountriesDTO> page = isoCountriesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /iso-countries/:id} : get the "id" isoCountries.
     *
     * @param id the id of the isoCountriesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the isoCountriesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/iso-countries/{id}")
    public ResponseEntity<IsoCountriesDTO> getIsoCountries(@PathVariable Long id) {
        log.debug("REST request to get IsoCountries : {}", id);
        Optional<IsoCountriesDTO> isoCountriesDTO = isoCountriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(isoCountriesDTO);
    }

    /**
     * {@code DELETE  /iso-countries/:id} : delete the "id" isoCountries.
     *
     * @param id the id of the isoCountriesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/iso-countries/{id}")
    public ResponseEntity<Void> deleteIsoCountries(@PathVariable Long id) {
        log.debug("REST request to delete IsoCountries : {}", id);
        isoCountriesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
