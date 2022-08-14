package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LangDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LangDTO.class);
        LangDTO langDTO1 = new LangDTO();
        langDTO1.setId(1L);
        LangDTO langDTO2 = new LangDTO();
        assertThat(langDTO1).isNotEqualTo(langDTO2);
        langDTO2.setId(langDTO1.getId());
        assertThat(langDTO1).isEqualTo(langDTO2);
        langDTO2.setId(2L);
        assertThat(langDTO1).isNotEqualTo(langDTO2);
        langDTO1.setId(null);
        assertThat(langDTO1).isNotEqualTo(langDTO2);
    }
}
