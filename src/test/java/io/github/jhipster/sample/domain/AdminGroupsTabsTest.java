package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminGroupsTabsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminGroupsTabs.class);
        AdminGroupsTabs adminGroupsTabs1 = new AdminGroupsTabs();
        adminGroupsTabs1.setId(1L);
        AdminGroupsTabs adminGroupsTabs2 = new AdminGroupsTabs();
        adminGroupsTabs2.setId(adminGroupsTabs1.getId());
        assertThat(adminGroupsTabs1).isEqualTo(adminGroupsTabs2);
        adminGroupsTabs2.setId(2L);
        assertThat(adminGroupsTabs1).isNotEqualTo(adminGroupsTabs2);
        adminGroupsTabs1.setId(null);
        assertThat(adminGroupsTabs1).isNotEqualTo(adminGroupsTabs2);
    }
}
