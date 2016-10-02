package rhammond;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Emits a random int to two delayed streams that transform the value by doubling it and halving it.
 * The streams are merged and then the program blocks until both streams emit their values.  The point is to illustrate
 * that the values are emitted non-deterministically because the delays are random on each stream.
 */
public class IntMapper
{
    public static void main( String[] args )
    {

        final long start = new Date().getTime();

        Observable<Integer> intStream = Observable.just(new Random().nextInt(1000));

        Observable<String> doubler = intStream
            .delay(new Random().nextInt(1000), TimeUnit.MILLISECONDS)
            .timestamp()
            .map(t -> String.format("%s DOUBLED is %s, elapsed: %sms",
                t.getValue(), t.getValue() * 2, t.getTimestampMillis() - start))
            .last();

        Observable<String> halfer = intStream
            .delay(new Random().nextInt(1000), TimeUnit.MILLISECONDS)
            .timestamp()
            .map(t -> String.format("%s HALVED is %s, elapsed: %sms",
                t.getValue(), t.getValue() / 2, t.getTimestampMillis() - start))
            .last();

        doubler.mergeWith(halfer).toBlocking().subscribe(System.out::println);
    }
}
