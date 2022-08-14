package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminControlsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminControlsDTO.class);
        AdminControlsDTO adminControlsDTO1 = new AdminControlsDTO();
        adminControlsDTO1.setId(1L);
        AdminControlsDTO adminControlsDTO2 = new AdminControlsDTO();
        assertThat(adminControlsDTO1).isNotEqualTo(adminControlsDTO2);
        adminControlsDTO2.setId(adminControlsDTO1.getId());
        assertThat(adminControlsDTO1).isEqualTo(adminControlsDTO2);
        adminControlsDTO2.setId(2L);
        assertThat(adminControlsDTO1).isNotEqualTo(adminControlsDTO2);
        adminControlsDTO1.setId(null);
        assertThat(adminControlsDTO1).isNotEqualTo(adminControlsDTO2);
    }
}
