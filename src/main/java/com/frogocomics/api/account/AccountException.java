package com.frogocomics.api.account;

/**
 * The exception <code>AccountException</code> is used to
 * signify something is wrong with a player's account.
 *
 * @see java.lang.Exception
 */
public class AccountException extends Exception {

    public AccountException(String message) {
        super(message);
    }
}
