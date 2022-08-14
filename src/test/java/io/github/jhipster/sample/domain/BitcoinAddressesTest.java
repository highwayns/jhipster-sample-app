package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BitcoinAddressesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BitcoinAddresses.class);
        BitcoinAddresses bitcoinAddresses1 = new BitcoinAddresses();
        bitcoinAddresses1.setId(1L);
        BitcoinAddresses bitcoinAddresses2 = new BitcoinAddresses();
        bitcoinAddresses2.setId(bitcoinAddresses1.getId());
        assertThat(bitcoinAddresses1).isEqualTo(bitcoinAddresses2);
        bitcoinAddresses2.setId(2L);
        assertThat(bitcoinAddresses1).isNotEqualTo(bitcoinAddresses2);
        bitcoinAddresses1.setId(null);
        assertThat(bitcoinAddresses1).isNotEqualTo(bitcoinAddresses2);
    }
}
