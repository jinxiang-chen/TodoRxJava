import io.reactivex.Single

fun main(args: Array<String>) {
    single()
}

fun single(){
    Single.just("YOOOOO")
            .map { s: String -> s.length }
            .subscribe({
                System.out.println(it)
            },{
                it.printStackTrace()
            })

}