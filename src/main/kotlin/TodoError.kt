import io.reactivex.Observable

fun main(args: Array<String>) {
    retry()
}

fun doErrorReturnItem() {
    Observable.just(5, 2, 4, 0, 3, 2, 8)
            .map { 10 / it }
            .onErrorReturnItem(-1)
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
}

fun doErrorReturn() {
    Observable.just(5, 2, 4, 0, 3, 2, 8)
            .map { 10 / it }
            .onErrorReturn { -1 }
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
}

fun handleErrorInMap() {
    Observable.just(5, 2, 4, 0, 3, 2, 8)
            .map {
                try {
                    10 / it
                } catch (e: ArithmeticException) {
                    return@map -1
                }
            }
            .onErrorReturn { -1 }
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
}

fun onErrorResumeNext() {
    Observable.just(5, 2, 4, 0, 3, 2, 8)
            .map { 10 / it }
            .onErrorResumeNext(Observable.just(-1).repeat(3))
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })

}

fun onErrorResumeNextWithEmpty() {
    Observable.just(5, 2, 4, 0, 3, 2, 8)
            .map { 10 / it }
            .onErrorResumeNext(Observable.empty())
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })

}

fun onErrorResumeNextWithFunction() {
    Observable.just(5, 2, 4, 0, 3, 2, 8)
            .map { 10 / it }
            .onErrorResumeNext { e: Throwable -> Observable.just(-1).repeat(3) }
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })

}

fun retry() {
    Observable.just(5, 2, 4, 0, 3, 2, 8)
            .map { 10 / it }
            .retry(1)
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })

}