package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatusEscrowsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatusEscrowsDTO.class);
        StatusEscrowsDTO statusEscrowsDTO1 = new StatusEscrowsDTO();
        statusEscrowsDTO1.setId(1L);
        StatusEscrowsDTO statusEscrowsDTO2 = new StatusEscrowsDTO();
        assertThat(statusEscrowsDTO1).isNotEqualTo(statusEscrowsDTO2);
        statusEscrowsDTO2.setId(statusEscrowsDTO1.getId());
        assertThat(statusEscrowsDTO1).isEqualTo(statusEscrowsDTO2);
        statusEscrowsDTO2.setId(2L);
        assertThat(statusEscrowsDTO1).isNotEqualTo(statusEscrowsDTO2);
        statusEscrowsDTO1.setId(null);
        assertThat(statusEscrowsDTO1).isNotEqualTo(statusEscrowsDTO2);
    }
}
