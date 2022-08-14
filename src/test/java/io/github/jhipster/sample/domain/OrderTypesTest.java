package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderTypesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderTypes.class);
        OrderTypes orderTypes1 = new OrderTypes();
        orderTypes1.setId(1L);
        OrderTypes orderTypes2 = new OrderTypes();
        orderTypes2.setId(orderTypes1.getId());
        assertThat(orderTypes1).isEqualTo(orderTypes2);
        orderTypes2.setId(2L);
        assertThat(orderTypes1).isNotEqualTo(orderTypes2);
        orderTypes1.setId(null);
        assertThat(orderTypes1).isNotEqualTo(orderTypes2);
    }
}
