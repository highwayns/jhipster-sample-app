package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminTabsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminTabsDTO.class);
        AdminTabsDTO adminTabsDTO1 = new AdminTabsDTO();
        adminTabsDTO1.setId(1L);
        AdminTabsDTO adminTabsDTO2 = new AdminTabsDTO();
        assertThat(adminTabsDTO1).isNotEqualTo(adminTabsDTO2);
        adminTabsDTO2.setId(adminTabsDTO1.getId());
        assertThat(adminTabsDTO1).isEqualTo(adminTabsDTO2);
        adminTabsDTO2.setId(2L);
        assertThat(adminTabsDTO1).isNotEqualTo(adminTabsDTO2);
        adminTabsDTO1.setId(null);
        assertThat(adminTabsDTO1).isNotEqualTo(adminTabsDTO2);
    }
}
