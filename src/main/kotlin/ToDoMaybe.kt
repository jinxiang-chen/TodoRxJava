import io.reactivex.Maybe

fun main(args: Array<String>) {
    maybe()
}

fun maybe(){
    //one emit

    val presentSource = Maybe.just(100)
    presentSource.subscribe ({System.out.println(it)},
            { System.out.println(it.message) },
            {System.out.println("complete")})
    //no emit
    val emptySource = Maybe.empty<Int>()
    emptySource.subscribe ({System.out.println(it)},
            { System.out.println(it.message) },
            {System.out.println("complete2")})
}