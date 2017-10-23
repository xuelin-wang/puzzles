### Concepts
#### Pseudorandom number generator (PRNG)
Want to take a few random bits, and generate more random bits: {0, 1}^s -> {0, 1}^t, where |s| << |t|.

A PRNG is considered random if its indistinguishable from a true random n bits using an effective algorithm.

Or, equivalently, doesn't exist an i, such that the i+1 bit can be predicted in 2/1 + e, where e is non-negligible, using an effective algorithm.

#### stream cipher
One time pad.

Semantic security: given m0, m1, |m0| = |m1|, for cipher c, attacker cannot differentiate whether c is for m0 or m1, using efficient algo A.

#### Pseudorandom function, Pseudo random permutation
A PRF is a family of functions F: X -> Y that can be deterministically and effectively calculated, such that there is no effective algorithm A to differentiate a PRF chosen randomly from the collection from a random oracle: X -> Y.

#### Block cipher
CBC: chain mode, not parallizable.

CTR: counter mode, parallizable.

semantic security: chosen plaintext attack. After certain number of queries, cannot differentiate c is for m0 or m1.
