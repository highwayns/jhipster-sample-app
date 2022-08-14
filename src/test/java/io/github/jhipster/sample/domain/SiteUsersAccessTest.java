package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SiteUsersAccessTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteUsersAccess.class);
        SiteUsersAccess siteUsersAccess1 = new SiteUsersAccess();
        siteUsersAccess1.setId(1L);
        SiteUsersAccess siteUsersAccess2 = new SiteUsersAccess();
        siteUsersAccess2.setId(siteUsersAccess1.getId());
        assertThat(siteUsersAccess1).isEqualTo(siteUsersAccess2);
        siteUsersAccess2.setId(2L);
        assertThat(siteUsersAccess1).isNotEqualTo(siteUsersAccess2);
        siteUsersAccess1.setId(null);
        assertThat(siteUsersAccess1).isNotEqualTo(siteUsersAccess2);
    }
}
