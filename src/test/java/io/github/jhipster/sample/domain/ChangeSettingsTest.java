package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChangeSettingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChangeSettings.class);
        ChangeSettings changeSettings1 = new ChangeSettings();
        changeSettings1.setId(1L);
        ChangeSettings changeSettings2 = new ChangeSettings();
        changeSettings2.setId(changeSettings1.getId());
        assertThat(changeSettings1).isEqualTo(changeSettings2);
        changeSettings2.setId(2L);
        assertThat(changeSettings1).isNotEqualTo(changeSettings2);
        changeSettings1.setId(null);
        assertThat(changeSettings1).isNotEqualTo(changeSettings2);
    }
}
