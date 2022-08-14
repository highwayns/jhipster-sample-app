package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminUsersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminUsersDTO.class);
        AdminUsersDTO adminUsersDTO1 = new AdminUsersDTO();
        adminUsersDTO1.setId(1L);
        AdminUsersDTO adminUsersDTO2 = new AdminUsersDTO();
        assertThat(adminUsersDTO1).isNotEqualTo(adminUsersDTO2);
        adminUsersDTO2.setId(adminUsersDTO1.getId());
        assertThat(adminUsersDTO1).isEqualTo(adminUsersDTO2);
        adminUsersDTO2.setId(2L);
        assertThat(adminUsersDTO1).isNotEqualTo(adminUsersDTO2);
        adminUsersDTO1.setId(null);
        assertThat(adminUsersDTO1).isNotEqualTo(adminUsersDTO2);
    }
}
