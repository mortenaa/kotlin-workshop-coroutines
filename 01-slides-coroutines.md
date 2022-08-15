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
-->

# Structured concurrency

* child coroutine inherits properties from parent
* cancellation of parent propagates to children

<!--
  Med structured concurrency forsøker man å strukturere bruken av korutiner
  på en måte oversiktlig og trygg måte. Nøsting av uttrykk for korutiner skaper ett 
  hierarki som samsvarer med organiseringen av koden, og som sørger for at feil
  propagerer oppover om de ikke håndteres.
-->
---

# Coroutine Builders

---

# runBlocking

---

# launch

---

# Suspend Functions

---

# Async / Await

---

# CoroutineScope



