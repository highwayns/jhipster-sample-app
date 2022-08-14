package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChangeSettingsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChangeSettingsDTO.class);
        ChangeSettingsDTO changeSettingsDTO1 = new ChangeSettingsDTO();
        changeSettingsDTO1.setId(1L);
        ChangeSettingsDTO changeSettingsDTO2 = new ChangeSettingsDTO();
        assertThat(changeSettingsDTO1).isNotEqualTo(changeSettingsDTO2);
        changeSettingsDTO2.setId(changeSettingsDTO1.getId());
        assertThat(changeSettingsDTO1).isEqualTo(changeSettingsDTO2);
        changeSettingsDTO2.setId(2L);
        assertThat(changeSettingsDTO1).isNotEqualTo(changeSettingsDTO2);
        changeSettingsDTO1.setId(null);
        assertThat(changeSettingsDTO1).isNotEqualTo(changeSettingsDTO2);
    }
}
