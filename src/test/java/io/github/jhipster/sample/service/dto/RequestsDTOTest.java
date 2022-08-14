package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequestsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestsDTO.class);
        RequestsDTO requestsDTO1 = new RequestsDTO();
        requestsDTO1.setId(1L);
        RequestsDTO requestsDTO2 = new RequestsDTO();
        assertThat(requestsDTO1).isNotEqualTo(requestsDTO2);
        requestsDTO2.setId(requestsDTO1.getId());
        assertThat(requestsDTO1).isEqualTo(requestsDTO2);
        requestsDTO2.setId(2L);
        assertThat(requestsDTO1).isNotEqualTo(requestsDTO2);
        requestsDTO1.setId(null);
        assertThat(requestsDTO1).isNotEqualTo(requestsDTO2);
    }
}
