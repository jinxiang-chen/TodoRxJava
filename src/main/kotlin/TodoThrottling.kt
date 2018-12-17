import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    throttleWithTimeout()
}

fun throttleLast(){
    val source1 = Observable.interval(100, TimeUnit.MILLISECONDS)
            .map { (it +1) * 100 }
            .map { "source1 $it" }
            .take(10)
    val source2 = Observable.interval(300, TimeUnit.MILLISECONDS)
            .map { (it +1) * 300 }
            .map { "source2 $it" }
            .take(3)
    val source3 = Observable.interval(2000, TimeUnit.MILLISECONDS)
            .map { (it +1) * 2000 }
            .map { "source3 $it" }
            .take(2)
    Observable.concat(source1, source2, source3)
            .throttleLast(2, TimeUnit.SECONDS)
            .subscribe{
                System.out.println(it)
            }
    sleep(6000)
}

fun throttleFirst(){
    val source1 = Observable.interval(100, TimeUnit.MILLISECONDS)
            .map { (it +1) * 100 }
            .map { "source1 $it" }
            .take(10)
    val source2 = Observable.interval(300, TimeUnit.MILLISECONDS)
            .map { (it +1) * 300 }
            .map { "source2 $it" }
            .take(3)
    val source3 = Observable.interval(2000, TimeUnit.MILLISECONDS)
            .map { (it +1) * 2000 }
            .map { "source3 $it" }
            .take(2)
    Observable.concat(source1, source2, source3)
            .throttleFirst(1, TimeUnit.SECONDS)
            .subscribe{
                System.out.println(it)
            }
    sleep(6000)
}

fun throttleWithTimeout() {
    val source1 = Observable.interval(100, TimeUnit.MILLISECONDS)
            .map { (it + 1) * 100 }
            .map { "source1 $it" }
            .take(10)
    val source2 = Observable.interval(300, TimeUnit.MILLISECONDS)
            .map { (it + 1) * 300 }
            .map { "source2 $it" }
            .take(3)
    val source3 = Observable.interval(2000, TimeUnit.MILLISECONDS)
            .map { (it + 1) * 2000 }
            .map { "source3 $it" }
            .take(2)
    Observable.concat(source1, source2, source3)
            .throttleWithTimeout(1, TimeUnit.SECONDS)
            .subscribe {
                System.out.println(it)
            }
    sleep(6000)
}