1. Have you worked with React? If so, what are the benefits and downsides to it?
Yes, I have used React in a vendors data view web app (Two Sigma). The project used clojurescript/reframe.
React encourages separation of model and state.
Model data source is either from local component state or passed through component properties,
 this prevents global state spreading all over the views, creating a web of dependencies among views,
 making code hard to reason and debug.
DOM view specification is declarative. React frees developers from manipulating DOM trees, and performs well due to its COM diff mechanism.
React also simplifies view coding by normalizing the event handling functions and ensure browser neutral.

Downside is that there is a learning curve for the new paradigms, such as controlled components,
and the control flow maybe less obvious comparing to plain javascript + template html code.

2. How would you describe functional programming?
In my understanding, functional programming is to build a program mainly
through functions operating on immutable data. Data is values instead of mutable storage cells.
Functions are mostly like math functions, returns same value when invoking with same arguments.
It encourages reuse of generic/higher order functions.
Functional programs tend to be more concise.
This is different from imperative programming, which mutates local and global state.

3. How do you manage state in a frontend web application?
Good practice is to centralize state in a single place, minimizing component local state.
If it's necessary to maintain a component local state, avoid exposing the state outside the component.
Make state read/write path explicit by controlling the API to access state.
Ensure a single atomic state. Asynchronous backend data queries will mutate the state.
View components will listen to changes for the part the state they cares.

4. Explain monads and why they are or are not useful.
In programming, they are like type "amplifiers": wrapping a type in a new type with some additional constraints,
 but allowing functions applied to wrapped values. Some examples are Nullabe, Maybe etc.
 Monad itself is very general, the usefulness depends on the actual constructs.
 For example, Nullable type is useful in making nullable explicit in type system, helps preventing assigning null
 to non-null type at compile time. Haskell use IO monad to abstract state-mutation actions into a type.
 However, I think wrapping a type also makes programs more complex, so need make sure it's worth it.

