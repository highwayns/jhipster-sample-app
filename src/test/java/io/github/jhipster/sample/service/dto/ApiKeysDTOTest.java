package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApiKeysDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiKeysDTO.class);
        ApiKeysDTO apiKeysDTO1 = new ApiKeysDTO();
        apiKeysDTO1.setId(1L);
        ApiKeysDTO apiKeysDTO2 = new ApiKeysDTO();
        assertThat(apiKeysDTO1).isNotEqualTo(apiKeysDTO2);
        apiKeysDTO2.setId(apiKeysDTO1.getId());
        assertThat(apiKeysDTO1).isEqualTo(apiKeysDTO2);
        apiKeysDTO2.setId(2L);
        assertThat(apiKeysDTO1).isNotEqualTo(apiKeysDTO2);
        apiKeysDTO1.setId(null);
        assertThat(apiKeysDTO1).isNotEqualTo(apiKeysDTO2);
    }
}
