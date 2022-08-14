package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatusEscrowsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatusEscrows.class);
        StatusEscrows statusEscrows1 = new StatusEscrows();
        statusEscrows1.setId(1L);
        StatusEscrows statusEscrows2 = new StatusEscrows();
        statusEscrows2.setId(statusEscrows1.getId());
        assertThat(statusEscrows1).isEqualTo(statusEscrows2);
        statusEscrows2.setId(2L);
        assertThat(statusEscrows1).isNotEqualTo(statusEscrows2);
        statusEscrows1.setId(null);
        assertThat(statusEscrows1).isNotEqualTo(statusEscrows2);
    }
}
