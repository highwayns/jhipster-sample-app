package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CurrentStatsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrentStats.class);
        CurrentStats currentStats1 = new CurrentStats();
        currentStats1.setId(1L);
        CurrentStats currentStats2 = new CurrentStats();
        currentStats2.setId(currentStats1.getId());
        assertThat(currentStats1).isEqualTo(currentStats2);
        currentStats2.setId(2L);
        assertThat(currentStats1).isNotEqualTo(currentStats2);
        currentStats1.setId(null);
        assertThat(currentStats1).isNotEqualTo(currentStats2);
    }
}
