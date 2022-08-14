package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BitcoinAddressesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BitcoinAddressesDTO.class);
        BitcoinAddressesDTO bitcoinAddressesDTO1 = new BitcoinAddressesDTO();
        bitcoinAddressesDTO1.setId(1L);
        BitcoinAddressesDTO bitcoinAddressesDTO2 = new BitcoinAddressesDTO();
        assertThat(bitcoinAddressesDTO1).isNotEqualTo(bitcoinAddressesDTO2);
        bitcoinAddressesDTO2.setId(bitcoinAddressesDTO1.getId());
        assertThat(bitcoinAddressesDTO1).isEqualTo(bitcoinAddressesDTO2);
        bitcoinAddressesDTO2.setId(2L);
        assertThat(bitcoinAddressesDTO1).isNotEqualTo(bitcoinAddressesDTO2);
        bitcoinAddressesDTO1.setId(null);
        assertThat(bitcoinAddressesDTO1).isNotEqualTo(bitcoinAddressesDTO2);
    }
}
