package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SettingsFilesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SettingsFiles.class);
        SettingsFiles settingsFiles1 = new SettingsFiles();
        settingsFiles1.setId(1L);
        SettingsFiles settingsFiles2 = new SettingsFiles();
        settingsFiles2.setId(settingsFiles1.getId());
        assertThat(settingsFiles1).isEqualTo(settingsFiles2);
        settingsFiles2.setId(2L);
        assertThat(settingsFiles1).isNotEqualTo(settingsFiles2);
        settingsFiles1.setId(null);
        assertThat(settingsFiles1).isNotEqualTo(settingsFiles2);
    }
}
