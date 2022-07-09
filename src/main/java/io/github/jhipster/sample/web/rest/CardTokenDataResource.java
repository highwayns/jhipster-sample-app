package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.CardTokenData;
import io.github.jhipster.sample.repository.CardTokenDataRepository;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.CardTokenData}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CardTokenDataResource {

    private final Logger log = LoggerFactory.getLogger(CardTokenDataResource.class);

    private static final String ENTITY_NAME = "cardTokenData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CardTokenDataRepository cardTokenDataRepository;

    public CardTokenDataResource(CardTokenDataRepository cardTokenDataRepository) {
        this.cardTokenDataRepository = cardTokenDataRepository;
    }

    /**
     * {@code POST  /card-token-data} : Create a new cardTokenData.
     *
     * @param cardTokenData the cardTokenData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cardTokenData, or with status {@code 400 (Bad Request)} if the cardTokenData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/card-token-data")
    public ResponseEntity<CardTokenData> createCardTokenData(@RequestBody CardTokenData cardTokenData) throws URISyntaxException {
        log.debug("REST request to save CardTokenData : {}", cardTokenData);
        if (cardTokenData.getId() != null) {
            throw new BadRequestAlertException("A new cardTokenData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CardTokenData result = cardTokenDataRepository.save(cardTokenData);
        return ResponseEntity
            .created(new URI("/api/card-token-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /card-token-data/:id} : Updates an existing cardTokenData.
     *
     * @param id the id of the cardTokenData to save.
     * @param cardTokenData the cardTokenData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cardTokenData,
     * or with status {@code 400 (Bad Request)} if the cardTokenData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cardTokenData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/card-token-data/{id}")
    public ResponseEntity<CardTokenData> updateCardTokenData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CardTokenData cardTokenData
    ) throws URISyntaxException {
        log.debug("REST request to update CardTokenData : {}, {}", id, cardTokenData);
        if (cardTokenData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cardTokenData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cardTokenDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CardTokenData result = cardTokenDataRepository.save(cardTokenData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cardTokenData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /card-token-data/:id} : Partial updates given fields of an existing cardTokenData, field will ignore if it is null
     *
     * @param id the id of the cardTokenData to save.
     * @param cardTokenData the cardTokenData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cardTokenData,
     * or with status {@code 400 (Bad Request)} if the cardTokenData is not valid,
     * or with status {@code 404 (Not Found)} if the cardTokenData is not found,
     * or with status {@code 500 (Internal Server Error)} if the cardTokenData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/card-token-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CardTokenData> partialUpdateCardTokenData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CardTokenData cardTokenData
    ) throws URISyntaxException {
        log.debug("REST request to partial update CardTokenData partially : {}, {}", id, cardTokenData);
        if (cardTokenData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cardTokenData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cardTokenDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CardTokenData> result = cardTokenDataRepository
            .findById(cardTokenData.getId())
            .map(existingCardTokenData -> {
                if (cardTokenData.getToken() != null) {
                    existingCardTokenData.setToken(cardTokenData.getToken());
                }
                if (cardTokenData.getCardExpiryMonth() != null) {
                    existingCardTokenData.setCardExpiryMonth(cardTokenData.getCardExpiryMonth());
                }
                if (cardTokenData.getCardExpiryYear() != null) {
                    existingCardTokenData.setCardExpiryYear(cardTokenData.getCardExpiryYear());
                }
                if (cardTokenData.getIssuerReturnCode() != null) {
                    existingCardTokenData.setIssuerReturnCode(cardTokenData.getIssuerReturnCode());
                }
                if (cardTokenData.getTruncatedCardNumber() != null) {
                    existingCardTokenData.setTruncatedCardNumber(cardTokenData.getTruncatedCardNumber());
                }

                return existingCardTokenData;
            })
            .map(cardTokenDataRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cardTokenData.getId().toString())
        );
    }

    /**
     * {@code GET  /card-token-data} : get all the cardTokenData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cardTokenData in body.
     */
    @GetMapping("/card-token-data")
    public List<CardTokenData> getAllCardTokenData() {
        log.debug("REST request to get all CardTokenData");
        return cardTokenDataRepository.findAll();
    }

    /**
     * {@code GET  /card-token-data/:id} : get the "id" cardTokenData.
     *
     * @param id the id of the cardTokenData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cardTokenData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/card-token-data/{id}")
    public ResponseEntity<CardTokenData> getCardTokenData(@PathVariable Long id) {
        log.debug("REST request to get CardTokenData : {}", id);
        Optional<CardTokenData> cardTokenData = cardTokenDataRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cardTokenData);
    }

    /**
     * {@code DELETE  /card-token-data/:id} : delete the "id" cardTokenData.
     *
     * @param id the id of the cardTokenData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/card-token-data/{id}")
    public ResponseEntity<Void> deleteCardTokenData(@PathVariable Long id) {
        log.debug("REST request to delete CardTokenData : {}", id);
        cardTokenDataRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
