package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentMethodInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentMethodInfo.class);
        PaymentMethodInfo paymentMethodInfo1 = new PaymentMethodInfo();
        paymentMethodInfo1.setId(1L);
        PaymentMethodInfo paymentMethodInfo2 = new PaymentMethodInfo();
        paymentMethodInfo2.setId(paymentMethodInfo1.getId());
        assertThat(paymentMethodInfo1).isEqualTo(paymentMethodInfo2);
        paymentMethodInfo2.setId(2L);
        assertThat(paymentMethodInfo1).isNotEqualTo(paymentMethodInfo2);
        paymentMethodInfo1.setId(null);
        assertThat(paymentMethodInfo1).isNotEqualTo(paymentMethodInfo2);
    }
}
