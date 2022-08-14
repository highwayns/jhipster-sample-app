package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminUsersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminUsers.class);
        AdminUsers adminUsers1 = new AdminUsers();
        adminUsers1.setId(1L);
        AdminUsers adminUsers2 = new AdminUsers();
        adminUsers2.setId(adminUsers1.getId());
        assertThat(adminUsers1).isEqualTo(adminUsers2);
        adminUsers2.setId(2L);
        assertThat(adminUsers1).isNotEqualTo(adminUsers2);
        adminUsers1.setId(null);
        assertThat(adminUsers1).isNotEqualTo(adminUsers2);
    }
}
