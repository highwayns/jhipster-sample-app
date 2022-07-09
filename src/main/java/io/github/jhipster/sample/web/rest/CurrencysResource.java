package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.Currencys;
import io.github.jhipster.sample.repository.CurrencysRepository;
import io.github.jhipster.sample.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link io.github.jhipster.sample.domain.Currencys}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CurrencysResource {

    private final Logger log = LoggerFactory.getLogger(CurrencysResource.class);

    private static final String ENTITY_NAME = "currencys";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CurrencysRepository currencysRepository;

    public CurrencysResource(CurrencysRepository currencysRepository) {
        this.currencysRepository = currencysRepository;
    }

    /**
     * {@code POST  /currencys} : Create a new currencys.
     *
     * @param currencys the currencys to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new currencys, or with status {@code 400 (Bad Request)} if the currencys has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/currencys")
    public ResponseEntity<Currencys> createCurrencys(@RequestBody Currencys currencys) throws URISyntaxException {
        log.debug("REST request to save Currencys : {}", currencys);
        if (currencys.getId() != null) {
            throw new BadRequestAlertException("A new currencys cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Currencys result = currencysRepository.save(currencys);
        return ResponseEntity
            .created(new URI("/api/currencys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /currencys/:id} : Updates an existing currencys.
     *
     * @param id the id of the currencys to save.
     * @param currencys the currencys to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currencys,
     * or with status {@code 400 (Bad Request)} if the currencys is not valid,
     * or with status {@code 500 (Internal Server Error)} if the currencys couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/currencys/{id}")
    public ResponseEntity<Currencys> updateCurrencys(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Currencys currencys
    ) throws URISyntaxException {
        log.debug("REST request to update Currencys : {}, {}", id, currencys);
        if (currencys.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, currencys.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!currencysRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Currencys result = currencysRepository.save(currencys);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, currencys.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /currencys/:id} : Partial updates given fields of an existing currencys, field will ignore if it is null
     *
     * @param id the id of the currencys to save.
     * @param currencys the currencys to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currencys,
     * or with status {@code 400 (Bad Request)} if the currencys is not valid,
     * or with status {@code 404 (Not Found)} if the currencys is not found,
     * or with status {@code 500 (Internal Server Error)} if the currencys couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/currencys/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Currencys> partialUpdateCurrencys(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Currencys currencys
    ) throws URISyntaxException {
        log.debug("REST request to partial update Currencys partially : {}, {}", id, currencys);
        if (currencys.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, currencys.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!currencysRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Currencys> result = currencysRepository
            .findById(currencys.getId())
            .map(existingCurrencys -> {
                if (currencys.getCurrency() != null) {
                    existingCurrencys.setCurrency(currencys.getCurrency());
                }

                return existingCurrencys;
            })
            .map(currencysRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, currencys.getId().toString())
        );
    }

    /**
     * {@code GET  /currencys} : get all the currencys.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of currencys in body.
     */
    @GetMapping("/currencys")
    public List<Currencys> getAllCurrencys() {
        log.debug("REST request to get all Currencys");
        return currencysRepository.findAll();
    }

    /**
     * {@code GET  /currencys/:id} : get the "id" currencys.
     *
     * @param id the id of the currencys to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the currencys, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/currencys/{id}")
    public ResponseEntity<Currencys> getCurrencys(@PathVariable Long id) {
        log.debug("REST request to get Currencys : {}", id);
        Optional<Currencys> currencys = currencysRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(currencys);
    }

    /**
     * {@code DELETE  /currencys/:id} : delete the "id" currencys.
     *
     * @param id the id of the currencys to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/currencys/{id}")
    public ResponseEntity<Void> deleteCurrencys(@PathVariable Long id) {
        log.debug("REST request to delete Currencys : {}", id);
        currencysRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
