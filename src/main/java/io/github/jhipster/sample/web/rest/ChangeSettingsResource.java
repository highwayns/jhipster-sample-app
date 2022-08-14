package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.ChangeSettingsRepository;
import io.github.jhipster.sample.service.ChangeSettingsService;
import io.github.jhipster.sample.service.dto.ChangeSettingsDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.ChangeSettings}.
 */
@RestController
@RequestMapping("/api")
public class ChangeSettingsResource {

    private final Logger log = LoggerFactory.getLogger(ChangeSettingsResource.class);

    private static final String ENTITY_NAME = "changeSettings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChangeSettingsService changeSettingsService;

    private final ChangeSettingsRepository changeSettingsRepository;

    public ChangeSettingsResource(ChangeSettingsService changeSettingsService, ChangeSettingsRepository changeSettingsRepository) {
        this.changeSettingsService = changeSettingsService;
        this.changeSettingsRepository = changeSettingsRepository;
    }

    /**
     * {@code POST  /change-settings} : Create a new changeSettings.
     *
     * @param changeSettingsDTO the changeSettingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new changeSettingsDTO, or with status {@code 400 (Bad Request)} if the changeSettings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/change-settings")
    public ResponseEntity<ChangeSettingsDTO> createChangeSettings(@Valid @RequestBody ChangeSettingsDTO changeSettingsDTO)
        throws URISyntaxException {
        log.debug("REST request to save ChangeSettings : {}", changeSettingsDTO);
        if (changeSettingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new changeSettings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChangeSettingsDTO result = changeSettingsService.save(changeSettingsDTO);
        return ResponseEntity
            .created(new URI("/api/change-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /change-settings/:id} : Updates an existing changeSettings.
     *
     * @param id the id of the changeSettingsDTO to save.
     * @param changeSettingsDTO the changeSettingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated changeSettingsDTO,
     * or with status {@code 400 (Bad Request)} if the changeSettingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the changeSettingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/change-settings/{id}")
    public ResponseEntity<ChangeSettingsDTO> updateChangeSettings(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ChangeSettingsDTO changeSettingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ChangeSettings : {}, {}", id, changeSettingsDTO);
        if (changeSettingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, changeSettingsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!changeSettingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChangeSettingsDTO result = changeSettingsService.update(changeSettingsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, changeSettingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /change-settings/:id} : Partial updates given fields of an existing changeSettings, field will ignore if it is null
     *
     * @param id the id of the changeSettingsDTO to save.
     * @param changeSettingsDTO the changeSettingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated changeSettingsDTO,
     * or with status {@code 400 (Bad Request)} if the changeSettingsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the changeSettingsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the changeSettingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/change-settings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChangeSettingsDTO> partialUpdateChangeSettings(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ChangeSettingsDTO changeSettingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChangeSettings partially : {}, {}", id, changeSettingsDTO);
        if (changeSettingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, changeSettingsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!changeSettingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChangeSettingsDTO> result = changeSettingsService.partialUpdate(changeSettingsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, changeSettingsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /change-settings} : get all the changeSettings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of changeSettings in body.
     */
    @GetMapping("/change-settings")
    public ResponseEntity<List<ChangeSettingsDTO>> getAllChangeSettings(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ChangeSettings");
        Page<ChangeSettingsDTO> page = changeSettingsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /change-settings/:id} : get the "id" changeSettings.
     *
     * @param id the id of the changeSettingsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the changeSettingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/change-settings/{id}")
    public ResponseEntity<ChangeSettingsDTO> getChangeSettings(@PathVariable Long id) {
        log.debug("REST request to get ChangeSettings : {}", id);
        Optional<ChangeSettingsDTO> changeSettingsDTO = changeSettingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(changeSettingsDTO);
    }

    /**
     * {@code DELETE  /change-settings/:id} : delete the "id" changeSettings.
     *
     * @param id the id of the changeSettingsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/change-settings/{id}")
    public ResponseEntity<Void> deleteChangeSettings(@PathVariable Long id) {
        log.debug("REST request to delete ChangeSettings : {}", id);
        changeSettingsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
