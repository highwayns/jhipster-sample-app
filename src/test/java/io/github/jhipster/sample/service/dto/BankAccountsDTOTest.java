package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankAccountsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankAccountsDTO.class);
        BankAccountsDTO bankAccountsDTO1 = new BankAccountsDTO();
        bankAccountsDTO1.setId(1L);
        BankAccountsDTO bankAccountsDTO2 = new BankAccountsDTO();
        assertThat(bankAccountsDTO1).isNotEqualTo(bankAccountsDTO2);
        bankAccountsDTO2.setId(bankAccountsDTO1.getId());
        assertThat(bankAccountsDTO1).isEqualTo(bankAccountsDTO2);
        bankAccountsDTO2.setId(2L);
        assertThat(bankAccountsDTO1).isNotEqualTo(bankAccountsDTO2);
        bankAccountsDTO1.setId(null);
        assertThat(bankAccountsDTO1).isNotEqualTo(bankAccountsDTO2);
    }
}
