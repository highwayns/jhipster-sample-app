package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TokenisedCardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TokenisedCard.class);
        TokenisedCard tokenisedCard1 = new TokenisedCard();
        tokenisedCard1.setId(1L);
        TokenisedCard tokenisedCard2 = new TokenisedCard();
        tokenisedCard2.setId(tokenisedCard1.getId());
        assertThat(tokenisedCard1).isEqualTo(tokenisedCard2);
        tokenisedCard2.setId(2L);
        assertThat(tokenisedCard1).isNotEqualTo(tokenisedCard2);
        tokenisedCard1.setId(null);
        assertThat(tokenisedCard1).isNotEqualTo(tokenisedCard2);
    }
}
