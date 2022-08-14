package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminImageSizesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminImageSizes.class);
        AdminImageSizes adminImageSizes1 = new AdminImageSizes();
        adminImageSizes1.setId(1L);
        AdminImageSizes adminImageSizes2 = new AdminImageSizes();
        adminImageSizes2.setId(adminImageSizes1.getId());
        assertThat(adminImageSizes1).isEqualTo(adminImageSizes2);
        adminImageSizes2.setId(2L);
        assertThat(adminImageSizes1).isNotEqualTo(adminImageSizes2);
        adminImageSizes1.setId(null);
        assertThat(adminImageSizes1).isNotEqualTo(adminImageSizes2);
    }
}
