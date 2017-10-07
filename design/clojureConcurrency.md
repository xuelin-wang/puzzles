## Clojure concurrency

### channel
two sets of ops, one in go block. The other not. ops in go block will be executed in a thread pool which has predefined size of 8, but can be updated using a system property.

Parking (in go block) doesn't keep a thread blocked, it just hand the thread to another task if blocked. For long-time waiting task, should use thread + block instead of park.

Use channles to avoid callback hell: say we have this logic: when x -> call f -> when f is done, call g -> when g done, call h. We can have tasks f, g ,h takes input channel and output to a channel. Then the three tasks are separated instead of a callback chain.
