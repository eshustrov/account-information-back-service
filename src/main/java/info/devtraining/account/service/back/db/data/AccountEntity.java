package info.devtraining.account.service.back.db.data;

import java.math.BigDecimal;

public class AccountEntity {
    public String number;
    public String currency;
    public BigDecimal amount;

    public AccountEntity() {
    }

    public AccountEntity(final AccountEntity account) {
        number = account.number;
        currency = account.currency;
        amount = account.amount;
    }
}
