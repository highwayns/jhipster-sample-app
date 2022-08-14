package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContentFilesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentFilesDTO.class);
        ContentFilesDTO contentFilesDTO1 = new ContentFilesDTO();
        contentFilesDTO1.setId(1L);
        ContentFilesDTO contentFilesDTO2 = new ContentFilesDTO();
        assertThat(contentFilesDTO1).isNotEqualTo(contentFilesDTO2);
        contentFilesDTO2.setId(contentFilesDTO1.getId());
        assertThat(contentFilesDTO1).isEqualTo(contentFilesDTO2);
        contentFilesDTO2.setId(2L);
        assertThat(contentFilesDTO1).isNotEqualTo(contentFilesDTO2);
        contentFilesDTO1.setId(null);
        assertThat(contentFilesDTO1).isNotEqualTo(contentFilesDTO2);
    }
}
