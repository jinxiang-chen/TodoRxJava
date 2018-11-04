import io.reactivex.Observable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    scan()
}

fun map() {
    val dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy")
    Observable.just("1/3/2018", "5/9/2018")
            .map { LocalDate.parse(it, dateTimeFormatter) }
            .subscribe(
                    { System.out.println("next:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
}

fun cast() {
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .cast(Any::class.java)
            .subscribe(
                    { System.out.println("next:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
}

fun startWith() {
    val menu = Observable.just("Coffee", "Tea", "Latte")
    menu.startWith("COFFEE SHOP MENU")
            .subscribe { System.out.println(it) }
}

fun startWithArray() {
    val menu = Observable.just("Coffee", "Tea", "Latte")
    menu.startWithArray("COFFEE SHOP MENU", "----------------")
            .subscribe { System.out.println(it) }
}

fun defaultIfEmpty() {
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .filter { it.startsWith("z") }
            .defaultIfEmpty("none")
            .subscribe(
                    { System.out.println("next:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
}

fun switchIfEmpty() {
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .filter { it.startsWith("Z") }
            .switchIfEmpty(Observable.just("1", "2"))
            .subscribe(
                    { System.out.println("next:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
}

fun sorted() {
    Observable.just(2, 5, 6, 8, 9, 4, 0, 3)
            .sorted()
            .subscribe {
                System.out.println("$it")
            }

    Observable.just(2, 5, 6, 8, 9, 4, 0, 3)
            .sorted(Comparator.reverseOrder())
            .subscribe {
                System.out.println("observable2:$it")
            }

    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .sorted { o1, o2 -> Integer.compare(o1.length, o2.length) }
            .subscribe {
                System.out.println("observable3:$it")
            }
}

fun delay() {
    Observable.just(2, 5, 6, 8, 9, 4, 0, 3)
            .delay(1, TimeUnit.SECONDS)
            .subscribe {
                System.out.println("observable:$it")
            }
    sleep(5000)
}

fun repeat() {
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .repeat(2)
            .subscribe(
                    { System.out.println("next:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })
}

fun scan() {
    Observable.just(2, 5, 6, 8, 9, 4, 0, 3)
            .scan { t1: Int, t2: Int -> t1 + t2 }
            .subscribe(
                    { System.out.println("next:$it") },
                    { System.out.println("error") },
                    { System.out.println("complete") })

    Observable.just(2, 5, 6, 8, 9, 4, 0, 3)
            .scan(0) { t1, t2 -> t1 + 1 }
            .subscribe(
                    { System.out.println("next2:$it") },
                    { System.out.println("error2") },
                    { System.out.println("complete2") })
}