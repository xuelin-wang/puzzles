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
CBC: chain mode, not parallizable. c1=F(k, m0 xor IV),
c2 = F(k, m1 xor c1), ... IV must be unique and mapped from an independent PRP from F, otherwise a chosen plaintext and chosen counter number will break it. Note PRP mapped IV must be transfered in cipher text for decryption.

CTR: counter mode, parallizable.

semantic security: chosen plaintext attack. After certain number of queries, cannot differentiate c is for m0 or m1.

#### PMAC, One time MAC, Many time MAC, HMAC
PMAC: split message into blocks, each block is xored with P(k, i), this is to prevent switch block attack. then F(k, block i), xor them together and F(k, results) = tag.

One time MAC can use faster method.

Many time MAC use one time MAC with same key (fast) for original long messages, then apply one last step using different key (slow).

HMAC using hash function. Some of details: PAD: 1||0^k||four bytes length.

To prevent time attack, either try to always do same number of compares (may be optimized away by compilers), or add one extra step of Hash before compare so attacker won't know what are being compared.

#### Goals
Confidentiality: Chosen plaintext prevents eavesdropping. Adversaries cannot find out information about encrypted plaintext.

Integrity: Adversaries cannot forge a message. But message is not necessarily encrypted.

IP Sec: TCP/IP stack at source and dest share a secret key. IP layer encryption at source and decryption at destination. Without integrity, cannot guarantee confidentiality due to tampering dest port of message by changing IV in CBC for example.

Authenticated encryption: E: K X M X N -> C, D: K X C X N -> M or bottom. Bottom means invalid cipher text. Authenticated encryption prevents both chosen plain text and cipher text tampering (could not forge cipher text). Although it does not prevent replay attack.

Correctly combining CPA-proof encryption and integrity methods: SSH (encrypt and mac): C || tag(m, km) -- bad, tag can reveal message. SSL (mac then encrypt): C = D(m || tag(m, km)) -- certain CPA attack. IPSec (encrypt then mac): C = D(m, km) || tag(c, k) -- best, can  not forge cipher text and no revealation of plain text.

#### Key exchange
TTP Trusted third party. Each user store a key in TTP. If a and b needs exchange a shared key, TTP send tickets to a: E(Ka, (a,b) || Kab), b: E(Kb, (a,b) || Kab). E is CPA secure encryption. This is eavesdropping secure, but not against active attacks such as replay attack. Kerberos is based on this idea.

Diffe/Hellman: Key exchange without TTP. Use fixed prime p and generator g. A send g^a to B, B send g^b to A. A and B both can calculate g^(ab) easily, but hard for other to calculate without knowing a or b. The time for others is O(e^(n^1/3)). This doesn't prevent MIM (Man in the middle attach) though. Elliptic functions have better properties and is harder to crack. This is for two party key exchanges without TTP. Three parties also has solution with very complicated functions. parties more than three is an **open question**.

Public key exchange: (G, E, D), G is the algorihm to generate key pair (pk, sk). Public key exchange cannot prevent MIM attack.

#### Public Key System
Trap door function/permutation: (F, pk, sk), c = F(pk, m), m = F^-1(sk, c). One way, hard to invert without knowing sk.

Public key encryption system, don't use textbook public key encryption.
* random x, y = F(pk, x)
* k = H(x)
* output (y, c=E(m, k))
* when decrypt, x = F^-1(sk, y), k = H(x), m = D(c, k)

RSA trap door permutation. Recommended encryption key is 2^16 + 1. so 17 multiplications. Decryption is much slower with factor of 10 to 30.

RSA is one way if factoring large integer is hard. This is an open question. 
