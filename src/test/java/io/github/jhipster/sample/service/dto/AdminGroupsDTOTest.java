package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminGroupsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminGroupsDTO.class);
        AdminGroupsDTO adminGroupsDTO1 = new AdminGroupsDTO();
        adminGroupsDTO1.setId(1L);
        AdminGroupsDTO adminGroupsDTO2 = new AdminGroupsDTO();
        assertThat(adminGroupsDTO1).isNotEqualTo(adminGroupsDTO2);
        adminGroupsDTO2.setId(adminGroupsDTO1.getId());
        assertThat(adminGroupsDTO1).isEqualTo(adminGroupsDTO2);
        adminGroupsDTO2.setId(2L);
        assertThat(adminGroupsDTO1).isNotEqualTo(adminGroupsDTO2);
        adminGroupsDTO1.setId(null);
        assertThat(adminGroupsDTO1).isNotEqualTo(adminGroupsDTO2);
    }
}
