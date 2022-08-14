package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankAccountsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankAccounts.class);
        BankAccounts bankAccounts1 = new BankAccounts();
        bankAccounts1.setId(1L);
        BankAccounts bankAccounts2 = new BankAccounts();
        bankAccounts2.setId(bankAccounts1.getId());
        assertThat(bankAccounts1).isEqualTo(bankAccounts2);
        bankAccounts2.setId(2L);
        assertThat(bankAccounts1).isNotEqualTo(bankAccounts2);
        bankAccounts1.setId(null);
        assertThat(bankAccounts1).isNotEqualTo(bankAccounts2);
    }
}
