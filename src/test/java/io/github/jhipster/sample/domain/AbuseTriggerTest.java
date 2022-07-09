package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AbuseTriggerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AbuseTrigger.class);
        AbuseTrigger abuseTrigger1 = new AbuseTrigger();
        abuseTrigger1.setId(1L);
        AbuseTrigger abuseTrigger2 = new AbuseTrigger();
        abuseTrigger2.setId(abuseTrigger1.getId());
        assertThat(abuseTrigger1).isEqualTo(abuseTrigger2);
        abuseTrigger2.setId(2L);
        assertThat(abuseTrigger1).isNotEqualTo(abuseTrigger2);
        abuseTrigger1.setId(null);
        assertThat(abuseTrigger1).isNotEqualTo(abuseTrigger2);
    }
}
