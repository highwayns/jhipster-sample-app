package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminSessionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminSessionsDTO.class);
        AdminSessionsDTO adminSessionsDTO1 = new AdminSessionsDTO();
        adminSessionsDTO1.setId(1L);
        AdminSessionsDTO adminSessionsDTO2 = new AdminSessionsDTO();
        assertThat(adminSessionsDTO1).isNotEqualTo(adminSessionsDTO2);
        adminSessionsDTO2.setId(adminSessionsDTO1.getId());
        assertThat(adminSessionsDTO1).isEqualTo(adminSessionsDTO2);
        adminSessionsDTO2.setId(2L);
        assertThat(adminSessionsDTO1).isNotEqualTo(adminSessionsDTO2);
        adminSessionsDTO1.setId(null);
        assertThat(adminSessionsDTO1).isNotEqualTo(adminSessionsDTO2);
    }
}
