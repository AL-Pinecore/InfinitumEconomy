package cn.infinitumstudios.infinitumEconomy.foundation.database;

import cn.infinitumstudios.infinitumEconomy.foundation.Currency;
import cn.infinitumstudios.infinitumEconomy.foundation.types.Account;
import cn.infinitumstudios.infinitumEconomy.utility.Reference;

import java.io.File;
import java.nio.file.Path;

/**
 * @implNote The currency database is a wrapper of the {@link Database} class for {@link Currency} related operations. See {@link Database} implementation for more info. Also, do keep in mind that the Default currency is not included in the database to prevent human-made errors.
 * @apiNote Before you use any methods from this class, please *read the documentations* of each method in this class.
 */
public class CurrencyDatabase extends Database<Currency> {
    public CurrencyDatabase() {
        super(Reference.ACCOUNT_DATABASE_NAME, Currency.class, new File(Path.of(Reference.DATA_FILES_DIRECTORY.toString(), Reference.CURRENCY_DATABASE_NAME + ".json").toUri()));
    }
}
