package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IsoCountriesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IsoCountries.class);
        IsoCountries isoCountries1 = new IsoCountries();
        isoCountries1.setId(1L);
        IsoCountries isoCountries2 = new IsoCountries();
        isoCountries2.setId(isoCountries1.getId());
        assertThat(isoCountries1).isEqualTo(isoCountries2);
        isoCountries2.setId(2L);
        assertThat(isoCountries1).isNotEqualTo(isoCountries2);
        isoCountries1.setId(null);
        assertThat(isoCountries1).isNotEqualTo(isoCountries2);
    }
}
