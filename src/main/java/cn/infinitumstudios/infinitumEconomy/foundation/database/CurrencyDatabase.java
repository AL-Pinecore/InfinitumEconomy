package cn.infinitumstudios.infinitumEconomy.foundation.database;

import cn.infinitumstudios.infinitumEconomy.foundation.Currency;
import cn.infinitumstudios.infinitumEconomy.foundation.types.Account;
import cn.infinitumstudios.infinitumEconomy.utility.Reference;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

/**
 * @implNote The currency database is a wrapper of the {@link Database} class for {@link Currency} related operations. See {@link Database} implementation for more info. Also, do keep in mind that the Default currency is not included in the database to prevent human-made errors.
 * @apiNote Before you use any methods from this class, please *read the documentations* of each method in this class.
 */
public class CurrencyDatabase extends Database<Currency> {
    public static Currency DEFAULT_CURRENCY;

    public CurrencyDatabase() {
        super(Reference.ACCOUNT_DATABASE_NAME, Currency.class, new File(Path.of(Reference.DATA_FILES_DIRECTORY.toString(), Reference.CURRENCY_DATABASE_NAME + ".json").toUri()));
        load();

        Optional<Currency> temp = read(currency -> currency.getName().equals("Fractal") && currency.getPluralName().equals("Fractals"));

        if(temp.isEmpty()) { // If the returned query result for the default currency returns nothing, create the default currency and save
            DEFAULT_CURRENCY = new Currency("Fractal", "Fractals", "Σ");
            create(DEFAULT_CURRENCY);
            save();
        } else // If default currency exists, set default currency
            DEFAULT_CURRENCY = temp.get();
    }

    static { // Make sure the DEFAULT_CURRENCY is not null when referenced
        new CurrencyDatabase().load();
    }
}
