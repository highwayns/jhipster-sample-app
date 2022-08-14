package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequestStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestStatusDTO.class);
        RequestStatusDTO requestStatusDTO1 = new RequestStatusDTO();
        requestStatusDTO1.setId(1L);
        RequestStatusDTO requestStatusDTO2 = new RequestStatusDTO();
        assertThat(requestStatusDTO1).isNotEqualTo(requestStatusDTO2);
        requestStatusDTO2.setId(requestStatusDTO1.getId());
        assertThat(requestStatusDTO1).isEqualTo(requestStatusDTO2);
        requestStatusDTO2.setId(2L);
        assertThat(requestStatusDTO1).isNotEqualTo(requestStatusDTO2);
        requestStatusDTO1.setId(null);
        assertThat(requestStatusDTO1).isNotEqualTo(requestStatusDTO2);
    }
}
