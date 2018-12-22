import io.reactivex.*
import io.reactivex.functions.Action
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import java.util.concurrent.atomic.AtomicInteger

fun main(args: Array<String>) {
    flowableOperator()
}

fun noCustomTransform(){
    Observable.just("A12", "B234", "C5466", "D6466", "E")
            .subscribeOn(Schedulers.io())
            .map {
                it.length
            }
            .subscribe {
                System.out.println("$it")
            }
    Observable.just("11", "123", "23", "4567", "5")
            .subscribeOn(Schedulers.io())
            .map {
                it.length
            }
            .subscribe {
                System.out.println("$it")
            }
    sleep(1000)
}

fun customTransformStringToLength(): ObservableTransformer<String, Int>{
    return ObservableTransformer {
        it.subscribeOn(Schedulers.io())
                .map {
                    s-> s.length
                }
    }
}

fun customTransform(){
    Observable.just("A12", "B234", "C5466", "D6466", "E")
            .compose(customTransformStringToLength())
            .subscribe {
                System.out.println("$it")
            }
    Observable.just("11", "123", "23", "4567", "5")
            .compose(customTransformStringToLength())
            .subscribe {
                System.out.println("$it")
            }
    sleep(1000)
}

fun customFlowableTransformStringToLength(): FlowableTransformer<String, Int>{
    return FlowableTransformer { it ->
        it.subscribeOn(Schedulers.io())
                .map {
                    s-> s.length
                }
    }
}

fun customFlowableTransform(){
    Flowable.just("A12", "B234", "C5466", "D6466", "E")
            .compose(customFlowableTransformStringToLength())
            .subscribe {
                System.out.println("$it")
            }
    Flowable.just("11", "123", "23", "4567", "5")
            .compose(customFlowableTransformStringToLength())
            .subscribe {
                System.out.println("$it")
            }
    sleep(1000)
}

class IndexedValue<T>(val index: Int, val value: T){
    override fun toString(): String {
        return index.toString() + " - " + value
    }
}

fun <T> withIndex(): ObservableTransformer<T, IndexedValue<T>>{
    val indexer = AtomicInteger(-1)
    return ObservableTransformer {
        it.map {
            v -> IndexedValue(indexer.incrementAndGet(), v)
        }
    }
}

fun testWithIndex(){
    val indexedString = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .compose(withIndex())
    indexedString .subscribe {
        System.out.println("Subscriber 1: $it")
    }

    indexedString .subscribe {
        System.out.println("Subscriber 2: $it")
    }
}

fun customOperator(){
    Observable.range(1, 5)
            .lift(doOnEmpty(Action {
                System.out.println("Operation: 1 Empty!")
            }))
            .subscribe {
                System.out.println("Operation: 1: $it")
            }

    Observable.empty<Int>()
            .lift(doOnEmpty(Action {
                System.out.println("Operation: 2 Empty!")
            }))
            .subscribe {
                System.out.println("Operation: 2: $it")
            }

}

fun <T> doOnEmpty(action: Action): ObservableOperator<T, T>{
    return ObservableOperator {
        object : DisposableObserver<T>(){
            var isEmpty = true
            override fun onComplete() {
                if(isEmpty){
                    try {
                        action.run()
                    }catch (e: Exception){
                        onError(e)
                        return
                    }
                }
                it.onComplete()
            }

            override fun onNext(t: T) {
                isEmpty = false
                it.onNext(t)
            }

            override fun onError(e: Throwable) {
                it.onError(e)
            }

        }
    }
}

fun customOperator2() {
    Observable.range(1, 5)
            .lift(toMyList())
            .subscribe {
                System.out.println("Operation: 1: $it")
            }

    Observable.empty<Int>()
            .lift(toMyList())
            .subscribe {
                System.out.println("Operation: 2: $it")
            }

}

fun <T> toMyList(): ObservableOperator<List<T>, T>{
    return ObservableOperator {
        object : DisposableObserver<T>() {
            val list = ArrayList<T>()
            override fun onComplete() {
                it.onNext(list)
                it.onComplete()
            }

            override fun onNext(t: T) {
                list.add(t)
            }

            override fun onError(e: Throwable) {
                it.onError(e)
            }

        }

    }

}

fun flowableOperator(){
    Flowable.range(1, 5)
            .lift(doOnEmptyFlowable(Action {
                System.out.println("Operation: 1 Empty!")
            }))
            .subscribe {
                System.out.println("Operation: 1: $it")
            }

    Flowable.empty<Int>()
            .lift(doOnEmptyFlowable(Action {
                System.out.println("Operation: 2 Empty!")
            }))
            .subscribe {
                System.out.println("Operation: 2: $it")
            }
}

fun <T> doOnEmptyFlowable(action: Action): FlowableOperator<T, T>{
    return FlowableOperator {

        object : DisposableSubscriber<T>() {
            var isEmpty = true
            override fun onComplete() {
                if(isEmpty){
                    try {
                        action.run()
                    }catch (e: Exception){
                        onError(e)
                        return
                    }
                }
                it.onComplete()
            }

            override fun onNext(t: T) {
                isEmpty = false
                it.onNext(t)
            }

            override fun onError(e: Throwable) {
                it.onError(e)
            }

        }
    }
}






