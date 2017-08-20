### monads


- Category theory

A endofunctor F: C->C maps objects and morphisms while preserving unit and associativity of morphisms.

A monad on category C consists an endofunctor T with two natural transformations join and unit:<br>
join: T2->T<br>
unit: 1C->T

A monad is also a monoid in monoidal categories of endofunctors on C with functor composition as tensor product.

- In programming

A functor F maps a type a to a new type F(a), function f: a->b to function F(a)->F(b)=F(f(a)).

A monad M is a construction to compose computations. It specifies additional logic/context while composing computations. A monad defines one of fish operator, bind or join, in addition to unit operator.<br>
unit: a->M(a)<br>
one of:<br>
fish: (a->M(b)) -> (b->M(c)) -> (a->M(c)) <br>
bind: M(a) -> (a->M(b)) -> (a->M(c)) <br>
join: M(M(a)) -> M(a)

- Connection between category theory and programming

Think type a is an object in category C. A functor F maps type a to type F(a). A function f: a->b is a morphism in C. F maps f to F(f): F(a)->F(b).

A monad M is a functor C->C with two

Consider a type as an object in category C, computation a morphism in category C. A monad is an eudofunctor M on C. join function is the natural from T2->T and unit is the natural 1C->T.

- Monad examples:

In programming, monad(Functor) mapping itself is really general that it can attach all kinds of context.<br>
Some example: Maybe, Either, Sequence, Logging, Future.<br>

- When to use monads:

If a common context computation can be cleanly extracted out. And lots of functions will need this feature.

- When not to use monads:

Not many functions need this feature.
