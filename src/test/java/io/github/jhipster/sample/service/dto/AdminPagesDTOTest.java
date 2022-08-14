package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminPagesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminPagesDTO.class);
        AdminPagesDTO adminPagesDTO1 = new AdminPagesDTO();
        adminPagesDTO1.setId(1L);
        AdminPagesDTO adminPagesDTO2 = new AdminPagesDTO();
        assertThat(adminPagesDTO1).isNotEqualTo(adminPagesDTO2);
        adminPagesDTO2.setId(adminPagesDTO1.getId());
        assertThat(adminPagesDTO1).isEqualTo(adminPagesDTO2);
        adminPagesDTO2.setId(2L);
        assertThat(adminPagesDTO1).isNotEqualTo(adminPagesDTO2);
        adminPagesDTO1.setId(null);
        assertThat(adminPagesDTO1).isNotEqualTo(adminPagesDTO2);
    }
}
