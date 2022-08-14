package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminGroupsPagesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminGroupsPagesDTO.class);
        AdminGroupsPagesDTO adminGroupsPagesDTO1 = new AdminGroupsPagesDTO();
        adminGroupsPagesDTO1.setId(1L);
        AdminGroupsPagesDTO adminGroupsPagesDTO2 = new AdminGroupsPagesDTO();
        assertThat(adminGroupsPagesDTO1).isNotEqualTo(adminGroupsPagesDTO2);
        adminGroupsPagesDTO2.setId(adminGroupsPagesDTO1.getId());
        assertThat(adminGroupsPagesDTO1).isEqualTo(adminGroupsPagesDTO2);
        adminGroupsPagesDTO2.setId(2L);
        assertThat(adminGroupsPagesDTO1).isNotEqualTo(adminGroupsPagesDTO2);
        adminGroupsPagesDTO1.setId(null);
        assertThat(adminGroupsPagesDTO1).isNotEqualTo(adminGroupsPagesDTO2);
    }
}
