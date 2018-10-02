package com.neovisionaries.i18n;

import java.util.Locale;

/**
 * The region interface is implemented by {@link CountryCode}, but other implementations could exist also.
 *
 * @author Michiel Meeuwissen
 * @since 1.24
 */
public interface Region
{
	String getName();

	Locale toLocale();

	Type getType();

	enum Type {
		/**
		 * A country or former country
		 */
		COUNTRY,

		/**
		 * A subdivision of a country, of which the type is otherwise unknown
		 */
		SUBDIVISION,

		PROVINCE,
		STATE,
		PARISH,
		EMIRATE,
		DEPENDENCY,
		CITY,
		REGION,
		TERRITORY,
		MUNICIPALITY,
		SPECIAL_MUNICIPALITY,
		RAYON,
		ENTITY,
		DIVISION,
		DISTRICT,
		GOVERNORATE,
		DEPARTMENT,
		FEDERAL_DISTRICT,
		TOWN,
		OBLAST,
		COMMUNUNE,
		PREFECTURE,
		CANTON,
		LAND,
		ISLAND,
		NATION
	}



}
