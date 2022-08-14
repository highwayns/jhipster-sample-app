package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FeeScheduleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeeSchedule.class);
        FeeSchedule feeSchedule1 = new FeeSchedule();
        feeSchedule1.setId(1L);
        FeeSchedule feeSchedule2 = new FeeSchedule();
        feeSchedule2.setId(feeSchedule1.getId());
        assertThat(feeSchedule1).isEqualTo(feeSchedule2);
        feeSchedule2.setId(2L);
        assertThat(feeSchedule1).isNotEqualTo(feeSchedule2);
        feeSchedule1.setId(null);
        assertThat(feeSchedule1).isNotEqualTo(feeSchedule2);
    }
}
