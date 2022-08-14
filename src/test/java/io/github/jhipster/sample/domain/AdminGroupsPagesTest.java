package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminGroupsPagesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminGroupsPages.class);
        AdminGroupsPages adminGroupsPages1 = new AdminGroupsPages();
        adminGroupsPages1.setId(1L);
        AdminGroupsPages adminGroupsPages2 = new AdminGroupsPages();
        adminGroupsPages2.setId(adminGroupsPages1.getId());
        assertThat(adminGroupsPages1).isEqualTo(adminGroupsPages2);
        adminGroupsPages2.setId(2L);
        assertThat(adminGroupsPages1).isNotEqualTo(adminGroupsPages2);
        adminGroupsPages1.setId(null);
        assertThat(adminGroupsPages1).isNotEqualTo(adminGroupsPages2);
    }
}
