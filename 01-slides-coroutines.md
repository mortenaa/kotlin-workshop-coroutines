---
marp: true
theme: uncover
class: invert
paginate: true

---

# Coroutines

</br>

### JavaZone 2022
### Bjørn Hamre & Morten Nygaard Åsnes


</br>

[@mortenaa](twitter.com/mortenaa) </br>
[@javaguruen](twitter.com/javaguruen)

---
# Coroutines - Motivation

* Avoid blocking main thread
* Concurrency with Threads is difficult. Deadlocks & memory leaks
* Thread are resource hungry
* Do long computation in the background
* Do tasks in parallel

<!--
  Langvarige operasjoner som nettverkskall og disk io blokkerer tråden
  mens de venter på svar. For en interaktiv applikasjon (mobil, js, gui) vil
  ui bli uresponsivt om blokkererman tråden som oppdaterer ui.

  Kan løses med tråder, men tråder er vanskelig å gjøre riktig. Kan føre til
  minnelekasje. Tråder er "tunge" å switche mellom. Vanskelig å debugge.
-->
---

# What is a coroutine?

* A computation that can be suspended and resumed
* Lightweight & fast switching
* Easier to manage than threads
* Can be run on one or many threads

<!--
  Korutiner stammer tilbake til 60 tallet, men ble først popularisert med
  goroutines i Golang. 
  Korutiner baserer seg på at en funksjon kan suspendes for å så fortsette senere. 
  Korutin api er på et høyere abstraksjonsnivå enn tråder. Kotlin håndterer
  bytting av hvilke korutiner som kjører og blir suspended, og kan gjenbruke minnet 
  til en suspended korutine. Korutine er mye mindre ressurskrevende enn tråder.
-->

# Structured concurrency

* child coroutine inherits properties from parent
* cancellation of parent propagates to children

<!--
  Med structured concurrency forsøker man å strukturere bruken av korutiner
  på en måte oversiktlig og trygg måte. Nøsting av uttrykk for korutiner skaper ett 
  hierarki som samsvarer med organiseringen av koden, og som sørger for at feil
  propagerer oppover om de ikke håndteres.

StructuredConcurency betyr at korutiner arver context egenskaper (som Job og dispatcher) fra korutinen den startes i (parent). Om forelder korutinen blir canceled, vil
alle child korutiner også bli canceled. Dette gir en naturlig måte å organisere korutiner på. 
-->
---

# Coroutine Builders

---

# runBlocking

* Starts a coroutine with a new context
* can be called from "normal" code
* waits for all contained coroutines to finish

<!--
  `runBlocking` er en korutine builder som blokerer til korutinen er ferdig. Den lager en ny korutine, 
  og bruker den aktuelle tråden til å kjøre korutinen. 
  Den er en bro mellom "vanlig" kode og korutiner (suspending functions)
-->
---

# launch

* Build and launch a lambda as a coroutine
* extension function on CoroutineScope
* returns Job
  can cancel or wait for completion
* does not return a value from the lambda
* does not wait for contained coroutines to finish
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

# Suspend Functions
<!--
Funksjoner som `runBlocking` og 
`coroutineScope {}` kan benyttes i en suspend funksjon for å få tilgang til coroutine scopet som funksjonen blir kallet fra

-->
---

# Async / Await

---

# CoroutineScope



<!--
En CoroutineScope har en CoroutineContext som har contexten som bestemmer hvordan korutinen kjører. CoroutineContext inneholder bla.a 
En dispatcher som avgjør hvordan tråder allokeres og er `Job` objekt som kan brukes til å sjekke om korutinen kjører, og til å cancellere den.
Innebygde dispatchere `Dispatcher.Default|Main|IO|Unconfined` (TODO: beskrivelse av hver dispatcher). Man kan også sett opp sin egen dispatcher.

Dispatchers:
- Default: General work. Expensive computations. Threadpool based on cpu cores
- Main: Run on main/UI thread. (Android, JavaFX, Swing)
- IO: for IO operations. Large threadpool
- Unconfined: Dont care. same thread

Context og dermed tråd kan endres underveis i en korutine med `withContext`
-->
---

# GlobalScope

<!--
  GlobalScope er et top level coroutine scope som har levetid som applikasjonen, 
  og er dermed mulig å lekke minne om man ikke stopper (`cancel`) korutiner som er startet med dette scope.
-->


