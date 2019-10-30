package leapcore.lib.decode

// Generic state type
interface State<A, S> {
    val runState: (s: S) -> Pair<A, S>
}

// Functor instance
fun <A, S, B> State<A, S>.map(f: (A) -> B): State<B, S> = object : State<B, S> {
    override val runState: (s: S) -> Pair<B, S> =  {s ->
        val (a, ns) = this@map.runState(s)
        Pair(f(a), ns)
    }
}

// Monad instance
infix fun <A, S, B> State<A, S>.bind(f: (A) -> State<B, S>): State<B, S> = object :
    State<B, S> {
    override val runState: (s: S) -> Pair<B, S> = {s ->
        val (a, ns) = this@bind.runState(s)
        f(a).runState(ns)
    }
}

fun <A, S> retrn(a: A): State<A, S> = object : State<A, S> {
    override val runState: (s: S) -> Pair<A, S> = {s -> Pair(a, s) }
}

// utils
fun <S> put(ns: S): State<Unit, S> = object : State<Unit, S> {
    override val runState: (s: S) -> Pair<Unit, S> = {s -> Pair(Unit, ns)}
}

fun <S> get(): State<S, S> = object : State<S, S> {
    override val runState: (s: S) -> Pair<S, S> = {s -> Pair(s, s) }
}

fun <A, S> eval(st: State<A, S>, s: S): A = st.runState(s).first

fun <A, S> exec(st: State<A, S>, s: S): S = st.runState(s).second