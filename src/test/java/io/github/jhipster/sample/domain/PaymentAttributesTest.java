package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentAttributesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentAttributes.class);
        PaymentAttributes paymentAttributes1 = new PaymentAttributes();
        paymentAttributes1.setId(1L);
        PaymentAttributes paymentAttributes2 = new PaymentAttributes();
        paymentAttributes2.setId(paymentAttributes1.getId());
        assertThat(paymentAttributes1).isEqualTo(paymentAttributes2);
        paymentAttributes2.setId(2L);
        assertThat(paymentAttributes1).isNotEqualTo(paymentAttributes2);
        paymentAttributes1.setId(null);
        assertThat(paymentAttributes1).isNotEqualTo(paymentAttributes2);
    }
}
