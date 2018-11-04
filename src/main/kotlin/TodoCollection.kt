import io.reactivex.Observable

fun main(args: Array<String>) {
    collect()
}

fun toList() {
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .toList()
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") })
}

fun toSortList() {
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .toSortedList()
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") })
}

fun toMap() {
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .toMap({ it[0] }, { it.length })
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") })
}

fun toMultimap() {
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .toMultimap { it.length }
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") })
}

fun collect() {
    Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
            .collect({ HashSet<String>() }, { b, t -> b.add(t) })
            .subscribe(
                    { System.out.println("Observable:$it") },
                    { System.out.println("error") })
}

