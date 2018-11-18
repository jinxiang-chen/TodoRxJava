import io.reactivex.Observable
import java.util.concurrent.TimeUnit


fun main(args: Array<String>) {
    coldToHot()
}

fun createWithNoError(){
    val source= Observable.create<Int> {
        it.onNext(1)
        it.onNext(2)
        it.onNext(3)
        it.onNext(4)
        it.onNext(5)
        it.onNext(6)
        sleep(5000)
        it.onComplete()
    }

    source.subscribe {
        System.out.println(it)
    }

}

fun createWithError(){
    val source= Observable.create<Int> {
        try{
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
            it.onNext(4)
            it.onNext(5)
            it.onNext(6)
            throw Exception("error")
        }catch (e: Throwable){
            it.onError(e)
        }

    }

    source.subscribe({
        System.out.println(it)
    },{
        System.out.println(it.message)
    })
}

fun just(){
    val source= Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
    val sm = source.map { it.length }
    val sf = sm.filter { i: Int -> i >= 5}
    source.subscribe{System.out.println(it)}
    sm.subscribe{System.out.println("sm:$it")}
    sf.subscribe{System.out.println("sf:$it")}

}

fun coldToHot(){
    val source = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .publish()
    source.subscribe {System.out.println("Observable 1:$it")}

    source.map { it.length }
            .subscribe { length-> System.out.println("Observable 2:$length") }
    source.connect()

}

fun range(){
    val source = Observable.range(1,10)
            .subscribe {System.out.println(it)}

}

fun intervalCold(){
    val source = Observable.interval(1, TimeUnit.SECONDS)
            .subscribe {System.out.println(it)}
    Thread.sleep(5000)

}

fun intervalHot(){
    val source = Observable.interval(1, TimeUnit.SECONDS).publish()

    source.subscribe {System.out.println("Observable 1:$it")}
    source.connect()
    Thread.sleep(5000)

    source.subscribe {System.out.println("Observable 2:$it")}
    Thread.sleep(5000)

}

fun empty(){
    val source = Observable.empty<Any>()
            .subscribe (
                    {System.out.println("next")},
                    {System.out.println("error")},
                    {System.out.println("complete")})

}

fun never(){
    val source = Observable.never<Any>()
            .subscribe (
                    {System.out.println("next")},
                    {System.out.println("error")},
                    {System.out.println("complete")})
    sleep(5000)
}

fun error(){
    Observable.error<Any>(java.lang.Exception("burn"))
            .subscribe (
                    {System.out.println("next")},
                    {it.printStackTrace()},
                    {System.out.println("complete")})
}

fun defer(){
    var count =5
    val source = Observable.defer { Observable.range(1,count) }
    sleep(3000)
    source.subscribe{System.out.println("Observable 1:$it")}
    count = 10
    sleep(3000)
    source.subscribe (
        {System.out.println("Observable:$it")},
        {System.out.println("error")},
        {System.out.println("complete")})
    sleep(5000)
}

fun callable(){
    val source = Observable.fromCallable { 1/0 }
            .subscribe (
            {System.out.println("Observable:$it")},
            {System.out.println("RX error:${it.message}")},
            {System.out.println("complete")})
    sleep(5000)
}

