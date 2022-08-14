package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.ContentFilesRepository;
import io.github.jhipster.sample.service.ContentFilesService;
import io.github.jhipster.sample.service.dto.ContentFilesDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.ContentFiles}.
 */
@RestController
@RequestMapping("/api")
public class ContentFilesResource {

    private final Logger log = LoggerFactory.getLogger(ContentFilesResource.class);

    private static final String ENTITY_NAME = "contentFiles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContentFilesService contentFilesService;

    private final ContentFilesRepository contentFilesRepository;

    public ContentFilesResource(ContentFilesService contentFilesService, ContentFilesRepository contentFilesRepository) {
        this.contentFilesService = contentFilesService;
        this.contentFilesRepository = contentFilesRepository;
    }

    /**
     * {@code POST  /content-files} : Create a new contentFiles.
     *
     * @param contentFilesDTO the contentFilesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contentFilesDTO, or with status {@code 400 (Bad Request)} if the contentFiles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/content-files")
    public ResponseEntity<ContentFilesDTO> createContentFiles(@Valid @RequestBody ContentFilesDTO contentFilesDTO)
        throws URISyntaxException {
        log.debug("REST request to save ContentFiles : {}", contentFilesDTO);
        if (contentFilesDTO.getId() != null) {
            throw new BadRequestAlertException("A new contentFiles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContentFilesDTO result = contentFilesService.save(contentFilesDTO);
        return ResponseEntity
            .created(new URI("/api/content-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /content-files/:id} : Updates an existing contentFiles.
     *
     * @param id the id of the contentFilesDTO to save.
     * @param contentFilesDTO the contentFilesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contentFilesDTO,
     * or with status {@code 400 (Bad Request)} if the contentFilesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contentFilesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/content-files/{id}")
    public ResponseEntity<ContentFilesDTO> updateContentFiles(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContentFilesDTO contentFilesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ContentFiles : {}, {}", id, contentFilesDTO);
        if (contentFilesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contentFilesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contentFilesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContentFilesDTO result = contentFilesService.update(contentFilesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contentFilesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /content-files/:id} : Partial updates given fields of an existing contentFiles, field will ignore if it is null
     *
     * @param id the id of the contentFilesDTO to save.
     * @param contentFilesDTO the contentFilesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contentFilesDTO,
     * or with status {@code 400 (Bad Request)} if the contentFilesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contentFilesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contentFilesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/content-files/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContentFilesDTO> partialUpdateContentFiles(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContentFilesDTO contentFilesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContentFiles partially : {}, {}", id, contentFilesDTO);
        if (contentFilesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contentFilesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contentFilesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContentFilesDTO> result = contentFilesService.partialUpdate(contentFilesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contentFilesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /content-files} : get all the contentFiles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contentFiles in body.
     */
    @GetMapping("/content-files")
    public ResponseEntity<List<ContentFilesDTO>> getAllContentFiles(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ContentFiles");
        Page<ContentFilesDTO> page = contentFilesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /content-files/:id} : get the "id" contentFiles.
     *
     * @param id the id of the contentFilesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contentFilesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/content-files/{id}")
    public ResponseEntity<ContentFilesDTO> getContentFiles(@PathVariable Long id) {
        log.debug("REST request to get ContentFiles : {}", id);
        Optional<ContentFilesDTO> contentFilesDTO = contentFilesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contentFilesDTO);
    }

    /**
     * {@code DELETE  /content-files/:id} : delete the "id" contentFiles.
     *
     * @param id the id of the contentFilesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/content-files/{id}")
    public ResponseEntity<Void> deleteContentFiles(@PathVariable Long id) {
        log.debug("REST request to delete ContentFiles : {}", id);
        contentFilesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
