package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecurrenceCriteriaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecurrenceCriteria.class);
        RecurrenceCriteria recurrenceCriteria1 = new RecurrenceCriteria();
        recurrenceCriteria1.setId(1L);
        RecurrenceCriteria recurrenceCriteria2 = new RecurrenceCriteria();
        recurrenceCriteria2.setId(recurrenceCriteria1.getId());
        assertThat(recurrenceCriteria1).isEqualTo(recurrenceCriteria2);
        recurrenceCriteria2.setId(2L);
        assertThat(recurrenceCriteria1).isNotEqualTo(recurrenceCriteria2);
        recurrenceCriteria1.setId(null);
        assertThat(recurrenceCriteria1).isNotEqualTo(recurrenceCriteria2);
    }
}
