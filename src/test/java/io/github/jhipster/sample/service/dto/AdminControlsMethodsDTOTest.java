package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminControlsMethodsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminControlsMethodsDTO.class);
        AdminControlsMethodsDTO adminControlsMethodsDTO1 = new AdminControlsMethodsDTO();
        adminControlsMethodsDTO1.setId(1L);
        AdminControlsMethodsDTO adminControlsMethodsDTO2 = new AdminControlsMethodsDTO();
        assertThat(adminControlsMethodsDTO1).isNotEqualTo(adminControlsMethodsDTO2);
        adminControlsMethodsDTO2.setId(adminControlsMethodsDTO1.getId());
        assertThat(adminControlsMethodsDTO1).isEqualTo(adminControlsMethodsDTO2);
        adminControlsMethodsDTO2.setId(2L);
        assertThat(adminControlsMethodsDTO1).isNotEqualTo(adminControlsMethodsDTO2);
        adminControlsMethodsDTO1.setId(null);
        assertThat(adminControlsMethodsDTO1).isNotEqualTo(adminControlsMethodsDTO2);
    }
}
