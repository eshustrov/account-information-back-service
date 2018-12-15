package info.devtraining.account.service.back.db;

import info.devtraining.account.service.back.db.data.AccountEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.TextStringBuilder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class AccountRepo {
    private final ConcurrentMap<String, AccountEntity> data = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        final AccountEntity account = new AccountEntity();
        account.number = "001-000-0001";
        account.currency = "EUR";
        account.amount = new BigDecimal("123.45");

        data.put(account.number, account);
    }

    public AccountEntity open(final String currency) {
        final AccountEntity account = new AccountEntity();
        account.number = generateNumber();
        account.currency = currency;
        account.amount = BigDecimal.ZERO;

        data.put(account.number, account);
        return new AccountEntity(account);
    }

    public AccountEntity findByNumber(final String number) {
        if (number == null) {
            return null;
        }

        final AccountEntity account = data.get(number);
        if (account == null) {
            return null;
        }

        return new AccountEntity(account);
    }

    private String generateNumber() {
        for (; ; ) {
            final TextStringBuilder builder = new TextStringBuilder(12);
            final String number = builder.append(RandomStringUtils.randomNumeric(10))
                    .insert(3, '-')
                    .insert(7, '-')
                    .toString();
            if (!data.containsKey(number)) {
                return number;
            }
        }
    }
}
