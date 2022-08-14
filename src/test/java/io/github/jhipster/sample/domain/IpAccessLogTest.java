package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IpAccessLogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IpAccessLog.class);
        IpAccessLog ipAccessLog1 = new IpAccessLog();
        ipAccessLog1.setId(1L);
        IpAccessLog ipAccessLog2 = new IpAccessLog();
        ipAccessLog2.setId(ipAccessLog1.getId());
        assertThat(ipAccessLog1).isEqualTo(ipAccessLog2);
        ipAccessLog2.setId(2L);
        assertThat(ipAccessLog1).isNotEqualTo(ipAccessLog2);
        ipAccessLog1.setId(null);
        assertThat(ipAccessLog1).isNotEqualTo(ipAccessLog2);
    }
}
