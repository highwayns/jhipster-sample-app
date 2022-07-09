package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.TokenisedCard;
import io.github.jhipster.sample.repository.TokenisedCardRepository;
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
 * REST controller for managing {@link io.github.jhipster.sample.domain.TokenisedCard}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TokenisedCardResource {

    private final Logger log = LoggerFactory.getLogger(TokenisedCardResource.class);

    private static final String ENTITY_NAME = "tokenisedCard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TokenisedCardRepository tokenisedCardRepository;

    public TokenisedCardResource(TokenisedCardRepository tokenisedCardRepository) {
        this.tokenisedCardRepository = tokenisedCardRepository;
    }

    /**
     * {@code POST  /tokenised-cards} : Create a new tokenisedCard.
     *
     * @param tokenisedCard the tokenisedCard to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tokenisedCard, or with status {@code 400 (Bad Request)} if the tokenisedCard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tokenised-cards")
    public ResponseEntity<TokenisedCard> createTokenisedCard(@RequestBody TokenisedCard tokenisedCard) throws URISyntaxException {
        log.debug("REST request to save TokenisedCard : {}", tokenisedCard);
        if (tokenisedCard.getId() != null) {
            throw new BadRequestAlertException("A new tokenisedCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TokenisedCard result = tokenisedCardRepository.save(tokenisedCard);
        return ResponseEntity
            .created(new URI("/api/tokenised-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tokenised-cards/:id} : Updates an existing tokenisedCard.
     *
     * @param id the id of the tokenisedCard to save.
     * @param tokenisedCard the tokenisedCard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tokenisedCard,
     * or with status {@code 400 (Bad Request)} if the tokenisedCard is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tokenisedCard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tokenised-cards/{id}")
    public ResponseEntity<TokenisedCard> updateTokenisedCard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TokenisedCard tokenisedCard
    ) throws URISyntaxException {
        log.debug("REST request to update TokenisedCard : {}, {}", id, tokenisedCard);
        if (tokenisedCard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tokenisedCard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tokenisedCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TokenisedCard result = tokenisedCardRepository.save(tokenisedCard);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tokenisedCard.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tokenised-cards/:id} : Partial updates given fields of an existing tokenisedCard, field will ignore if it is null
     *
     * @param id the id of the tokenisedCard to save.
     * @param tokenisedCard the tokenisedCard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tokenisedCard,
     * or with status {@code 400 (Bad Request)} if the tokenisedCard is not valid,
     * or with status {@code 404 (Not Found)} if the tokenisedCard is not found,
     * or with status {@code 500 (Internal Server Error)} if the tokenisedCard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tokenised-cards/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TokenisedCard> partialUpdateTokenisedCard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TokenisedCard tokenisedCard
    ) throws URISyntaxException {
        log.debug("REST request to partial update TokenisedCard partially : {}, {}", id, tokenisedCard);
        if (tokenisedCard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tokenisedCard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tokenisedCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TokenisedCard> result = tokenisedCardRepository
            .findById(tokenisedCard.getId())
            .map(existingTokenisedCard -> {
                if (tokenisedCard.getToken() != null) {
                    existingTokenisedCard.setToken(tokenisedCard.getToken());
                }
                if (tokenisedCard.getCardExpiryMonth() != null) {
                    existingTokenisedCard.setCardExpiryMonth(tokenisedCard.getCardExpiryMonth());
                }
                if (tokenisedCard.getCardExpiryYear() != null) {
                    existingTokenisedCard.setCardExpiryYear(tokenisedCard.getCardExpiryYear());
                }
                if (tokenisedCard.getTruncatedCardNumber() != null) {
                    existingTokenisedCard.setTruncatedCardNumber(tokenisedCard.getTruncatedCardNumber());
                }

                return existingTokenisedCard;
            })
            .map(tokenisedCardRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tokenisedCard.getId().toString())
        );
    }

    /**
     * {@code GET  /tokenised-cards} : get all the tokenisedCards.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tokenisedCards in body.
     */
    @GetMapping("/tokenised-cards")
    public List<TokenisedCard> getAllTokenisedCards() {
        log.debug("REST request to get all TokenisedCards");
        return tokenisedCardRepository.findAll();
    }

    /**
     * {@code GET  /tokenised-cards/:id} : get the "id" tokenisedCard.
     *
     * @param id the id of the tokenisedCard to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tokenisedCard, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tokenised-cards/{id}")
    public ResponseEntity<TokenisedCard> getTokenisedCard(@PathVariable Long id) {
        log.debug("REST request to get TokenisedCard : {}", id);
        Optional<TokenisedCard> tokenisedCard = tokenisedCardRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tokenisedCard);
    }

    /**
     * {@code DELETE  /tokenised-cards/:id} : delete the "id" tokenisedCard.
     *
     * @param id the id of the tokenisedCard to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tokenised-cards/{id}")
    public ResponseEntity<Void> deleteTokenisedCard(@PathVariable Long id) {
        log.debug("REST request to delete TokenisedCard : {}", id);
        tokenisedCardRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
