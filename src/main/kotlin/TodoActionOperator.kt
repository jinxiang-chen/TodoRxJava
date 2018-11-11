import io.reactivex.Observable

fun main(args: Array<String>) {
    doOnSubscribe()
}

fun doOnNext() {
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .doOnNext { System.out.println("Processing") }
            .map { it.length }
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
}

fun doOnComplete() {
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .doOnComplete { System.out.println("done") }
            .map { it.length }
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
}

fun doOnError() {
    Observable.just(5, 7, 3, 0, 8, 9)
            .doOnError { System.out.println("source failed!") }
            .map { 10 / it }
            .doOnError { System.out.println("Division failed!!") }
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
}

fun doOnSubscribe() {
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .doOnSubscribe { System.out.println("Subscribe") }
            .doOnDispose { System.out.println("Dispose") }
            .map { it.length }
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
}
