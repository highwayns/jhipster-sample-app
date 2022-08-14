package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.repository.LangRepository;
import io.github.jhipster.sample.service.LangService;
import io.github.jhipster.sample.service.dto.LangDTO;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.Lang}.
 */
@RestController
@RequestMapping("/api")
public class LangResource {

    private final Logger log = LoggerFactory.getLogger(LangResource.class);

    private static final String ENTITY_NAME = "lang";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LangService langService;

    private final LangRepository langRepository;

    public LangResource(LangService langService, LangRepository langRepository) {
        this.langService = langService;
        this.langRepository = langRepository;
    }

    /**
     * {@code POST  /langs} : Create a new lang.
     *
     * @param langDTO the langDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new langDTO, or with status {@code 400 (Bad Request)} if the lang has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/langs")
    public ResponseEntity<LangDTO> createLang(@Valid @RequestBody LangDTO langDTO) throws URISyntaxException {
        log.debug("REST request to save Lang : {}", langDTO);
        if (langDTO.getId() != null) {
            throw new BadRequestAlertException("A new lang cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LangDTO result = langService.save(langDTO);
        return ResponseEntity
            .created(new URI("/api/langs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /langs/:id} : Updates an existing lang.
     *
     * @param id the id of the langDTO to save.
     * @param langDTO the langDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated langDTO,
     * or with status {@code 400 (Bad Request)} if the langDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the langDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/langs/{id}")
    public ResponseEntity<LangDTO> updateLang(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LangDTO langDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Lang : {}, {}", id, langDTO);
        if (langDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, langDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!langRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LangDTO result = langService.update(langDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, langDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /langs/:id} : Partial updates given fields of an existing lang, field will ignore if it is null
     *
     * @param id the id of the langDTO to save.
     * @param langDTO the langDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated langDTO,
     * or with status {@code 400 (Bad Request)} if the langDTO is not valid,
     * or with status {@code 404 (Not Found)} if the langDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the langDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/langs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LangDTO> partialUpdateLang(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LangDTO langDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Lang partially : {}, {}", id, langDTO);
        if (langDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, langDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!langRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LangDTO> result = langService.partialUpdate(langDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, langDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /langs} : get all the langs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of langs in body.
     */
    @GetMapping("/langs")
    public ResponseEntity<List<LangDTO>> getAllLangs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Langs");
        Page<LangDTO> page = langService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /langs/:id} : get the "id" lang.
     *
     * @param id the id of the langDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the langDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/langs/{id}")
    public ResponseEntity<LangDTO> getLang(@PathVariable Long id) {
        log.debug("REST request to get Lang : {}", id);
        Optional<LangDTO> langDTO = langService.findOne(id);
        return ResponseUtil.wrapOrNotFound(langDTO);
    }

    /**
     * {@code DELETE  /langs/:id} : delete the "id" lang.
     *
     * @param id the id of the langDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/langs/{id}")
    public ResponseEntity<Void> deleteLang(@PathVariable Long id) {
        log.debug("REST request to delete Lang : {}", id);
        langService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
