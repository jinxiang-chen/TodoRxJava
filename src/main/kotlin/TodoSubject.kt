import io.reactivex.Observable
import io.reactivex.subjects.*
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    unicastSubject()
}

fun publicSubjectAsObservable(){
    val subject = PublishSubject.create<String>()
    subject.map { it.length }
            .subscribe { System.out.println(it) }
    subject.onNext("Alpha")
    subject.onNext("Beta")
    subject.onNext("Gamma")
    subject.onComplete()
}

fun publicSubjectAsObserver(){
    val source1 = Observable.interval(1, TimeUnit.SECONDS)
            .map { "${it + 1} seconds" }

    val source2 = Observable.interval(300, TimeUnit.MILLISECONDS)
            .map { "${(it + 1) * 300} milliseconds" }

    val subject = PublishSubject.create<String>()
    subject.subscribe { System.out.println(it) }
    source1.subscribe(subject)
    source2.subscribe(subject)
    sleep(3000)

}

/**
 * 順序發生
 */
fun serializingSubject(){
    val subject = PublishSubject.create<String>().toSerialized()
}

fun behaviorSubject(){
    val subject = BehaviorSubject.create<String>()
    subject.subscribe { System.out.println("Observable 1:$it") }

    subject.onNext("Alpha")
    subject.onNext("Beta")
    subject.onNext("Gamma")

    subject.subscribe { System.out.println("Observable 2:$it") }

    subject.onComplete()
}

fun replySubject(){
    val subject = ReplaySubject.create<String>()
    subject.subscribe { System.out.println("Observable 1:$it") }

    subject.onNext("Alpha")
    subject.onNext("Beta")
    subject.onNext("Gamma")
    subject.onComplete()

    subject.subscribe { System.out.println("Observable 2:$it") }

}

fun asyncSubject(){
    val subject = AsyncSubject.create<String>()
    subject.subscribe({
        System.out.println("Observable 1:$it")
    }, {
        it.printStackTrace()
    }, {
        System.out.println("Observable 1 done!")
    })

    subject.onNext("Alpha")
    subject.onNext("Beta")
    subject.onNext("Gamma")
    subject.onComplete()
    subject.subscribe({
        System.out.println("Observable 2:$it")
    }, {
        it.printStackTrace()
    }, {
        System.out.println("Observable 2 done!")
    })

}

fun unicastSubject(){
    val subject = UnicastSubject.create<String>()

    Observable.interval(300, TimeUnit.MILLISECONDS)
            .map { "${(it + 1) * 300} milliseconds" }
            .subscribe (subject)
    sleep(2000)
    subject.subscribe{
        System.out.println("Observable 1:$it")
    }

    sleep(10000)


}