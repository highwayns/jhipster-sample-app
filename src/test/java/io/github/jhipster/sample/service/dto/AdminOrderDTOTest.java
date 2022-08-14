package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminOrderDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminOrderDTO.class);
        AdminOrderDTO adminOrderDTO1 = new AdminOrderDTO();
        adminOrderDTO1.setId(1L);
        AdminOrderDTO adminOrderDTO2 = new AdminOrderDTO();
        assertThat(adminOrderDTO1).isNotEqualTo(adminOrderDTO2);
        adminOrderDTO2.setId(adminOrderDTO1.getId());
        assertThat(adminOrderDTO1).isEqualTo(adminOrderDTO2);
        adminOrderDTO2.setId(2L);
        assertThat(adminOrderDTO1).isNotEqualTo(adminOrderDTO2);
        adminOrderDTO1.setId(null);
        assertThat(adminOrderDTO1).isNotEqualTo(adminOrderDTO2);
    }
}
