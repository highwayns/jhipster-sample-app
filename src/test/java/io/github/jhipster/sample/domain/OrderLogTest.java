package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderLogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderLog.class);
        OrderLog orderLog1 = new OrderLog();
        orderLog1.setId(1L);
        OrderLog orderLog2 = new OrderLog();
        orderLog2.setId(orderLog1.getId());
        assertThat(orderLog1).isEqualTo(orderLog2);
        orderLog2.setId(2L);
        assertThat(orderLog1).isNotEqualTo(orderLog2);
        orderLog1.setId(null);
        assertThat(orderLog1).isNotEqualTo(orderLog2);
    }
}
