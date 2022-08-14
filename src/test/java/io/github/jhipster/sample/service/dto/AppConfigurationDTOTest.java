package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppConfigurationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppConfigurationDTO.class);
        AppConfigurationDTO appConfigurationDTO1 = new AppConfigurationDTO();
        appConfigurationDTO1.setId(1L);
        AppConfigurationDTO appConfigurationDTO2 = new AppConfigurationDTO();
        assertThat(appConfigurationDTO1).isNotEqualTo(appConfigurationDTO2);
        appConfigurationDTO2.setId(appConfigurationDTO1.getId());
        assertThat(appConfigurationDTO1).isEqualTo(appConfigurationDTO2);
        appConfigurationDTO2.setId(2L);
        assertThat(appConfigurationDTO1).isNotEqualTo(appConfigurationDTO2);
        appConfigurationDTO1.setId(null);
        assertThat(appConfigurationDTO1).isNotEqualTo(appConfigurationDTO2);
    }
}
