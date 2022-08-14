package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.SettingsFilesRepository;
import io.github.jhipster.sample.service.SettingsFilesService;
import io.github.jhipster.sample.service.dto.SettingsFilesDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.SettingsFiles}.
 */
@RestController
@RequestMapping("/api")
public class SettingsFilesResource {

    private final Logger log = LoggerFactory.getLogger(SettingsFilesResource.class);

    private static final String ENTITY_NAME = "settingsFiles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SettingsFilesService settingsFilesService;

    private final SettingsFilesRepository settingsFilesRepository;

    public SettingsFilesResource(SettingsFilesService settingsFilesService, SettingsFilesRepository settingsFilesRepository) {
        this.settingsFilesService = settingsFilesService;
        this.settingsFilesRepository = settingsFilesRepository;
    }

    /**
     * {@code POST  /settings-files} : Create a new settingsFiles.
     *
     * @param settingsFilesDTO the settingsFilesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new settingsFilesDTO, or with status {@code 400 (Bad Request)} if the settingsFiles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/settings-files")
    public ResponseEntity<SettingsFilesDTO> createSettingsFiles(@Valid @RequestBody SettingsFilesDTO settingsFilesDTO)
        throws URISyntaxException {
        log.debug("REST request to save SettingsFiles : {}", settingsFilesDTO);
        if (settingsFilesDTO.getId() != null) {
            throw new BadRequestAlertException("A new settingsFiles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SettingsFilesDTO result = settingsFilesService.save(settingsFilesDTO);
        return ResponseEntity
            .created(new URI("/api/settings-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /settings-files/:id} : Updates an existing settingsFiles.
     *
     * @param id the id of the settingsFilesDTO to save.
     * @param settingsFilesDTO the settingsFilesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated settingsFilesDTO,
     * or with status {@code 400 (Bad Request)} if the settingsFilesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the settingsFilesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/settings-files/{id}")
    public ResponseEntity<SettingsFilesDTO> updateSettingsFiles(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SettingsFilesDTO settingsFilesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SettingsFiles : {}, {}", id, settingsFilesDTO);
        if (settingsFilesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, settingsFilesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!settingsFilesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SettingsFilesDTO result = settingsFilesService.update(settingsFilesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, settingsFilesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /settings-files/:id} : Partial updates given fields of an existing settingsFiles, field will ignore if it is null
     *
     * @param id the id of the settingsFilesDTO to save.
     * @param settingsFilesDTO the settingsFilesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated settingsFilesDTO,
     * or with status {@code 400 (Bad Request)} if the settingsFilesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the settingsFilesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the settingsFilesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/settings-files/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SettingsFilesDTO> partialUpdateSettingsFiles(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SettingsFilesDTO settingsFilesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SettingsFiles partially : {}, {}", id, settingsFilesDTO);
        if (settingsFilesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, settingsFilesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!settingsFilesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SettingsFilesDTO> result = settingsFilesService.partialUpdate(settingsFilesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, settingsFilesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /settings-files} : get all the settingsFiles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of settingsFiles in body.
     */
    @GetMapping("/settings-files")
    public ResponseEntity<List<SettingsFilesDTO>> getAllSettingsFiles(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SettingsFiles");
        Page<SettingsFilesDTO> page = settingsFilesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /settings-files/:id} : get the "id" settingsFiles.
     *
     * @param id the id of the settingsFilesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the settingsFilesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/settings-files/{id}")
    public ResponseEntity<SettingsFilesDTO> getSettingsFiles(@PathVariable Long id) {
        log.debug("REST request to get SettingsFiles : {}", id);
        Optional<SettingsFilesDTO> settingsFilesDTO = settingsFilesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(settingsFilesDTO);
    }

    /**
     * {@code DELETE  /settings-files/:id} : delete the "id" settingsFiles.
     *
     * @param id the id of the settingsFilesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/settings-files/{id}")
    public ResponseEntity<Void> deleteSettingsFiles(@PathVariable Long id) {
        log.debug("REST request to delete SettingsFiles : {}", id);
        settingsFilesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
