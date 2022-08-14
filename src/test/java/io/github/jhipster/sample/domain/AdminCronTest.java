package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminCronTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminCron.class);
        AdminCron adminCron1 = new AdminCron();
        adminCron1.setId(1L);
        AdminCron adminCron2 = new AdminCron();
        adminCron2.setId(adminCron1.getId());
        assertThat(adminCron1).isEqualTo(adminCron2);
        adminCron2.setId(2L);
        assertThat(adminCron1).isNotEqualTo(adminCron2);
        adminCron1.setId(null);
        assertThat(adminCron1).isNotEqualTo(adminCron2);
    }
}
