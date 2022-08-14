package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SiteUsersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteUsers.class);
        SiteUsers siteUsers1 = new SiteUsers();
        siteUsers1.setId(1L);
        SiteUsers siteUsers2 = new SiteUsers();
        siteUsers2.setId(siteUsers1.getId());
        assertThat(siteUsers1).isEqualTo(siteUsers2);
        siteUsers2.setId(2L);
        assertThat(siteUsers1).isNotEqualTo(siteUsers2);
        siteUsers1.setId(null);
        assertThat(siteUsers1).isNotEqualTo(siteUsers2);
    }
}
