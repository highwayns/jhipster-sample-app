package io.github.jhipster.sample.service.dto;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.github.jhipster.sample.domain.SiteUsers} entity.
 */
public class SiteUsersDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String pass;

    @NotNull
    @Size(max = 200)
    private String firstName;

    @NotNull
    @Size(max = 200)
    private String lastName;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    private Instant date;

    @NotNull
    @Size(max = 255)
    private String tel;

    @NotNull
    @Size(max = 255)
    private String user;

    @NotNull
    private Integer countryCode;

    @NotNull
    private YesNo authyRequested;

    @NotNull
    private YesNo verifiedAuthy;

    @NotNull
    private Integer authyId;

    @NotNull
    private YesNo usingSms;

    @NotNull
    private YesNo dontAsk30Days;

    @NotNull
    private Instant dontAskDate;

    @NotNull
    private YesNo confirmWithdrawalEmailBtc;

    @NotNull
    private YesNo confirmWithdrawal2faBtc;

    @NotNull
    private YesNo confirmWithdrawal2faBank;

    @NotNull
    private YesNo confirmWithdrawalEmailBank;

    @NotNull
    private YesNo notifyDepositBtc;

    @NotNull
    private YesNo notifyDepositBank;

    @NotNull
    private Instant lastUpdate;

    @NotNull
    private YesNo noLogins;

    @NotNull
    private YesNo notifyLogin;

    @NotNull
    private YesNo deactivated;

    @NotNull
    private YesNo locked;

    @NotNull
    @Size(max = 255)
    private String google2faCode;

    @NotNull
    private YesNo verifiedGoogle;

    @NotNull
    @Size(max = 255)
    private String lastLang;

    @NotNull
    private YesNo notifyWithdrawBtc;

    @NotNull
    private YesNo notifyWithdrawBank;

    @NotNull
    private YesNo trusted;

    private IsoCountriesDTO country;

    private FeeScheduleDTO feeSchedule;

    private CurrenciesDTO defaultCurrency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public YesNo getAuthyRequested() {
        return authyRequested;
    }

    public void setAuthyRequested(YesNo authyRequested) {
        this.authyRequested = authyRequested;
    }

    public YesNo getVerifiedAuthy() {
        return verifiedAuthy;
    }

    public void setVerifiedAuthy(YesNo verifiedAuthy) {
        this.verifiedAuthy = verifiedAuthy;
    }

    public Integer getAuthyId() {
        return authyId;
    }

    public void setAuthyId(Integer authyId) {
        this.authyId = authyId;
    }

    public YesNo getUsingSms() {
        return usingSms;
    }

    public void setUsingSms(YesNo usingSms) {
        this.usingSms = usingSms;
    }

    public YesNo getDontAsk30Days() {
        return dontAsk30Days;
    }

    public void setDontAsk30Days(YesNo dontAsk30Days) {
        this.dontAsk30Days = dontAsk30Days;
    }

    public Instant getDontAskDate() {
        return dontAskDate;
    }

    public void setDontAskDate(Instant dontAskDate) {
        this.dontAskDate = dontAskDate;
    }

    public YesNo getConfirmWithdrawalEmailBtc() {
        return confirmWithdrawalEmailBtc;
    }

    public void setConfirmWithdrawalEmailBtc(YesNo confirmWithdrawalEmailBtc) {
        this.confirmWithdrawalEmailBtc = confirmWithdrawalEmailBtc;
    }

    public YesNo getConfirmWithdrawal2faBtc() {
        return confirmWithdrawal2faBtc;
    }

    public void setConfirmWithdrawal2faBtc(YesNo confirmWithdrawal2faBtc) {
        this.confirmWithdrawal2faBtc = confirmWithdrawal2faBtc;
    }

    public YesNo getConfirmWithdrawal2faBank() {
        return confirmWithdrawal2faBank;
    }

    public void setConfirmWithdrawal2faBank(YesNo confirmWithdrawal2faBank) {
        this.confirmWithdrawal2faBank = confirmWithdrawal2faBank;
    }

    public YesNo getConfirmWithdrawalEmailBank() {
        return confirmWithdrawalEmailBank;
    }

    public void setConfirmWithdrawalEmailBank(YesNo confirmWithdrawalEmailBank) {
        this.confirmWithdrawalEmailBank = confirmWithdrawalEmailBank;
    }

    public YesNo getNotifyDepositBtc() {
        return notifyDepositBtc;
    }

    public void setNotifyDepositBtc(YesNo notifyDepositBtc) {
        this.notifyDepositBtc = notifyDepositBtc;
    }

    public YesNo getNotifyDepositBank() {
        return notifyDepositBank;
    }

    public void setNotifyDepositBank(YesNo notifyDepositBank) {
        this.notifyDepositBank = notifyDepositBank;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public YesNo getNoLogins() {
        return noLogins;
    }

    public void setNoLogins(YesNo noLogins) {
        this.noLogins = noLogins;
    }

    public YesNo getNotifyLogin() {
        return notifyLogin;
    }

    public void setNotifyLogin(YesNo notifyLogin) {
        this.notifyLogin = notifyLogin;
    }

    public YesNo getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(YesNo deactivated) {
        this.deactivated = deactivated;
    }

    public YesNo getLocked() {
        return locked;
    }

    public void setLocked(YesNo locked) {
        this.locked = locked;
    }

    public String getGoogle2faCode() {
        return google2faCode;
    }

    public void setGoogle2faCode(String google2faCode) {
        this.google2faCode = google2faCode;
    }

    public YesNo getVerifiedGoogle() {
        return verifiedGoogle;
    }

    public void setVerifiedGoogle(YesNo verifiedGoogle) {
        this.verifiedGoogle = verifiedGoogle;
    }

    public String getLastLang() {
        return lastLang;
    }

    public void setLastLang(String lastLang) {
        this.lastLang = lastLang;
    }

    public YesNo getNotifyWithdrawBtc() {
        return notifyWithdrawBtc;
    }

    public void setNotifyWithdrawBtc(YesNo notifyWithdrawBtc) {
        this.notifyWithdrawBtc = notifyWithdrawBtc;
    }

    public YesNo getNotifyWithdrawBank() {
        return notifyWithdrawBank;
    }

    public void setNotifyWithdrawBank(YesNo notifyWithdrawBank) {
        this.notifyWithdrawBank = notifyWithdrawBank;
    }

    public YesNo getTrusted() {
        return trusted;
    }

    public void setTrusted(YesNo trusted) {
        this.trusted = trusted;
    }

    public IsoCountriesDTO getCountry() {
        return country;
    }

    public void setCountry(IsoCountriesDTO country) {
        this.country = country;
    }

    public FeeScheduleDTO getFeeSchedule() {
        return feeSchedule;
    }

    public void setFeeSchedule(FeeScheduleDTO feeSchedule) {
        this.feeSchedule = feeSchedule;
    }

    public CurrenciesDTO getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(CurrenciesDTO defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SiteUsersDTO)) {
            return false;
        }

        SiteUsersDTO siteUsersDTO = (SiteUsersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, siteUsersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteUsersDTO{" +
            "id=" + getId() +
            ", pass='" + getPass() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", date='" + getDate() + "'" +
            ", tel='" + getTel() + "'" +
            ", user='" + getUser() + "'" +
            ", countryCode=" + getCountryCode() +
            ", authyRequested='" + getAuthyRequested() + "'" +
            ", verifiedAuthy='" + getVerifiedAuthy() + "'" +
            ", authyId=" + getAuthyId() +
            ", usingSms='" + getUsingSms() + "'" +
            ", dontAsk30Days='" + getDontAsk30Days() + "'" +
            ", dontAskDate='" + getDontAskDate() + "'" +
            ", confirmWithdrawalEmailBtc='" + getConfirmWithdrawalEmailBtc() + "'" +
            ", confirmWithdrawal2faBtc='" + getConfirmWithdrawal2faBtc() + "'" +
            ", confirmWithdrawal2faBank='" + getConfirmWithdrawal2faBank() + "'" +
            ", confirmWithdrawalEmailBank='" + getConfirmWithdrawalEmailBank() + "'" +
            ", notifyDepositBtc='" + getNotifyDepositBtc() + "'" +
            ", notifyDepositBank='" + getNotifyDepositBank() + "'" +
            ", lastUpdate='" + getLastUpdate() + "'" +
            ", noLogins='" + getNoLogins() + "'" +
            ", notifyLogin='" + getNotifyLogin() + "'" +
            ", deactivated='" + getDeactivated() + "'" +
            ", locked='" + getLocked() + "'" +
            ", google2faCode='" + getGoogle2faCode() + "'" +
            ", verifiedGoogle='" + getVerifiedGoogle() + "'" +
            ", lastLang='" + getLastLang() + "'" +
            ", notifyWithdrawBtc='" + getNotifyWithdrawBtc() + "'" +
            ", notifyWithdrawBank='" + getNotifyWithdrawBank() + "'" +
            ", trusted='" + getTrusted() + "'" +
            ", country=" + getCountry() +
            ", feeSchedule=" + getFeeSchedule() +
            ", defaultCurrency=" + getDefaultCurrency() +
            "}";
    }
}
