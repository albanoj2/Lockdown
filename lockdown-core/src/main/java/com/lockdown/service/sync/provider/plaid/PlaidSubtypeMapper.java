package com.lockdown.service.sync.provider.plaid;

import com.lockdown.domain.Account.Subtype;

public final class PlaidSubtypeMapper {

	public static Subtype toSubtype(String subtypeName) {
		switch (subtypeName) {
			case "401k":			return Subtype._401K;
			case "brokerage":		return Subtype.BROKERAGE;
			case "ira":				return Subtype.IRA;
			case "retirement":		return Subtype.RETIREMENT;
			case "roth":			return Subtype.ROTH;
			case "ugma":			return Subtype.UGMA;
			case "credit card":		return Subtype.CREDIT_CARD;
			case "paypal":			return Subtype.PAYPAL;
			case "line of credit":	return Subtype.LINE_OF_CREDIT;
			case "rewards":			return Subtype.REWARDS;
			case "cd":				return Subtype.CD;
			case "checking":		return Subtype.CHECKING;
			case "savings":			return Subtype.SAVINGS;
			case "money market":	return Subtype.MONEY_MARKET;
			case "prepaid":			return Subtype.PREPAID;
			case "auto":			return Subtype.AUTO;
			case "commercial":		return Subtype.COMMERCIAL;
			case "construction":	return Subtype.CONSTRUCTION;
			case "consumer":		return Subtype.CONSUMER;
			case "home":			return Subtype.HOME;
			case "home equity":		return Subtype.HOME_EQUITY;
			case "overdraft":		return Subtype.OVERDRAFT;
			case "student":			return Subtype.STUDENT;
			case "403B":			return Subtype._403B;
			case "cash management":	return Subtype.CASH_MANAGEMENT;
			case "hsa":				return Subtype.HSA;
			case "keogh":			return Subtype.KOEGH;
			case "mutual fund":		return Subtype.MUTUAL_FUND;
			case "recurring":		return Subtype.RECURRING;
			case "safe deposit":	return Subtype.SAFE_DEPOSIT;
			case "sarsep":			return Subtype.SARSEP;
			case "other":			return Subtype.OTHER;
			default:				return Subtype.UNKNOWN;
			
		}
	}
	

	
//	brokerage	401k, brokerage, ira, retirement, roth, ugma
//	credit	credit card, paypal, line of credit, rewards
//	depository	cd, checking, savings, money market, paypal, prepaid
//	loan	auto, commercial, construction, consumer, home, home equity, loan, mortgage, overdraft, line of credit, student
//	mortgage	home
//	other	403B, cash management, cd, hsa, keogh, money market, mutual fund, prepaid, recurring, rewards, safe deposit, sarsep, other
}
