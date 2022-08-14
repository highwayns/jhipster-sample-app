package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminSessionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminSessions.class);
        AdminSessions adminSessions1 = new AdminSessions();
        adminSessions1.setId(1L);
        AdminSessions adminSessions2 = new AdminSessions();
        adminSessions2.setId(adminSessions1.getId());
        assertThat(adminSessions1).isEqualTo(adminSessions2);
        adminSessions2.setId(2L);
        assertThat(adminSessions1).isNotEqualTo(adminSessions2);
        adminSessions1.setId(null);
        assertThat(adminSessions1).isNotEqualTo(adminSessions2);
    }
}
