package com.lockdown.service.sync.provider.plaid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import com.lockdown.domain.Account;
import com.lockdown.domain.Money;
import com.lockdown.domain.Transaction;

public class PlaidConverter {

	public static Account toAccount(com.plaid.client.response.Account input) {
		return new Account(
			null, 
			input.getAccountId(), 
			input.getName(), 
			input.getType(), 
			input.getSubtype(), 
			new ArrayList<>()
		);
	}
	
	public static Transaction toTransaction(com.plaid.client.response.TransactionsGetResponse.Transaction input) {
		return new Transaction(
			null, 
			LocalDate.parse(input.getDate(), DateTimeFormatter.ISO_LOCAL_DATE), 
			Money.fractionalDollars(input.getAmount()), 
			input.getTransactionId(), 
			input.getName(), 
			input.getOriginalDescription(), 
			input.getPending(), 
			Optional.empty()
		);
	}
}
