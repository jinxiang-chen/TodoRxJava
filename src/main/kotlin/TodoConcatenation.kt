import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    zip()
}

fun concat() {
    val source1 = Observable.just(1, 2, 3, 4, 5)

    val source2 = Observable.just(11, 12, 13, 14, 15)

    Observable.concat(source1, source2)
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
}

fun concatWith() {
    val source1 = Observable.just(1, 2, 3, 4, 5)

    val source2 = Observable.just(11, 12, 13, 14, 15)

    source1.concatWith(source2)
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
}

fun concatMap() {

    val source1 = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")

    source1.concatMap { Observable.fromIterable(it.split("")) }
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
}

fun ambiguous() {
    val source1 = Observable.interval(1, TimeUnit.SECONDS)
            .take(2)
            .map { it + 1 }
            .map { "Source1 $it seconds" }

    val source2 = Observable.interval(300, TimeUnit.MILLISECONDS)
            .map { (it + 1) * 300 }
            .map { "Source2 $it milliseconds" }
    Observable.ambArray(source1, source2)
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
    sleep(5000)
}

fun zip() {
    val source1 = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")

    val source2 = Observable.interval(1, TimeUnit.SECONDS)

    Observable.zip(source1, source2, BiFunction { t1: String, t2: Long -> "$t1-$t2" })
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
    sleep(6000)
}

fun zipWith() {
    val source1 = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")

    val source2 = Observable.interval(1, TimeUnit.SECONDS)

    source1.zipWith(source2, BiFunction { t1: String, t2: Long -> "$t1-$t2" })
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
    sleep(6000)
}

fun combineLatest() {
    val source1 = Observable.interval(300, TimeUnit.MILLISECONDS)

    val source2 = Observable.interval(1, TimeUnit.SECONDS)
    Observable.combineLatest(source1, source2, BiFunction { t1: Long, t2: Long ->
        "$t1-$t2"
    }).subscribe(
            { System.out.println("Observable:$it") },
            { System.out.println("error") },
            { System.out.println("complete") })
    sleep(6000)
}

fun withLatestFrom() {
    val source1 = Observable.interval(300, TimeUnit.MILLISECONDS)

    val source2 = Observable.interval(1, TimeUnit.SECONDS)
    source2.withLatestFrom(source1, BiFunction { t1: Long, t2: Long ->
        "$t1-$t2"
    }).subscribe(
            { System.out.println("Observable:$it") },
            { System.out.println("error") },
            { System.out.println("complete") })
    sleep(6000)
}

fun groupBy() {
    val source1 = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")

    val byLength = source1.groupBy {
        it.length
    }
    byLength.flatMapSingle {
        it.toList()
    }.subscribe(
            { System.out.println("Observable:$it") },
            { System.out.println("error") },
            { System.out.println("complete") })
}