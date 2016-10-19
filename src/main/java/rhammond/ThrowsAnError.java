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

        // stream will successfully emit '1'
        Observable<Integer> one = Observable.just(1);

        // stream will throw an error
        Observable err = Observable.error(new Exception("something went wrong"));

        // error is thrown on the the main thread
        Observable.zip(one, err, (num, o) -> null).toBlocking().first();
    }
}
