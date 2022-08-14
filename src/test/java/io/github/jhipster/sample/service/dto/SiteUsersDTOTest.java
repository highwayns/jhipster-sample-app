package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SiteUsersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteUsersDTO.class);
        SiteUsersDTO siteUsersDTO1 = new SiteUsersDTO();
        siteUsersDTO1.setId(1L);
        SiteUsersDTO siteUsersDTO2 = new SiteUsersDTO();
        assertThat(siteUsersDTO1).isNotEqualTo(siteUsersDTO2);
        siteUsersDTO2.setId(siteUsersDTO1.getId());
        assertThat(siteUsersDTO1).isEqualTo(siteUsersDTO2);
        siteUsersDTO2.setId(2L);
        assertThat(siteUsersDTO1).isNotEqualTo(siteUsersDTO2);
        siteUsersDTO1.setId(null);
        assertThat(siteUsersDTO1).isNotEqualTo(siteUsersDTO2);
    }
}
