package cn.infinitumstudios.infinitumEconomy.utility;

/**
 * This Enum shows the status of Infinitum EconomyImplementer database operations.
 */

public enum ResponseStatus {

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
