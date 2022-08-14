package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequestDescriptionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestDescriptionsDTO.class);
        RequestDescriptionsDTO requestDescriptionsDTO1 = new RequestDescriptionsDTO();
        requestDescriptionsDTO1.setId(1L);
        RequestDescriptionsDTO requestDescriptionsDTO2 = new RequestDescriptionsDTO();
        assertThat(requestDescriptionsDTO1).isNotEqualTo(requestDescriptionsDTO2);
        requestDescriptionsDTO2.setId(requestDescriptionsDTO1.getId());
        assertThat(requestDescriptionsDTO1).isEqualTo(requestDescriptionsDTO2);
        requestDescriptionsDTO2.setId(2L);
        assertThat(requestDescriptionsDTO1).isNotEqualTo(requestDescriptionsDTO2);
        requestDescriptionsDTO1.setId(null);
        assertThat(requestDescriptionsDTO1).isNotEqualTo(requestDescriptionsDTO2);
    }
}
