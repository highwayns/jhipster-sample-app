package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentJobTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentJob.class);
        PaymentJob paymentJob1 = new PaymentJob();
        paymentJob1.setId(1L);
        PaymentJob paymentJob2 = new PaymentJob();
        paymentJob2.setId(paymentJob1.getId());
        assertThat(paymentJob1).isEqualTo(paymentJob2);
        paymentJob2.setId(2L);
        assertThat(paymentJob1).isNotEqualTo(paymentJob2);
        paymentJob1.setId(null);
        assertThat(paymentJob1).isNotEqualTo(paymentJob2);
    }
}
