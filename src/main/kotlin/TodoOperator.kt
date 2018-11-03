import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    lastElement()
}

fun filter(){
    val source= Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
    source.filter { it.length != 5 }
            .subscribe {System.out.println(it)}
}

fun take(){
    val source= Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
    source.take(3)
            .subscribe (
                    {System.out.println(it)},
                    {

                    },{
                        System.out.println("complete")
                    })
}

fun takeWithInterval(){
    val source = Observable.interval(1000, TimeUnit.MILLISECONDS)
    source.take(2, TimeUnit.SECONDS)
            .subscribe (
                    {System.out.println("next:$it")},
                    {System.out.println("error")},
                    {System.out.println("complete")})
    sleep(5000)

}

fun skip(){
    Observable.range(1, 100)
//            .skipLast(10)
            .skip(90)
            .subscribe(
                    {System.out.println("next:$it")},
                    {System.out.println("error")},
                    {System.out.println("complete")})
}

fun takeWhile(){
    Observable.range(1,100)
            .takeWhile { it < 50}
            .subscribe(
                    {System.out.println("next:$it")},
                    {System.out.println("error")},
                    {System.out.println("complete")})
}

fun skipWhile(){
    Observable.range(1,100)
            .skipWhile() { it < 50}
            .subscribe(
                    {System.out.println("next:$it")},
                    {System.out.println("error")},
                    {System.out.println("complete")})
}

fun distinct(){
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .map { it.length }
            .distinct()
            .subscribe(
                    {System.out.println("next:$it")},
                    {System.out.println("error")},
                    {System.out.println("complete")})
}

fun distinctWithKey(){
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .distinct{it.length}
            .subscribe(
                    {System.out.println("next:$it")},
                    {System.out.println("error")},
                    {System.out.println("complete")})
}

fun distinctUntilChange(){
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .distinctUntilChanged()
            .subscribe(
                    {System.out.println("next:$it")},
                    {System.out.println("error")},
                    {System.out.println("complete")})
}

fun distinctUntilChangeWithKey(){
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .distinctUntilChanged{t -> t.length}
            .subscribe(
                    {System.out.println("next:$it")},
                    {System.out.println("error")},
                    {System.out.println("complete")})
}

fun elementAt(){
    val source = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
    source.elementAt(3)
            .subscribe(
                    {System.out.println("1next:$it")},
                    {System.out.println("1error")},
                    {System.out.println("1complete")})

    source.elementAt(6)
            .subscribe(
                    {System.out.println("2next:$it")},
                    {System.out.println("2error")},
                    {System.out.println("2complete")})

    source.elementAt(6, "empty")
            .subscribe(
                    {System.out.println("3next:$it")},
                    {System.out.println("3error")})

}

fun elementAtOrError(){
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .elementAtOrError(6)
            .subscribe(
                    {System.out.println("next:$it")},
                    {System.out.println("error")})
}

fun singleElement(){
    val source = Observable.just("Alpha")
    source.singleElement()
            .subscribe(
                    {System.out.println("next:$it")},
                    {System.out.println("error")})
    val source2 = Observable.just("Alpha","Beta")
    source2.singleElement()
            .subscribe(
                    {System.out.println("next:$it")},
                    {System.out.println("error")})
}

fun firstElement(){
    val source = Observable.just("Alpha","Beta")
    source.firstElement()
            .subscribe(
                    {System.out.println("next:$it")},
                    {System.out.println("error")})

}

fun lastElement(){
    val source = Observable.just("Alpha","Beta")
    source.lastElement()
            .subscribe(
                    {System.out.println("next:$it")},
                    {System.out.println("error")})

}