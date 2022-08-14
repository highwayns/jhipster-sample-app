package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SiteUsersBalancesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteUsersBalances.class);
        SiteUsersBalances siteUsersBalances1 = new SiteUsersBalances();
        siteUsersBalances1.setId(1L);
        SiteUsersBalances siteUsersBalances2 = new SiteUsersBalances();
        siteUsersBalances2.setId(siteUsersBalances1.getId());
        assertThat(siteUsersBalances1).isEqualTo(siteUsersBalances2);
        siteUsersBalances2.setId(2L);
        assertThat(siteUsersBalances1).isNotEqualTo(siteUsersBalances2);
        siteUsersBalances1.setId(null);
        assertThat(siteUsersBalances1).isNotEqualTo(siteUsersBalances2);
    }
}
