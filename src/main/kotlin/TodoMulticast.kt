import io.reactivex.Observable
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    multicast()
}

fun multicast() {
    val threeRandom =
            Observable.range(1, 3)
                    .map { ThreadLocalRandom.current().nextInt(100000) }
                    .publish()
    threeRandom.subscribe(
            { System.out.println("Observable1:$it") },
            { System.out.println("error") },
            { System.out.println("complete") })
    threeRandom.subscribe(
            { System.out.println("Observable2:$it") },
            { System.out.println("error") },
            { System.out.println("complete") })

    threeRandom.connect()
}

fun autoConnect() {
    val threeRandom =
            Observable.range(1, 3)
                    .map { ThreadLocalRandom.current().nextInt(100000) }
                    .publish()
                    .autoConnect(2)
    threeRandom.subscribe(
            { System.out.println("Observable1:$it") },
            { System.out.println("error") },
            { System.out.println("complete") })

    threeRandom.reduce(0) { t1, t2 -> t1 + t2 }
            .subscribe(
                    { System.out.println("Observable2:$it") },
                    { System.out.println("error") })
}

fun refCount() {
    val seconds =
            Observable.interval(1, TimeUnit.SECONDS)
                    .publish()
                    .refCount()

    seconds.take(5)
            .subscribe(
                    { System.out.println("Observable1:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
    sleep(3000)
    seconds.take(2)
            .subscribe(
                    { System.out.println("Observable2:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
    sleep(3000)
    seconds.subscribe(
            { System.out.println("Observable3:$it") },
            { System.out.println("error") },
            { System.out.println("complete") })
    sleep(3000)
}

fun share() {
    val seconds =
            Observable.interval(1, TimeUnit.SECONDS)
                    .share()

    seconds.take(5)
            .subscribe(
                    { System.out.println("Observable1:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
    sleep(3000)
    seconds.take(2)
            .subscribe(
                    { System.out.println("Observable2:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
    sleep(3000)
    seconds.subscribe(
            { System.out.println("Observable3:$it") },
            { System.out.println("error") },
            { System.out.println("complete") })
    sleep(3000)
}

fun reply() {
    val seconds =
            Observable.interval(1, TimeUnit.SECONDS)
                    .replay()
                    .autoConnect()
    seconds.subscribe(
            { System.out.println("Observable1:$it") },
            { System.out.println("error") },
            { System.out.println("complete") })
    sleep(3000)
    seconds.subscribe(
            { System.out.println("Observable2:$it") },
            { System.out.println("error") },
            { System.out.println("complete") })
    sleep(3000)
}

fun replyWithBuffer() {
    val seconds =
            Observable.interval(1, TimeUnit.SECONDS)
                    .replay(2)
                    .autoConnect()
    seconds.subscribe(
            { System.out.println("Observable1:$it") },
            { System.out.println("error") },
            { System.out.println("complete") })
    sleep(3000)
    seconds.subscribe(
            { System.out.println("Observable2:$it") },
            { System.out.println("error") },
            { System.out.println("complete") })
    sleep(3000)
}

fun replyWithTime() {
    val seconds =
            Observable.interval(300, TimeUnit.MILLISECONDS)
                    .replay(1, TimeUnit.SECONDS)
                    .autoConnect()
    seconds.subscribe(
            { System.out.println("Observable1:$it") },
            { System.out.println("error") },
            { System.out.println("complete") })
    sleep(3000)
    seconds.subscribe(
            { System.out.println("Observable2:$it") },
            { System.out.println("error") },
            { System.out.println("complete") })
    sleep(3000)
}

fun cache() {
    val source = Observable.just(1, 2, 3, 4, 5, 6)
            .scan(0) { t1: Int, t2: Int ->
                t1 + t2
            }
            .cacheWithInitialCapacity(3)
    source.subscribe(
            { System.out.println("Observable1:$it") },
            { System.out.println("error") },
            { System.out.println("complete") })
    source.subscribe(
            { System.out.println("Observable2:$it") },
            { System.out.println("error") },
            { System.out.println("complete") })

}