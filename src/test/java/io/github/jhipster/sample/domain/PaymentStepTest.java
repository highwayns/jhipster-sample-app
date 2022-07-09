package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentStepTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentStep.class);
        PaymentStep paymentStep1 = new PaymentStep();
        paymentStep1.setId(1L);
        PaymentStep paymentStep2 = new PaymentStep();
        paymentStep2.setId(paymentStep1.getId());
        assertThat(paymentStep1).isEqualTo(paymentStep2);
        paymentStep2.setId(2L);
        assertThat(paymentStep1).isNotEqualTo(paymentStep2);
        paymentStep1.setId(null);
        assertThat(paymentStep1).isNotEqualTo(paymentStep2);
    }
}
