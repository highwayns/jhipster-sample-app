package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BitcoindLogDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BitcoindLogDTO.class);
        BitcoindLogDTO bitcoindLogDTO1 = new BitcoindLogDTO();
        bitcoindLogDTO1.setId(1L);
        BitcoindLogDTO bitcoindLogDTO2 = new BitcoindLogDTO();
        assertThat(bitcoindLogDTO1).isNotEqualTo(bitcoindLogDTO2);
        bitcoindLogDTO2.setId(bitcoindLogDTO1.getId());
        assertThat(bitcoindLogDTO1).isEqualTo(bitcoindLogDTO2);
        bitcoindLogDTO2.setId(2L);
        assertThat(bitcoindLogDTO1).isNotEqualTo(bitcoindLogDTO2);
        bitcoindLogDTO1.setId(null);
        assertThat(bitcoindLogDTO1).isNotEqualTo(bitcoindLogDTO2);
    }
}
