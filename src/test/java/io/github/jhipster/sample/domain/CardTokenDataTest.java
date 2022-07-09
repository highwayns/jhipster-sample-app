package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CardTokenDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CardTokenData.class);
        CardTokenData cardTokenData1 = new CardTokenData();
        cardTokenData1.setId(1L);
        CardTokenData cardTokenData2 = new CardTokenData();
        cardTokenData2.setId(cardTokenData1.getId());
        assertThat(cardTokenData1).isEqualTo(cardTokenData2);
        cardTokenData2.setId(2L);
        assertThat(cardTokenData1).isNotEqualTo(cardTokenData2);
        cardTokenData1.setId(null);
        assertThat(cardTokenData1).isNotEqualTo(cardTokenData2);
    }
}
