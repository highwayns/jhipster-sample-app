package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminControlsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminControls.class);
        AdminControls adminControls1 = new AdminControls();
        adminControls1.setId(1L);
        AdminControls adminControls2 = new AdminControls();
        adminControls2.setId(adminControls1.getId());
        assertThat(adminControls1).isEqualTo(adminControls2);
        adminControls2.setId(2L);
        assertThat(adminControls1).isNotEqualTo(adminControls2);
        adminControls1.setId(null);
        assertThat(adminControls1).isNotEqualTo(adminControls2);
    }
}
