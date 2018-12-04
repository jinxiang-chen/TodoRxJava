import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import java.util.concurrent.Executors

fun main(args: Array<String>) {
    observeOnBehavior()
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