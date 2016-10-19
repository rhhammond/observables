/*
 * Copyright Homeaway, Inc 2015-Present. All Rights Reserved.
 * No unauthorized use of this software.
 */
package rhammond;

import rx.Observable;

/**
 * Shows how a blocking observable will throw an error on the main thread
 */
public class ThrowsAnError {
    public static void main(String[] args) {
        Observable err = Observable.error(new Exception("something went wrong"));
        err.toBlocking().first();
    }
}
