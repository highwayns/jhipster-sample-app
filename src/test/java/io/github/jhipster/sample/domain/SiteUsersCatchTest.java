package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SiteUsersCatchTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteUsersCatch.class);
        SiteUsersCatch siteUsersCatch1 = new SiteUsersCatch();
        siteUsersCatch1.setId(1L);
        SiteUsersCatch siteUsersCatch2 = new SiteUsersCatch();
        siteUsersCatch2.setId(siteUsersCatch1.getId());
        assertThat(siteUsersCatch1).isEqualTo(siteUsersCatch2);
        siteUsersCatch2.setId(2L);
        assertThat(siteUsersCatch1).isNotEqualTo(siteUsersCatch2);
        siteUsersCatch1.setId(null);
        assertThat(siteUsersCatch1).isNotEqualTo(siteUsersCatch2);
    }
}
