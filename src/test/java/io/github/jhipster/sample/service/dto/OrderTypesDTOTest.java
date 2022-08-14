package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderTypesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderTypesDTO.class);
        OrderTypesDTO orderTypesDTO1 = new OrderTypesDTO();
        orderTypesDTO1.setId(1L);
        OrderTypesDTO orderTypesDTO2 = new OrderTypesDTO();
        assertThat(orderTypesDTO1).isNotEqualTo(orderTypesDTO2);
        orderTypesDTO2.setId(orderTypesDTO1.getId());
        assertThat(orderTypesDTO1).isEqualTo(orderTypesDTO2);
        orderTypesDTO2.setId(2L);
        assertThat(orderTypesDTO1).isNotEqualTo(orderTypesDTO2);
        orderTypesDTO1.setId(null);
        assertThat(orderTypesDTO1).isNotEqualTo(orderTypesDTO2);
    }
}
