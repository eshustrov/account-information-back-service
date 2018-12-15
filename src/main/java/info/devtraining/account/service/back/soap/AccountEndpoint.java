package info.devtraining.account.service.back.soap;

import info.devtraining.account.service.back.db.AccountRepo;
import info.devtraining.account.service.back.db.data.AccountEntity;
import info.devtraining.account.service.back.soap.data.GetAccountBalanceRequest;
import info.devtraining.account.service.back.soap.data.GetAccountBalanceResponce;
import info.devtraining.account.service.back.soap.data.OpenAccountRequest;
import info.devtraining.account.service.back.soap.data.OpenAccountResponce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class AccountEndpoint {
    private static final String TNS = "http://info.devtraining/account";
    private final AccountRepo repo;

    @Autowired
    public AccountEndpoint(final AccountRepo repo) {
        this.repo = repo;
    }

    @PayloadRoot(namespace = TNS, localPart = "OpenAccountRequest")
    @ResponsePayload
    @SuppressWarnings("unused")
    public OpenAccountResponce openAccount(@RequestPayload final OpenAccountRequest request) {
        final AccountEntity account = repo.open(request.getCurrency());

        final OpenAccountResponce responce = new OpenAccountResponce();
        responce.setAccountNumber(account.number);
        return responce;
    }

    @PayloadRoot(namespace = TNS, localPart = "GetAccountBalanceRequest")
    @ResponsePayload
    @SuppressWarnings("unused")
    public GetAccountBalanceResponce getAccountBalance(@RequestPayload final GetAccountBalanceRequest request) {
        final AccountEntity account = repo.findByNumber(request.getAccountNumber());

        final GetAccountBalanceResponce responce = new GetAccountBalanceResponce();
        responce.setCurrency(account.currency);
        responce.setAmount(account.amount);
        return responce;
    }
}
