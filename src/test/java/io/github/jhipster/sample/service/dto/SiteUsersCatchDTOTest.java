package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SiteUsersCatchDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteUsersCatchDTO.class);
        SiteUsersCatchDTO siteUsersCatchDTO1 = new SiteUsersCatchDTO();
        siteUsersCatchDTO1.setId(1L);
        SiteUsersCatchDTO siteUsersCatchDTO2 = new SiteUsersCatchDTO();
        assertThat(siteUsersCatchDTO1).isNotEqualTo(siteUsersCatchDTO2);
        siteUsersCatchDTO2.setId(siteUsersCatchDTO1.getId());
        assertThat(siteUsersCatchDTO1).isEqualTo(siteUsersCatchDTO2);
        siteUsersCatchDTO2.setId(2L);
        assertThat(siteUsersCatchDTO1).isNotEqualTo(siteUsersCatchDTO2);
        siteUsersCatchDTO1.setId(null);
        assertThat(siteUsersCatchDTO1).isNotEqualTo(siteUsersCatchDTO2);
    }
}
