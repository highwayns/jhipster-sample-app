package io.github.jhipster.sample.domain;

import io.github.jhipster.sample.domain.enumeration.YesNo;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SiteUsers.
 */
@Entity
@Table(name = "site_users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SiteUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "pass", length = 200, nullable = false)
    private String pass;

    @NotNull
    @Size(max = 200)
    @Column(name = "first_name", length = 200, nullable = false)
    private String firstName;

    @NotNull
    @Size(max = 200)
    @Column(name = "last_name", length = 200, nullable = false)
    private String lastName;

    @NotNull
    @Size(max = 255)
    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @NotNull
    @Size(max = 255)
    @Column(name = "tel", length = 255, nullable = false)
    private String tel;

    @NotNull
    @Size(max = 255)
    @Column(name = "jhi_user", length = 255, nullable = false)
    private String user;

    @NotNull
    @Column(name = "country_code", nullable = false)
    private Integer countryCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "authy_requested", nullable = false)
    private YesNo authyRequested;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "verified_authy", nullable = false)
    private YesNo verifiedAuthy;

    @NotNull
    @Column(name = "authy_id", nullable = false)
    private Integer authyId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "using_sms", nullable = false)
    private YesNo usingSms;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "dont_ask_30_days", nullable = false)
    private YesNo dontAsk30Days;

    @NotNull
    @Column(name = "dont_ask_date", nullable = false)
    private Instant dontAskDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "confirm_withdrawal_email_btc", nullable = false)
    private YesNo confirmWithdrawalEmailBtc;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "confirm_withdrawal_2_fa_btc", nullable = false)
    private YesNo confirmWithdrawal2faBtc;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "confirm_withdrawal_2_fa_bank", nullable = false)
    private YesNo confirmWithdrawal2faBank;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "confirm_withdrawal_email_bank", nullable = false)
    private YesNo confirmWithdrawalEmailBank;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "notify_deposit_btc", nullable = false)
    private YesNo notifyDepositBtc;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "notify_deposit_bank", nullable = false)
    private YesNo notifyDepositBank;

    @NotNull
    @Column(name = "last_update", nullable = false)
    private Instant lastUpdate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "no_logins", nullable = false)
    private YesNo noLogins;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "notify_login", nullable = false)
    private YesNo notifyLogin;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "deactivated", nullable = false)
    private YesNo deactivated;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "locked", nullable = false)
    private YesNo locked;

    @NotNull
    @Size(max = 255)
    @Column(name = "google_2_fa_code", length = 255, nullable = false)
    private String google2faCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "verified_google", nullable = false)
    private YesNo verifiedGoogle;

    @NotNull
    @Size(max = 255)
    @Column(name = "last_lang", length = 255, nullable = false)
    private String lastLang;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "notify_withdraw_btc", nullable = false)
    private YesNo notifyWithdrawBtc;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "notify_withdraw_bank", nullable = false)
    private YesNo notifyWithdrawBank;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "trusted", nullable = false)
    private YesNo trusted;

    @OneToOne
    @JoinColumn(unique = true)
    private IsoCountries country;

    @OneToOne
    @JoinColumn(unique = true)
    private FeeSchedule feeSchedule;

    @OneToOne
    @JoinColumn(unique = true)
    private Currencies defaultCurrency;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SiteUsers id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPass() {
        return this.pass;
    }

    public SiteUsers pass(String pass) {
        this.setPass(pass);
        return this;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public SiteUsers firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public SiteUsers lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public SiteUsers email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getDate() {
        return this.date;
    }

    public SiteUsers date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getTel() {
        return this.tel;
    }

    public SiteUsers tel(String tel) {
        this.setTel(tel);
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUser() {
        return this.user;
    }

    public SiteUsers user(String user) {
        this.setUser(user);
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getCountryCode() {
        return this.countryCode;
    }

    public SiteUsers countryCode(Integer countryCode) {
        this.setCountryCode(countryCode);
        return this;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public YesNo getAuthyRequested() {
        return this.authyRequested;
    }

    public SiteUsers authyRequested(YesNo authyRequested) {
        this.setAuthyRequested(authyRequested);
        return this;
    }

    public void setAuthyRequested(YesNo authyRequested) {
        this.authyRequested = authyRequested;
    }

    public YesNo getVerifiedAuthy() {
        return this.verifiedAuthy;
    }

    public SiteUsers verifiedAuthy(YesNo verifiedAuthy) {
        this.setVerifiedAuthy(verifiedAuthy);
        return this;
    }

    public void setVerifiedAuthy(YesNo verifiedAuthy) {
        this.verifiedAuthy = verifiedAuthy;
    }

    public Integer getAuthyId() {
        return this.authyId;
    }

    public SiteUsers authyId(Integer authyId) {
        this.setAuthyId(authyId);
        return this;
    }

    public void setAuthyId(Integer authyId) {
        this.authyId = authyId;
    }

    public YesNo getUsingSms() {
        return this.usingSms;
    }

    public SiteUsers usingSms(YesNo usingSms) {
        this.setUsingSms(usingSms);
        return this;
    }

    public void setUsingSms(YesNo usingSms) {
        this.usingSms = usingSms;
    }

    public YesNo getDontAsk30Days() {
        return this.dontAsk30Days;
    }

    public SiteUsers dontAsk30Days(YesNo dontAsk30Days) {
        this.setDontAsk30Days(dontAsk30Days);
        return this;
    }

    public void setDontAsk30Days(YesNo dontAsk30Days) {
        this.dontAsk30Days = dontAsk30Days;
    }

    public Instant getDontAskDate() {
        return this.dontAskDate;
    }

    public SiteUsers dontAskDate(Instant dontAskDate) {
        this.setDontAskDate(dontAskDate);
        return this;
    }

    public void setDontAskDate(Instant dontAskDate) {
        this.dontAskDate = dontAskDate;
    }

    public YesNo getConfirmWithdrawalEmailBtc() {
        return this.confirmWithdrawalEmailBtc;
    }

    public SiteUsers confirmWithdrawalEmailBtc(YesNo confirmWithdrawalEmailBtc) {
        this.setConfirmWithdrawalEmailBtc(confirmWithdrawalEmailBtc);
        return this;
    }

    public void setConfirmWithdrawalEmailBtc(YesNo confirmWithdrawalEmailBtc) {
        this.confirmWithdrawalEmailBtc = confirmWithdrawalEmailBtc;
    }

    public YesNo getConfirmWithdrawal2faBtc() {
        return this.confirmWithdrawal2faBtc;
    }

    public SiteUsers confirmWithdrawal2faBtc(YesNo confirmWithdrawal2faBtc) {
        this.setConfirmWithdrawal2faBtc(confirmWithdrawal2faBtc);
        return this;
    }

    public void setConfirmWithdrawal2faBtc(YesNo confirmWithdrawal2faBtc) {
        this.confirmWithdrawal2faBtc = confirmWithdrawal2faBtc;
    }

    public YesNo getConfirmWithdrawal2faBank() {
        return this.confirmWithdrawal2faBank;
    }

    public SiteUsers confirmWithdrawal2faBank(YesNo confirmWithdrawal2faBank) {
        this.setConfirmWithdrawal2faBank(confirmWithdrawal2faBank);
        return this;
    }

    public void setConfirmWithdrawal2faBank(YesNo confirmWithdrawal2faBank) {
        this.confirmWithdrawal2faBank = confirmWithdrawal2faBank;
    }

    public YesNo getConfirmWithdrawalEmailBank() {
        return this.confirmWithdrawalEmailBank;
    }

    public SiteUsers confirmWithdrawalEmailBank(YesNo confirmWithdrawalEmailBank) {
        this.setConfirmWithdrawalEmailBank(confirmWithdrawalEmailBank);
        return this;
    }

    public void setConfirmWithdrawalEmailBank(YesNo confirmWithdrawalEmailBank) {
        this.confirmWithdrawalEmailBank = confirmWithdrawalEmailBank;
    }

    public YesNo getNotifyDepositBtc() {
        return this.notifyDepositBtc;
    }

    public SiteUsers notifyDepositBtc(YesNo notifyDepositBtc) {
        this.setNotifyDepositBtc(notifyDepositBtc);
        return this;
    }

    public void setNotifyDepositBtc(YesNo notifyDepositBtc) {
        this.notifyDepositBtc = notifyDepositBtc;
    }

    public YesNo getNotifyDepositBank() {
        return this.notifyDepositBank;
    }

    public SiteUsers notifyDepositBank(YesNo notifyDepositBank) {
        this.setNotifyDepositBank(notifyDepositBank);
        return this;
    }

    public void setNotifyDepositBank(YesNo notifyDepositBank) {
        this.notifyDepositBank = notifyDepositBank;
    }

    public Instant getLastUpdate() {
        return this.lastUpdate;
    }

    public SiteUsers lastUpdate(Instant lastUpdate) {
        this.setLastUpdate(lastUpdate);
        return this;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public YesNo getNoLogins() {
        return this.noLogins;
    }

    public SiteUsers noLogins(YesNo noLogins) {
        this.setNoLogins(noLogins);
        return this;
    }

    public void setNoLogins(YesNo noLogins) {
        this.noLogins = noLogins;
    }

    public YesNo getNotifyLogin() {
        return this.notifyLogin;
    }

    public SiteUsers notifyLogin(YesNo notifyLogin) {
        this.setNotifyLogin(notifyLogin);
        return this;
    }

    public void setNotifyLogin(YesNo notifyLogin) {
        this.notifyLogin = notifyLogin;
    }

    public YesNo getDeactivated() {
        return this.deactivated;
    }

    public SiteUsers deactivated(YesNo deactivated) {
        this.setDeactivated(deactivated);
        return this;
    }

    public void setDeactivated(YesNo deactivated) {
        this.deactivated = deactivated;
    }

    public YesNo getLocked() {
        return this.locked;
    }

    public SiteUsers locked(YesNo locked) {
        this.setLocked(locked);
        return this;
    }

    public void setLocked(YesNo locked) {
        this.locked = locked;
    }

    public String getGoogle2faCode() {
        return this.google2faCode;
    }

    public SiteUsers google2faCode(String google2faCode) {
        this.setGoogle2faCode(google2faCode);
        return this;
    }

    public void setGoogle2faCode(String google2faCode) {
        this.google2faCode = google2faCode;
    }

    public YesNo getVerifiedGoogle() {
        return this.verifiedGoogle;
    }

    public SiteUsers verifiedGoogle(YesNo verifiedGoogle) {
        this.setVerifiedGoogle(verifiedGoogle);
        return this;
    }

    public void setVerifiedGoogle(YesNo verifiedGoogle) {
        this.verifiedGoogle = verifiedGoogle;
    }

    public String getLastLang() {
        return this.lastLang;
    }

    public SiteUsers lastLang(String lastLang) {
        this.setLastLang(lastLang);
        return this;
    }

    public void setLastLang(String lastLang) {
        this.lastLang = lastLang;
    }

    public YesNo getNotifyWithdrawBtc() {
        return this.notifyWithdrawBtc;
    }

    public SiteUsers notifyWithdrawBtc(YesNo notifyWithdrawBtc) {
        this.setNotifyWithdrawBtc(notifyWithdrawBtc);
        return this;
    }

    public void setNotifyWithdrawBtc(YesNo notifyWithdrawBtc) {
        this.notifyWithdrawBtc = notifyWithdrawBtc;
    }

    public YesNo getNotifyWithdrawBank() {
        return this.notifyWithdrawBank;
    }

    public SiteUsers notifyWithdrawBank(YesNo notifyWithdrawBank) {
        this.setNotifyWithdrawBank(notifyWithdrawBank);
        return this;
    }

    public void setNotifyWithdrawBank(YesNo notifyWithdrawBank) {
        this.notifyWithdrawBank = notifyWithdrawBank;
    }

    public YesNo getTrusted() {
        return this.trusted;
    }

    public SiteUsers trusted(YesNo trusted) {
        this.setTrusted(trusted);
        return this;
    }

    public void setTrusted(YesNo trusted) {
        this.trusted = trusted;
    }

    public IsoCountries getCountry() {
        return this.country;
    }

    public void setCountry(IsoCountries isoCountries) {
        this.country = isoCountries;
    }

    public SiteUsers country(IsoCountries isoCountries) {
        this.setCountry(isoCountries);
        return this;
    }

    public FeeSchedule getFeeSchedule() {
        return this.feeSchedule;
    }

    public void setFeeSchedule(FeeSchedule feeSchedule) {
        this.feeSchedule = feeSchedule;
    }

    public SiteUsers feeSchedule(FeeSchedule feeSchedule) {
        this.setFeeSchedule(feeSchedule);
        return this;
    }

    public Currencies getDefaultCurrency() {
        return this.defaultCurrency;
    }

    public void setDefaultCurrency(Currencies currencies) {
        this.defaultCurrency = currencies;
    }

    public SiteUsers defaultCurrency(Currencies currencies) {
        this.setDefaultCurrency(currencies);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SiteUsers)) {
            return false;
        }
        return id != null && id.equals(((SiteUsers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteUsers{" +
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
            "}";
    }
}
