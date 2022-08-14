package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminGroupsTabsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminGroupsTabsDTO.class);
        AdminGroupsTabsDTO adminGroupsTabsDTO1 = new AdminGroupsTabsDTO();
        adminGroupsTabsDTO1.setId(1L);
        AdminGroupsTabsDTO adminGroupsTabsDTO2 = new AdminGroupsTabsDTO();
        assertThat(adminGroupsTabsDTO1).isNotEqualTo(adminGroupsTabsDTO2);
        adminGroupsTabsDTO2.setId(adminGroupsTabsDTO1.getId());
        assertThat(adminGroupsTabsDTO1).isEqualTo(adminGroupsTabsDTO2);
        adminGroupsTabsDTO2.setId(2L);
        assertThat(adminGroupsTabsDTO1).isNotEqualTo(adminGroupsTabsDTO2);
        adminGroupsTabsDTO1.setId(null);
        assertThat(adminGroupsTabsDTO1).isNotEqualTo(adminGroupsTabsDTO2);
    }
}
