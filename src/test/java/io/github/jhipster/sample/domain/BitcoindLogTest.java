package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BitcoindLogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BitcoindLog.class);
        BitcoindLog bitcoindLog1 = new BitcoindLog();
        bitcoindLog1.setId(1L);
        BitcoindLog bitcoindLog2 = new BitcoindLog();
        bitcoindLog2.setId(bitcoindLog1.getId());
        assertThat(bitcoindLog1).isEqualTo(bitcoindLog2);
        bitcoindLog2.setId(2L);
        assertThat(bitcoindLog1).isNotEqualTo(bitcoindLog2);
        bitcoindLog1.setId(null);
        assertThat(bitcoindLog1).isNotEqualTo(bitcoindLog2);
    }
}
