package cn.infinitumstudios.infinitumEconomy.foundation.database.sql;

/**
 * @implNote This Enum shows the status of Infinitum Economy SQLite database operations.
 */

public enum Status {

    /**
     * Operation done successfully.
     */
    SUCCESS,

    /**
     * Target object or data fields is not founded in the database.
     */
    NOTFOUND,

    /**
     * Operation failed, this can be caused by internal issue, or the input data is null.
     */
    FAILED,

    /**
     * Target object or data fields is already existed in the database.
     */
    EXISTED
}
