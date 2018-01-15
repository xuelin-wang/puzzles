### A collection of problems

#### Random binomial walk
<pre>
Steps S = p | size = +1, (1-p) | size = -2
Tn = sum(Ti), summary of steps
What is the probability for displacement:
P(Tn = t) = ?
Mean and Var of Tn?

Solution:
For n steps, Pm = C(n, m)*p^m*(1-p)^(n-m) is probability of m steps +1s and n-m steps of -2s.
So P(Tn=t) = Pm where m is solution of:
m - 2(n - m) = 3m - 2n = t
m = (t + 2n) / 3

Mean E(Tn) = ?
(Si + 2) / 3 is distribution B(1,p), (Tn+2n)/3 is distribution B(n, p).
E((Tn+2n)/3) = np, E(Tn) = n(3p -2)
V((Tn+2n)/3) = np(1-P), V(Tn) = 9np(1-p)

</pre>

Review of Bernoulli distribution B(n, p):
<pre>
P(B) = p |e =1, (q = 1-p) | e = 0
E(B1) = p, E(Bn) = E(nB1) = np.
     or E(Bn) = Sum k from 0 to n: k * C(n, k) * p^k * q^(n-k)
     C(n, k) = k! * n! / (n-k)!
     (k + 1) * C(n, k) =
              = Sum
</prev>


