package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentJobAttributesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentJobAttributes.class);
        PaymentJobAttributes paymentJobAttributes1 = new PaymentJobAttributes();
        paymentJobAttributes1.setId(1L);
        PaymentJobAttributes paymentJobAttributes2 = new PaymentJobAttributes();
        paymentJobAttributes2.setId(paymentJobAttributes1.getId());
        assertThat(paymentJobAttributes1).isEqualTo(paymentJobAttributes2);
        paymentJobAttributes2.setId(2L);
        assertThat(paymentJobAttributes1).isNotEqualTo(paymentJobAttributes2);
        paymentJobAttributes1.setId(null);
        assertThat(paymentJobAttributes1).isNotEqualTo(paymentJobAttributes2);
    }
}
