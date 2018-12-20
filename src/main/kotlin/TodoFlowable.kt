import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

fun main(args: Array<String>) {
    flowableGenerate()
}

fun noBackPressure(){
    Observable.range(1, 999_999_999)
            .map { MyItem(it) }
            .observeOn(Schedulers.io())
            .subscribe{
                sleep(50)
                System.out.println("Received MyItem ${it.id}")
            }
    sleep(Long.MAX_VALUE)
}

fun backPressure(){
    Flowable.range(1, 999_999_999)
            .map { MyItem(it) }
            .observeOn(Schedulers.io())
            .subscribe{
                sleep(50)
                System.out.println("Received MyItem ${it.id}")
            }
    sleep(Long.MAX_VALUE)
}

class MyItem(val id: Int){

    init {
        System.out.println("Constructing MyItem $id")
    }
}

fun flowableSubscriber(){
    Flowable.range(1, 1000)
            .doOnNext { System.out.println("Source pushed $it") }
            .observeOn(Schedulers.io())
            .map { intenseCalculation(it) }
            .subscribe({
                System.out.println("Subscriber received $it")
            },{
                it.printStackTrace()
            },{
                System.out.println("Done!")
            })
    sleep(20000)
}

fun customSubscriber(){
    Flowable.range(1, 1000)
            .doOnNext { System.out.println("Source pushed $it") }
            .observeOn(Schedulers.io())
            .map { intenseCalculation(it) }
            .subscribe(object : Subscriber<Int> {

                var subscription: Subscription? = null
                val count = AtomicInteger(0)

                override fun onComplete() {
                    System.out.println("Done!")
                }

                override fun onSubscribe(s: Subscription?) {
                    subscription = s
                    System.out.println("Request 40 items!")
                    s?.request(40)
                }

                override fun onNext(t: Int?) {
                    sleep(50)
                    System.out.println("Subscriber received $t")
                    if(count.incrementAndGet() % 20 == 0 && count.get() >= 40){
                        System.out.println("Request 20 more!")
                        subscription?.request(20)

                    }
                }

                override fun onError(t: Throwable?) {
                    t?.printStackTrace()
                }

            })
    sleep(20000)
}

fun backPressureStrategy(){
    val source = Flowable.create(FlowableOnSubscribe<Int> {
        for(i in 1..1000){
            if(it.isCancelled){
               return@FlowableOnSubscribe
            }
            it.onNext(i)
        }

        it.onComplete()
    }, BackpressureStrategy.BUFFER)
    val a = source.observeOn(Schedulers.io())
            .subscribe {
                System.out.println(it)
            }
    sleep(1000)
}

fun observableToFlowable(){
    val source = Observable.range(1, 1000)
    source.toFlowable(BackpressureStrategy.BUFFER)
            .observeOn(Schedulers.io())
            .subscribe {
                System.out.println(it)
            }
    sleep(10000)
}

fun onBackPressureBuffer(){
    Flowable.interval(1, TimeUnit.MILLISECONDS)
            .onBackpressureBuffer(10, {
                System.out.println("overflow!")
            }, BackpressureOverflowStrategy.DROP_OLDEST)
            .observeOn(Schedulers.io())
            .subscribe ({
                sleep(5)
                System.out.println(it)
            }, {
                it.printStackTrace()
            }, {

            })
    sleep(5000)
}

fun onBackPressureLatest(){
    Flowable.interval(1, TimeUnit.MILLISECONDS)
            .onBackpressureLatest()
            .observeOn(Schedulers.io())
            .subscribe ({
                sleep(5)
                System.out.println(it)
            }, {
                it.printStackTrace()
            }, {
                System.out.println("done!")
            })
    sleep(5000)
}

fun onBackPressureDrop(){
    Flowable.interval(1, TimeUnit.MILLISECONDS)
            .onBackpressureDrop {
                System.out.println("Dropping $it")
            }
            .observeOn(Schedulers.io())
            .subscribe ({
                sleep(5)
                System.out.println(it)
            }, {
                it.printStackTrace()
            }, {
                System.out.println("done!")
            })
    sleep(5000)
}

fun flowableGenerate(){
    Flowable.generate<Int> {
        it.onNext(ThreadLocalRandom.current().nextInt(0, 100))
    }
            .subscribeOn(Schedulers.computation())
            .doOnNext {
                System.out.println("Emitting $it")
            }
            .observeOn(Schedulers.io())
            .subscribe ({
                sleep(50)
                System.out.println("Receive $it")
            }, {
                it.printStackTrace()
            }, {
                System.out.println("done!")
            })
    sleep(10000)
}