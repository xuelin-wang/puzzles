### statistics study notes

#### Discrete random variables
##### Bernoulli: toss coin 1 time
X in 0 or 1. PX(1) = p, PX(0) = 1-p = q

E(X) = p

V(X) = E((X-E(X))^2) = E(X^2 - 2XE(X) + E(X)^2) = E(X^2) - E(X)^2
E(X^2) = p^2

V(X) = p - p^2 = pq

##### Binomial: Combine n Bernoulli random variables
PX(k) = C(n, k)*p^k *(1-p)^(n-k)

E(X) = np

V(X) = npq  (addition properties of independent random vars)

##### Geometric random variable
Number of tosses to take to have value 1 appears

PX(k) = (1-p)^k * p

E(X) =

##### Poisson random variable
PX(k) = e^(-&lambda;) &lambda;^k / k!

E(X) = &lambda;

V(X) = &lambda;

Binomial with small p and large n

##### Function of random variables
<pre>
Y = g(X)
E(Y) = yp(y) = sum x: g(x) = y, yp(x) = &Sigma;g(x)pX(x)
E(X^n) = &Sigma;x^n PX(x)

Linear: ax+b
E(Y) = a*E(X) + b
V(Y) = E(Y^2) - E(Y) ^ 2 = E(a^2 X^2 + 2abX + b^2) -(aE(X) +b)^2
     = a^2 E(X^2) + 2aBE(X) + b^2 + a^2 E(X)^2 - 2abE(X) - b^2
     = a^2V(X)
</pre>
* mean: E(X) = sum xP(x). Expect everage over large number of experiments
* moments: nth moment is E(X^n)
* variance: E((X-E(X))^2)
* standard deviation = sqrt(variance)

#### Continuous random variables
##### Uniform distribution
<pre>
a<=x<=b, fX(x) = 1/(b-a)
E(X) = &int; x/(b-a) dx = (b+a)/2
V(X) = E(X^2) - E(X)^2
E(X^2) = &int; x^2/(b-a) dx = (b^3 - a^3)/3(b-a) = (a^2 + ab + b^2)/3
</pre>

##### mean and var of functions
<pre>
E(X) = &int; x * fX(x) dx
E(g(X)) = &int; g(x) * fX(x) dx
var(X) = &int; (x - E(X))^2 dx = E(X^2) - E(X)^2
for Y = aX + b
E(Y) = a E(X)
V(Y) = a^2 V(X)
</pre>

##### exponential random variable
<pre>
fX(x) = &lambda;e^(-&lambda;x) | x >= 0, 0|x < 0
P(x>=a) = e^(-&lambda;a)
E(X) = &int; x&lambda;e^(-&lambda;x) dx
 (xe^(-kx))' = -kxe^(-kx) + e^(-kx)
 kxe^(-x) = e^(-kx) - (xe^(-kx))' = (-e^(-kx)/k)' - (xe^(-kx))'
 E(X) = (-e^(-&lambda;x)/&lambda; - xe^(-&lambda;x) ) |0 to inf
      = 1/&lambda;
 V(X) = 1/&lambda;^2
</pre>

##### cumulative Distribution function
FX(x) = &int; fX(X) dx from -inf to x

##### max of random variables
<pre>
X = max(Xi)
FX(x) = &Pi; FXi(x)
</pre>

##### Geometric and exponential CDFs
<pre>
Geometric:
f(k) = (1-p)^k * p
Fgeo(n) = &Sigma; (1-p)^(k-1) * p , from 1 to n
     = 1 - (1-p)^n

Fexp = 1 - e^(-&lambda;x)
</pre>

##### Normal or Gaussian distribution
<pre>

fX(x) = 1/((2&pi;)^0.5 * &sigma;) * e^(-(x-&mu;)^2 / (2&sigma;^2))

e^(-b(x-a)^2)
(1/(-2b(x-a)) * e^(-b(x-a)^2) )' =  e^(-b(x-a)^2) + 1/(2b(x-a)^2) *  e^(-b(x-a)^2)

&int; e^(-b(x-a)^2) dx
     = 1/(-2b(x-a)) * e^(-b(x-a)^2) - &int; 1/(2b(x-a)^2) * e^(-b(x-a)^2) dx

// y = (x-a)^k
// dy = k(x-a)^(k-1) dx
// dx = 1/k * y ^ (1-k)/k dy
// ( e^(-b y^(2/k) ) )' = e^(-b y^(2/k) 2/k y ^ (2/k - 1)
//     y ^ (2/k - 1) * y ^ (2/k) = y ^ (1-k)/k
//     2/k -1 + 2/k = 1/k - 1
//


</pre>

#### Statistics process
Statistics process is a model of a probabilistic process to that evolves
in time and generates a sequence of numerical values.

* study long-term averages
* dependencies
* boundary events probabilities

Arrival-type processes: inter-arrival times are random variables.
* Bernoulli
* Poisson

Markov processes: next value depends on past values. For Markov, the depdency
is only through current value.



