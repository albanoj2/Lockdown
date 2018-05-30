package com.lockdown.service.sync.provider.plaid;

import com.lockdown.domain.Account.Type;

public final class PlaidTypeMapper {

	public static Type toType(String typeName) {
		switch (typeName) {
			case "brokerage": 	return Type.BROKERAGE;
			case "credit":		return Type.CREDIT;
			case "depository":	return Type.DEPOSITORY;
			case "loan":		return Type.LOAN;
			case "mortgage":	return Type.MORTGAGE;
			case "other":		return Type.OTHER;
			default:			return Type.UNKNOWN;
		}
	}
}
