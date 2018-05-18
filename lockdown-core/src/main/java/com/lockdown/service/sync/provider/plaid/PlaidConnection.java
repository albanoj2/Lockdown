package com.lockdown.service.sync.provider.plaid;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.plaid.client.response.Account;
import com.plaid.client.response.TransactionsGetResponse.Transaction;

public interface PlaidConnection {
	public List<Account> getRemoteAccounts(String accessToken) throws IOException;
	public List<Transaction> getRemoteTransactions(String accessToken, Date start, Date end) throws IOException;
}
