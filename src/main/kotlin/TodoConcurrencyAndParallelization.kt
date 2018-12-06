import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.time.LocalTime
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

fun main(args: Array<String>) {
    unsubscribeOnWithAssignSchedulers()
}

fun blockingSubscribe(){
    val source= Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
    source.subscribeOn(Schedulers.computation())
            .map { intenseCalculation(it) }
            .blockingSubscribe(
                    {System.out.println("next:$it")},
                    {it.printStackTrace()},
                    {System.out.println("complete")})
}

fun standardExecutorService(){
    val numberOfThreads = 20
    val executor = Executors.newFixedThreadPool(numberOfThreads)

    val scheduler = Schedulers.from(executor)

    Observable.just("Alpha","Beta","Gamma","Delta")
            .subscribeOn(scheduler)
            .doFinally{
                executor.shutdown()
            }
            .subscribe { System.out.println("next:$it") }
}

fun subscribeOn(){
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .subscribeOn(Schedulers.computation())
            .map { it.length }
            .filter { it > 5 }
            .subscribe { System.out.println("next:$it") }

    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .map { it.length }
            .subscribeOn(Schedulers.computation())
            .filter { it > 5 }
            .subscribe { System.out.println("next:$it") }

    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .map { it.length }
            .filter { it > 5 }
            .subscribeOn(Schedulers.computation())
            .subscribe { System.out.println("next:$it") }
}

fun subscribeOnTwoObserver(){
    val lengths = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .subscribeOn(Schedulers.computation())
            .map { intenseCalculation(it) }
            .map { it.length }
    lengths.subscribe { System.out.println("Received :$it on thread ${Thread.currentThread().name}") }

    lengths.subscribe { System.out.println("Received :$it on thread ${Thread.currentThread().name}") }

    sleep(10000)
}

fun subscribeOnBeforePublish(){
    val lengths = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .subscribeOn(Schedulers.computation())
            .map { intenseCalculation(it) }
            .map { it.length }
            .publish()
            .autoConnect(2)
    lengths.subscribe { System.out.println("Received :$it on thread ${Thread.currentThread().name}") }

    lengths.subscribe { System.out.println("Received :$it on thread ${Thread.currentThread().name}") }
    sleep(10000)
}

fun observeOn(){
    Observable.just("WHISKEY/27635/TANGO", "6555/BRAVO", "232352/5675675/FOXTROT")
            .subscribeOn(Schedulers.io())
            .map { it.split("/") }
            .observeOn(Schedulers.computation())
            .subscribe { System.out.println("next:$it") }
    sleep(10000)
}

fun observeOnBehavior(){
    Observable.just("WHISKEY/27635/TANGO", "6555/BRAVO", "232352/5675675/FOXTROT")
            .subscribeOn(Schedulers.io())
            .map { it.split("/") }
            .doOnNext {
                System.out.println("first on ${Thread.currentThread().name}")
            }
            .observeOn(Schedulers.computation())
            .doOnNext {
                System.out.println("second on ${Thread.currentThread().name}")
            }
            .map {
                it.last()
            }
            .observeOn(Schedulers.io())

            .doOnNext {
                System.out.println("third on ${Thread.currentThread().name}")
            }
            .subscribe { System.out.println("next:$it") }
    sleep(10000)
}

fun noParallel(){
    Observable.range(1, 10)
            .map { intenseCalculation(it) }
            .subscribe { System.out.println("${LocalTime.now()}") }
}

fun hasParallel(){
    Observable.range(1, 10)
            .flatMap{
                Observable.just(it)
                        .subscribeOn(Schedulers.computation())
                        .map { it2 ->
                            intenseCalculation(it2)
                        }
            }
            .subscribe { System.out.println("${LocalTime.now()} on thread ${Thread.currentThread().name}") }
    sleep(20000)

}

fun parallelGroupBy(){
    val coreCount = Runtime.getRuntime().availableProcessors()
    System.out.println(coreCount)
    val assigner = AtomicInteger(0)
    Observable.range(1, 24)
            .groupBy { assigner.incrementAndGet() % coreCount}
            .flatMap { it.observeOn(Schedulers.io()).map { it2 -> intenseCalculation(it2) }}
            .blockingSubscribe { System.out.println("Receive $it ${LocalTime.now()} on thread ${Thread.currentThread().name}") }
}

fun unsubscribeOn(){
    val d = Observable.interval(1, TimeUnit.SECONDS)
            .doOnDispose { System.out.println("Disposing on thread ${Thread.currentThread().name}") }
            .subscribe { System.out.println("Receive $it") }
    sleep(3000)
    d.dispose()
    sleep(3000)
}

fun unsubscribeOnWithAssignSchedulers(){
    val d = Observable.interval(1, TimeUnit.SECONDS)
            .doOnDispose { System.out.println("Disposing on thread ${Thread.currentThread().name}") }
            .unsubscribeOn(Schedulers.io())
            .subscribe { System.out.println("Receive $it") }
    sleep(3000)
    d.dispose()
    sleep(3000)
}

