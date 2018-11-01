import io.reactivex.Observable
import io.reactivex.functions.Predicate

fun main(args: Array<String>) {
    filter()
}

fun filter(){
    val source= Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
    source.filter { it.length != 5 }
            .subscribe {System.out.println(it)}
}