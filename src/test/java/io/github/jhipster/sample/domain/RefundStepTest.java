package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RefundStepTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefundStep.class);
        RefundStep refundStep1 = new RefundStep();
        refundStep1.setId(1L);
        RefundStep refundStep2 = new RefundStep();
        refundStep2.setId(refundStep1.getId());
        assertThat(refundStep1).isEqualTo(refundStep2);
        refundStep2.setId(2L);
        assertThat(refundStep1).isNotEqualTo(refundStep2);
        refundStep1.setId(null);
        assertThat(refundStep1).isNotEqualTo(refundStep2);
    }
}
