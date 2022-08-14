package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConversionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Conversions.class);
        Conversions conversions1 = new Conversions();
        conversions1.setId(1L);
        Conversions conversions2 = new Conversions();
        conversions2.setId(conversions1.getId());
        assertThat(conversions1).isEqualTo(conversions2);
        conversions2.setId(2L);
        assertThat(conversions1).isNotEqualTo(conversions2);
        conversions1.setId(null);
        assertThat(conversions1).isNotEqualTo(conversions2);
    }
}
