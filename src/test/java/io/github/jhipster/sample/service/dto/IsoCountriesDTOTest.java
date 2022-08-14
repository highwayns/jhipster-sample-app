package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IsoCountriesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IsoCountriesDTO.class);
        IsoCountriesDTO isoCountriesDTO1 = new IsoCountriesDTO();
        isoCountriesDTO1.setId(1L);
        IsoCountriesDTO isoCountriesDTO2 = new IsoCountriesDTO();
        assertThat(isoCountriesDTO1).isNotEqualTo(isoCountriesDTO2);
        isoCountriesDTO2.setId(isoCountriesDTO1.getId());
        assertThat(isoCountriesDTO1).isEqualTo(isoCountriesDTO2);
        isoCountriesDTO2.setId(2L);
        assertThat(isoCountriesDTO1).isNotEqualTo(isoCountriesDTO2);
        isoCountriesDTO1.setId(null);
        assertThat(isoCountriesDTO1).isNotEqualTo(isoCountriesDTO2);
    }
}
