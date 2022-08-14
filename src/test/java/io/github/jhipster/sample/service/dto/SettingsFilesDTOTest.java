package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SettingsFilesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SettingsFilesDTO.class);
        SettingsFilesDTO settingsFilesDTO1 = new SettingsFilesDTO();
        settingsFilesDTO1.setId(1L);
        SettingsFilesDTO settingsFilesDTO2 = new SettingsFilesDTO();
        assertThat(settingsFilesDTO1).isNotEqualTo(settingsFilesDTO2);
        settingsFilesDTO2.setId(settingsFilesDTO1.getId());
        assertThat(settingsFilesDTO1).isEqualTo(settingsFilesDTO2);
        settingsFilesDTO2.setId(2L);
        assertThat(settingsFilesDTO1).isNotEqualTo(settingsFilesDTO2);
        settingsFilesDTO1.setId(null);
        assertThat(settingsFilesDTO1).isNotEqualTo(settingsFilesDTO2);
    }
}
