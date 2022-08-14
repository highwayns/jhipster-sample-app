package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderLogDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderLogDTO.class);
        OrderLogDTO orderLogDTO1 = new OrderLogDTO();
        orderLogDTO1.setId(1L);
        OrderLogDTO orderLogDTO2 = new OrderLogDTO();
        assertThat(orderLogDTO1).isNotEqualTo(orderLogDTO2);
        orderLogDTO2.setId(orderLogDTO1.getId());
        assertThat(orderLogDTO1).isEqualTo(orderLogDTO2);
        orderLogDTO2.setId(2L);
        assertThat(orderLogDTO1).isNotEqualTo(orderLogDTO2);
        orderLogDTO1.setId(null);
        assertThat(orderLogDTO1).isNotEqualTo(orderLogDTO2);
    }
}
