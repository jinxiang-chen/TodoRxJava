import io.reactivex.Observable

fun main(args: Array<String>) {
    just()
}

fun just(){
    var source= Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
    var sm = source.map { it.length }
    var sf = sm.filter { i: Int -> i >= 5}
    source.subscribe{System.out.println(it)}
    sm.subscribe{System.out.println("sm:$it")}
    sf.subscribe{System.out.println("sf:$it")}
}