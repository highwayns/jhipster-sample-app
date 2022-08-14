package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminCronDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminCronDTO.class);
        AdminCronDTO adminCronDTO1 = new AdminCronDTO();
        adminCronDTO1.setId(1L);
        AdminCronDTO adminCronDTO2 = new AdminCronDTO();
        assertThat(adminCronDTO1).isNotEqualTo(adminCronDTO2);
        adminCronDTO2.setId(adminCronDTO1.getId());
        assertThat(adminCronDTO1).isEqualTo(adminCronDTO2);
        adminCronDTO2.setId(2L);
        assertThat(adminCronDTO1).isNotEqualTo(adminCronDTO2);
        adminCronDTO1.setId(null);
        assertThat(adminCronDTO1).isNotEqualTo(adminCronDTO2);
    }
}
