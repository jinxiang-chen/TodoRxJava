import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    flatMap()
}

fun merge() {
    val source1 = Observable.just(1, 2, 3, 4, 5)

    val source2 = Observable.just(11, 12, 13, 14, 15)

    Observable.merge(source1, source2)
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
}

fun mergeWith() {
    val source1 = Observable.just(1, 2, 3, 4, 5)

    val source2 = Observable.just(11, 12, 13, 14, 15)

    source1.mergeWith(source2)
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
}

fun mergeArray() {
    val source1 = Observable.just(1, 2, 3, 4, 5)

    val source2 = Observable.just(11, 12, 13, 14, 15)

    val source3 = Observable.just(111, 112, 113, 114, 115)

    val source4 = Observable.just(1111, 1112, 1113, 1114, 1115)

    Observable.mergeArray(source1, source2, source3, source4)
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
}

fun mergeInterval() {
    val source1 = Observable.interval(1, TimeUnit.SECONDS)
            .map { it + 1 }
            .map { "source1 $it seconds" }

    val source2 = Observable.interval(300, TimeUnit.MILLISECONDS)
            .map { (it + 1) * 300 }
            .map { "source2 $it seconds" }

    Observable.merge(source1, source2)
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })

    sleep(3000)
}

fun flatMap() {
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .flatMap { Observable.fromIterable(it.split("")).filter { a -> a != "" } }
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })

//    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
//            .flatMap({
//                it:String -> Observable.fromIterable(it.split(""))
//            }, { t1:String, t2:String ->
//                "$t1-$t2"
//            })
//            .subscribe (
//                    {System.out.println("Observable:$it")},
//                    {System.out.println("error")},
//                    {System.out.println("complete")})
}