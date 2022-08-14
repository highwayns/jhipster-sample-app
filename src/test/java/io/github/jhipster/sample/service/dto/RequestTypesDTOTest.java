package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequestTypesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestTypesDTO.class);
        RequestTypesDTO requestTypesDTO1 = new RequestTypesDTO();
        requestTypesDTO1.setId(1L);
        RequestTypesDTO requestTypesDTO2 = new RequestTypesDTO();
        assertThat(requestTypesDTO1).isNotEqualTo(requestTypesDTO2);
        requestTypesDTO2.setId(requestTypesDTO1.getId());
        assertThat(requestTypesDTO1).isEqualTo(requestTypesDTO2);
        requestTypesDTO2.setId(2L);
        assertThat(requestTypesDTO1).isNotEqualTo(requestTypesDTO2);
        requestTypesDTO1.setId(null);
        assertThat(requestTypesDTO1).isNotEqualTo(requestTypesDTO2);
    }
}
