package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CurrenciesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrenciesDTO.class);
        CurrenciesDTO currenciesDTO1 = new CurrenciesDTO();
        currenciesDTO1.setId(1L);
        CurrenciesDTO currenciesDTO2 = new CurrenciesDTO();
        assertThat(currenciesDTO1).isNotEqualTo(currenciesDTO2);
        currenciesDTO2.setId(currenciesDTO1.getId());
        assertThat(currenciesDTO1).isEqualTo(currenciesDTO2);
        currenciesDTO2.setId(2L);
        assertThat(currenciesDTO1).isNotEqualTo(currenciesDTO2);
        currenciesDTO1.setId(null);
        assertThat(currenciesDTO1).isNotEqualTo(currenciesDTO2);
    }
}
