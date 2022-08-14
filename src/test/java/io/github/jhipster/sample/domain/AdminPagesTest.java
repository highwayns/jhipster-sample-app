package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminPagesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminPages.class);
        AdminPages adminPages1 = new AdminPages();
        adminPages1.setId(1L);
        AdminPages adminPages2 = new AdminPages();
        adminPages2.setId(adminPages1.getId());
        assertThat(adminPages1).isEqualTo(adminPages2);
        adminPages2.setId(2L);
        assertThat(adminPages1).isNotEqualTo(adminPages2);
        adminPages1.setId(null);
        assertThat(adminPages1).isNotEqualTo(adminPages2);
    }
}
