package com.lockdown.service.sync.provider.plaid;

import com.lockdown.domain.Institution;

public final class PlaidInstitutionMapper {

	public static Institution toInstitution(String institutionId) {
		switch (institutionId) {
			case "ins_25": 		return Institution.ALLY_BANK;
			case "ins_10": 		return Institution.AMERICAN_EXPRESS;
			case "ins_100529": 	return Institution.AMERICAN_EXPRESS_HEALTHEQUITY_LOGIN;
			case "ins_100530": 	return Institution.AMERICAN_EXPRESS_PERSONAL_SAVINGS;
			case "ins_1": 		return Institution.BANK_OF_AMERICA;
			case "ins_100866": 	return Institution.BANK_OF_AMERICA_CHARITABLE_GIFT_FUND;
			case "ins_2": 		return Institution.BB_AND_T;
			case "ins_100771": 	return Institution.BB_AND_T_INVESTING_AND_RETIREMENT_MARKETCONNET;
			case "ins_100775": 	return Institution.BB_AND_T_WEALTH_MANAGEMENT_TAMLINK;
			case "ins_23": 		return Institution.BBVA_COMPASS;
			case "ins_110731": 	return Institution.BBVA_COMPASS_CLEARSPEND_VISA_CARD;
			case "ins_100777": 	return Institution.BBVA_COMPASS_CREDIT_ONLINE;
			case "ins_9": 		return Institution.CAPITAL_ONE;
			case "ins_11": 		return Institution.CHARLES_SCHWAB;
			case "ins_101686": 	return Institution.CHARLES_SCHWAB_LEARNING_QUEST_529_PLAN;
			case "ins_3": 		return Institution.CHASE;
			case "ins_5": 		return Institution.CITI;
			case "ins_20": 		return Institution.CITIZENS_BANK;
			case "ins_101804": 	return Institution.CITIZENS_BANK_CREDIT_CARD;
			case "ins_101805": 	return Institution.CITIZENS_BANK_INVESTMENTS;
			case "ins_101806":	return Institution.CITI_PERSONAL_WEALTH_MANAGEMENT;
			case "ins_12": 		return Institution.FIDELITY;
			case "ins_102914": 	return Institution.FIDELITY_401K;
			case "ins_102913": 	return Institution.FIDELITY_CHARITABLE_GIFT_FUND;
			case "ins_102937": 	return Institution.FIDELITY_FIDELITY_DOT_COM_AT_WORK;
			case "ins_26": 		return Institution.FIFTH_THIRD_BANK;
			case "ins_102942": 	return Institution.FIFTH_THIRD_BANK_HSA;
			case "ins_102943": 	return Institution.FIFTH_THIRD_BANK_PRIVATE_BANK;
			case "ins_21": 		return Institution.HUNTINGTON_BANK;
			case "ins_29": 		return Institution.KEY_BANK;
			case "ins_27": 		return Institution.M_AND_T_BANK;
			case "ins_105145": 	return Institution.M_AND_T_BANK_WEB_BANKING_FOR_BUSINESS;
			case "ins_15": 		return Institution.NAVY_FEDERAL_CREDIT_UNION;
			case "ins_22": 		return Institution.PAYPAL;
			case "ins_13": 		return Institution.PNC;
			case "ins_106258": 	return Institution.PNC_I_LINK;
			case "ins_106260": 	return Institution.PNC_POINTS_CREDIT_CARD;
			case "ins_106256": 	return Institution.PNC_SMALL_BUSINESS;
			case "ins_19": 		return Institution.REGIONS_BANK;
			case "ins_106891": 	return Institution.REGIONS_BANK_TRUST_ONLINE;
			case "ins_113800": 	return Institution.SAN_FRANCISCO_FIRE_CREDIT_UNION;
			case "ins_28": 		return Institution.SANTANDER;
			case "ins_24": 		return Institution.SIMPLE;
			case "ins_16": 		return Institution.SUN_TRUST;
			case "ins_110878": 	return Institution.SUN_TRUST_ONLINE_CASH_MANAGER;
			case "ins_107760": 	return Institution.SUN_TRUST_SMALL_BUSINESS;
			case "ins_14": 		return Institution.TD_BANK;
			case "ins_107836": 	return Institution.TD_BANK_BUSINESS_DIRECT;
			case "ins_7": 		return Institution.USAA;
			case "ins_4": 		return Institution.WELLS_FARGO;
			case "ins_114007": 	return Institution.WELLS_FARGO_COMMERCIAL_ELECTRONICS_OFFICE;
			case "ins_108968": 	return Institution.WELLS_FARGO_DEALER_SERVICES;
			case "ins_108970": 	return Institution.WELLS_FARGO_RETIREMENT_PLAN;
			case "ins_101776": 	return Institution.CITI_PERSONAL_WEALTH_MANAGEMENT;
			case "ins_33": 		return Institution.DISCOVER_CARD;
			case "ins_32": 		return Institution.WOODFOREST_NATIONAL_BANK;
			case "ins_114000":	return Institution.NAVIENT_LOANS;
			case "ins_101158": 	return Institution.BEST_BUY_CREDIT_CARD;
			case "ins_104063": 	return Institution.HARRIS_RETIREMENT_BENEFITS;
			case "ins_109357": 	return Institution.MACYS_CREDIT_CARD;
			case "ins_100015": 	return Institution.BOEING_EMPLOYEES_CREDIT_UNION;
			case "ins_104878": 	return Institution.KOHLS_CHARGE_CREDIT_CARD;
			case "ins_102903": 	return Institution.FED_LOAN_SERVICING;
			case "ins_100092": 	return Institution.GREAK_LAKES_EDUCATIONAL_LOAN_SERVICE;
			case "ins_101037": 	return Institution.BANK_MOBILE_VIBE;
			case "ins_105849": 	return Institution.NELNET_EDUCATION_FINANCING;
			case "ins_36": 		return Institution.STATE_EMPLOYEES_CREDIT_UNION;
			case "ins_110938": 	return Institution.FIRST_CONVENIENCE_BANK;
			case "ins_100071": 	return Institution.TARGET_CREDIT_CARD;
			case "ins_120012": 	return Institution.BANK_OF_THE_WEST;
			case "ins_101020": 	return Institution.BANK_OF_THE_WEST_PORTFOLIO_ONLINE;
			case "ins_100952": 	return Institution.BMO_BANK_OF_MONTREAL_INVESTOR_LINE;
			case "ins_100096": 	return Institution.E_TRADE;
			case "ins_31": 		return Institution.UNION_BANK;
			case "ins_108809": 	return Institution.VICTORIA_SECRET_CREDIT;
			case "ins_107252": 	return Institution.SEARS_CARD;
			case "ins_120013": 	return Institution.AMERICA_FIRST_CREDIT_UNION;
			case "ins_120000": 	return Institution.GOLDEN_1_CREDIT_UNION;
			case "ins_111968": 	return Institution.TCF_BANK_ILLINOIS;
			case "ins_114016": 	return Institution.FIRST_TENNESSEE_BANK_PERSONAL;
			case "ins_108768": 	return Institution.VANGUARD;
			case "ins_111972": 	return Institution.ARVEST_BANK;
			case "ins_100677": 	return Institution.ARVEST_BANK_ASSET_MANAGEMENT_CLIENT_POINT;
			case "ins_100679": 	return Institution.ARVEST_BANK_MORTGAGE;
			case "ins_100074": 	return Institution.RANDOLPH_BROOKS_FEDERAL_CREDIT_UNION;
			case "ins_100523": 	return Institution.AMERICAN_EDUCATION_SERVICES;
			case "ins_107889": 	return Institution.TARGET_GUEST_CARD;
			case "ins_105483": 	return Institution.MICHIGAN_STATE_UNIVERSITY_FEDERAL_CREDIT_UNION;
			case "ins_101327": 	return Institution.CARMAX;
			case "ins_110670": 	return Institution.FIRST_PREMIER_BANK_PERSONAL;
			case "ins_100798": 	return Institution.BMO_RETIREMENT_SERVICES;
			case "ins_100075": 	return Institution.MERRICK_BANK_CREDIT_CARD;
			case "ins_102625": 	return Institution.EDWARD_JONES_CREDIT_CARD;
			case "ins_102626": 	return Institution.EDWARD_JONES_US_CLIENTS_ACCESS;
			case "ins_100065": 	return Institution.DISCOVER_BANK;
			case "ins_101391": 	return Institution.CABELAS_CLUB_VISA;
			case "ins_100052": 	return Institution.MOUNTAIN_AMERICAN_CREDIT_UNION;
			case "ins_107233": 	return Institution.SCOTTRADE;
			case "ins_113571": 	return Institution.ZIONS_BANK_PERSONAL;
			case "ins_110622": 	return Institution.HSBC_US_PERSONAL_INTERNET_BANKING;
			default: 			return Institution.UNKNOWN;
		}
	}
}