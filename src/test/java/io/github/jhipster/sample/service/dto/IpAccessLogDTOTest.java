package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IpAccessLogDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IpAccessLogDTO.class);
        IpAccessLogDTO ipAccessLogDTO1 = new IpAccessLogDTO();
        ipAccessLogDTO1.setId(1L);
        IpAccessLogDTO ipAccessLogDTO2 = new IpAccessLogDTO();
        assertThat(ipAccessLogDTO1).isNotEqualTo(ipAccessLogDTO2);
        ipAccessLogDTO2.setId(ipAccessLogDTO1.getId());
        assertThat(ipAccessLogDTO1).isEqualTo(ipAccessLogDTO2);
        ipAccessLogDTO2.setId(2L);
        assertThat(ipAccessLogDTO1).isNotEqualTo(ipAccessLogDTO2);
        ipAccessLogDTO1.setId(null);
        assertThat(ipAccessLogDTO1).isNotEqualTo(ipAccessLogDTO2);
    }
}
