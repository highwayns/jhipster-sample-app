package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminGroupsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminGroups.class);
        AdminGroups adminGroups1 = new AdminGroups();
        adminGroups1.setId(1L);
        AdminGroups adminGroups2 = new AdminGroups();
        adminGroups2.setId(adminGroups1.getId());
        assertThat(adminGroups1).isEqualTo(adminGroups2);
        adminGroups2.setId(2L);
        assertThat(adminGroups1).isNotEqualTo(adminGroups2);
        adminGroups1.setId(null);
        assertThat(adminGroups1).isNotEqualTo(adminGroups2);
    }
}
