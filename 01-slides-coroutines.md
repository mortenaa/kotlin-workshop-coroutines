---
marp: true
theme: uncover
paginate: true
---

# Coroutines

<br/>

### JavaZone 2022
### Bjørn Hamre & Morten Nygaard Åsnes


<br/>

[@mortenaa](twitter.com/mortenaa) <br/>
[@javaguruen](twitter.com/javaguruen)

---

### Coroutines - Motivation

- Avoid blocking main thread
- Concurrency with Threads is difficult. Deadlocks & memory leaks
- Thread are resource hungry
- Do long computation in the background
- Do tasks in parallel

<!--
  Langvarige operasjoner som nettverkskall og disk io blokkerer tråden
  mens de venter på svar. For en interaktiv applikasjon (mobil, js, gui) vil
  ui bli uresponsivt om blokkererman tråden som oppdaterer ui.

  Kan løses med tråder, men tråder er vanskelig å gjøre riktig. Kan føre til
  minnelekasje. Tråder er "tunge" å switche mellom. Vanskelig å debugge.
-->

---

### What is a coroutine?

```kotlin
    val job = launch {
        println("Coroutine started")
        delay(1000L)
        println("Coroutine done")
    }
    println("Coroutine launched")
```

- A computation that can be suspended and resumed
- Lightweight & fast switching
- Easier to manage than threads
- Can be run on one or many threads

<!--
  Korutiner stammer tilbake til 60 tallet, men ble først popularisert med
  goroutines i Golang. 
  Korutiner baserer seg på at en funksjon kan suspendes for å så fortsette senere. 
  Korutin api er på et høyere abstraksjonsnivå enn tråder. Kotlin håndterer
  bytting av hvilke korutiner som kjører og blir suspended, og kan gjenbruke minnet 
  til en suspended korutine. Korutine er mye mindre ressurskrevende enn tråder.
-->

---

### Structured concurrency

- child coroutine inherits properties from parent
- cancellation of parent propagates to children
- errors in children propogates to parents

<!--
  Med structured concurrency forsøker man å strukturere bruken av korutiner
  på en måte oversiktlig og trygg måte. Nøsting av uttrykk for korutiner skaper ett 
  hierarki som samsvarer med organiseringen av koden, og som sørger for at feil
  propagerer oppover om de ikke håndteres.

StructuredConcurency betyr at korutiner arver context egenskaper (som Job og dispatcher) fra korutinen den startes i (parent). Om forelder korutinen blir canceled, vil
alle child korutiner også bli canceled. Dette gir en naturlig måte å organisere korutiner på. 
-->
---

### Coroutine Builders

---

### runBlocking

- Starts a coroutine with a new context
- can be called from "normal" code
- not usually used outside of tests or main function
- waits for all contained coroutines to finish
- blocks the thread!

<!--
  `runBlocking` er en korutine builder som blokerer til korutinen er ferdig. Den lager en ny korutine, 
  og bruker den aktuelle tråden til å kjøre korutinen. 
  Den er en bro mellom "vanlig" kode og korutiner (suspending functions)
  Siden runBlocking skal kunne kalles fra normal kode kan den ikke suspende selv,
  men må blokkere tråden den kjører i til corutinene er ferdig
-->
---

### launch

- Build and launch a lambda as a coroutine
- extension function on CoroutineScope
- returns Job
  can cancel or wait for completion
- does not return a value from the lambda
- waits for coroutines inside to finish (suspends but does not block)
  - for GlobalScope it does not wait for contained coroutines to finish

<!--
  launch er en coroutine builder som lager en korutine, som startes umiddelbart (men det 
  kan konfigureres).
  Den er implementert som en extension funksjon på CoroutineScope. launch returnerer
  en instans av Job. den har metoder for bla.a å kansellere en korutine, eller vente 
  på at den skal fullføre.

  Det finnes en subklasse av CorutineScope, GlobalScope som kjører korutinen utenfor
  structured concurency. Dvs. alt av opprydding av ressurser ved feilsitasjoer. Den må
  derfor brukes forsiktig.
-->
---

### Suspend Functions

- all functions that can suspend is marked with the `suspend` keyword
- suspending code can not be called from a non-suspending code
- but non-suspending code can be called from suspending code
- a suspend function doesn't provide a CoroutineScope 
  - (we can use `coroutineScope` for that)

<!--
Funksjoner som `runBlocking` og 
`coroutineScope {}` kan benyttes i en suspend funksjon for å få tilgang til coroutine scopet som funksjonen blir kallet fra

-->
---

### Async / Await

```kotlin
    val result = async {
        delay(1000L)
        42
    }
    println("Result=${result.await()}")
```

- Build and start coroutine (like launch)
- extension on CoroutineScope interface
- Returns Deferred<T> which is subclass of Job
- Can cancel or join like with Job
- Can also `await`, which produces a value when completed
- `await` will suspend until value is ready

---

### CoroutineScope

<!--
En CoroutineScope har en CoroutineContext som har contexten som bestemmer hvordan korutinen kjører. CoroutineContext inneholder bla.a 
En dispatcher som avgjør hvordan tråder allokeres og er `Job` objekt som kan brukes til å sjekke om korutinen kjører, og til å cancellere den.
Innebygde dispatchere `Dispatcher.Default|Main|IO|Unconfined` (TODO: beskrivelse av hver dispatcher). Man kan også sett opp sin egen dispatcher.

Context og dermed tråd kan endres underveis i en korutine med `withContext`
-->

# Dispatchers

- Default: General work. Expensive computations. Threadpool based on cpu cores
- Main: Run on main/UI thread. (Android, JavaFX, Swing)
- IO: for IO operations. Large threadpool
- Unconfined: Dont care. same thread

---

### GlobalScope

<!--
  GlobalScope er et top level coroutine scope som har levetid som applikasjonen, 
  og er dermed mulig å lekke minne om man ikke stopper (`cancel`) korutiner som er startet med dette scope.
-->

---

### Cancellation

---

### Error handling

---

### Testing Coroutines

---

### Debugging Coroutines
