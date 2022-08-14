package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConversionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConversionsDTO.class);
        ConversionsDTO conversionsDTO1 = new ConversionsDTO();
        conversionsDTO1.setId(1L);
        ConversionsDTO conversionsDTO2 = new ConversionsDTO();
        assertThat(conversionsDTO1).isNotEqualTo(conversionsDTO2);
        conversionsDTO2.setId(conversionsDTO1.getId());
        assertThat(conversionsDTO1).isEqualTo(conversionsDTO2);
        conversionsDTO2.setId(2L);
        assertThat(conversionsDTO1).isNotEqualTo(conversionsDTO2);
        conversionsDTO1.setId(null);
        assertThat(conversionsDTO1).isNotEqualTo(conversionsDTO2);
    }
}
