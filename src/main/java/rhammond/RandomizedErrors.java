package rhammond;

import java.util.Random;

import rx.Observable;

/**
 * Randomly generates errors for numbers divisible by 3 or any number between 0 and 100000 containing '13'.
 * Tests that a zipped observable fails on any error.
 */
public class RandomizedErrors {

    public static void main(String[] args) {

        Observable<Integer> intStream = Observable.just(new Random().nextInt(100000));

        Observable<String> errOnThirds = intStream.map(number -> {

            if (number % 3 == 0) {
                throw new RuntimeException(String.format("ERROR: %s is divisible by 3", number));
            }

            return String.format("%s is not divisible by 3", number);
        }).last();

        Observable<String> errOnUnlucky = intStream.map(number -> {

            if (number.toString().contains("13")) {
                throw new RuntimeException(String.format("ERROR: %s is unlucky", number));
            }

            return String.format("%s is not unlucky", number);
        }).last();

        Observable.zip(errOnThirds, errOnUnlucky, (s1, s2) -> "SUCCESS!\n" + s1 + "\n" +s2)
            .toBlocking()
            .subscribe(System.out::println, t -> System.out.println(t.getMessage()));
    }
}
