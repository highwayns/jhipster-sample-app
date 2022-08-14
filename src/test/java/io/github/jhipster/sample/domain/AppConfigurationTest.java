package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppConfigurationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppConfiguration.class);
        AppConfiguration appConfiguration1 = new AppConfiguration();
        appConfiguration1.setId(1L);
        AppConfiguration appConfiguration2 = new AppConfiguration();
        appConfiguration2.setId(appConfiguration1.getId());
        assertThat(appConfiguration1).isEqualTo(appConfiguration2);
        appConfiguration2.setId(2L);
        assertThat(appConfiguration1).isNotEqualTo(appConfiguration2);
        appConfiguration1.setId(null);
        assertThat(appConfiguration1).isNotEqualTo(appConfiguration2);
    }
}
