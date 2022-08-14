package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminTabsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminTabs.class);
        AdminTabs adminTabs1 = new AdminTabs();
        adminTabs1.setId(1L);
        AdminTabs adminTabs2 = new AdminTabs();
        adminTabs2.setId(adminTabs1.getId());
        assertThat(adminTabs1).isEqualTo(adminTabs2);
        adminTabs2.setId(2L);
        assertThat(adminTabs1).isNotEqualTo(adminTabs2);
        adminTabs1.setId(null);
        assertThat(adminTabs1).isNotEqualTo(adminTabs2);
    }
}
