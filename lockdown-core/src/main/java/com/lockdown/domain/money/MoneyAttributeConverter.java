package com.lockdown.domain.money;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MoneyAttributeConverter implements AttributeConverter<Money, Long> {

	@Override
	public Long convertToDatabaseColumn(Money money) {
		return money.getCents();
	}

	@Override
	public Money convertToEntityAttribute(Long cents) {
		return Money.cents(cents);
	}

}
