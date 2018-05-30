package com.lockdown.service.sync.provider.plaid;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.plaid.client.response.AccountsGetResponse;
import com.plaid.client.response.TransactionsGetResponse.Transaction;

public interface PlaidConnection {
	public AccountsGetResponse getRemoteAccounts(String accessToken) throws IOException;
	public List<Transaction> getRemoteTransactions(String accessToken, Date start, Date end) throws IOException;
}
