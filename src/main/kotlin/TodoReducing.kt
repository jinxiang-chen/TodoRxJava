import io.reactivex.Observable

fun main(args: Array<String>) {
    contains()
}

fun count() {
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .count()
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") })

}

fun reduce() {
    Observable.just(1, 4, 6, 8, 9, 3)
            .reduce { t1: Int, t2: Int -> t1 + t2 }
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })

}

fun all() {
    Observable.just(1, 4, 6, 8, 9, 3)
            .all { it < 10 }
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") })
}

fun any() {
    Observable.just(1, 4, 6, 8, 9, 3)
            .any { it > 10 }
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") })
}

fun contains() {
    Observable.just(1, 4, 6, 8, 9, 3)
            .contains(4)
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") })
}