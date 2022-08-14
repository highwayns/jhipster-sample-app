package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminImageSizesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminImageSizesDTO.class);
        AdminImageSizesDTO adminImageSizesDTO1 = new AdminImageSizesDTO();
        adminImageSizesDTO1.setId(1L);
        AdminImageSizesDTO adminImageSizesDTO2 = new AdminImageSizesDTO();
        assertThat(adminImageSizesDTO1).isNotEqualTo(adminImageSizesDTO2);
        adminImageSizesDTO2.setId(adminImageSizesDTO1.getId());
        assertThat(adminImageSizesDTO1).isEqualTo(adminImageSizesDTO2);
        adminImageSizesDTO2.setId(2L);
        assertThat(adminImageSizesDTO1).isNotEqualTo(adminImageSizesDTO2);
        adminImageSizesDTO1.setId(null);
        assertThat(adminImageSizesDTO1).isNotEqualTo(adminImageSizesDTO2);
    }
}
