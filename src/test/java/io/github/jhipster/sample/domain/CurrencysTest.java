package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CurrencysTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Currencys.class);
        Currencys currencys1 = new Currencys();
        currencys1.setId(1L);
        Currencys currencys2 = new Currencys();
        currencys2.setId(currencys1.getId());
        assertThat(currencys1).isEqualTo(currencys2);
        currencys2.setId(2L);
        assertThat(currencys1).isNotEqualTo(currencys2);
        currencys1.setId(null);
        assertThat(currencys1).isNotEqualTo(currencys2);
    }
}
