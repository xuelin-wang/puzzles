[TOC levels=4-6 markdown]: # "### Table of Contents"

### Table of Contents
- [Resources](#resources)
- [Application](#application)
- [Deep learning](#deep-learning)
- [Concepts](#concepts)
- [Why is neural network starting work so well now?](#why-is-neural-network-starting-work-so-well-now)
- [Logistic regression, forward/backward propogation, other implementation techniques](#logistic-regression-forwardbackward-propogation-other-implementation-techniques)
- [Activatoin function](#activatoin-function)
- [Intuition of deep NN](#intuition-of-deep-nn)
- [parameters and hyper parameters](#parameters-and-hyper-parameters)
- [training/dev/test data set](#trainingdevtest-data-set)
- [bias and variance](#bias-and-variance)
- [regularization](#regularization)
- [<a name="cs231n"></a> CS231 notes](#-cs231-notes)
    - [Problem](#problem)
    - [Challenges](#challenges)
    - [Pipeline:](#pipeline)
    - [Nearest neighbor classifier: rarely used in practice.](#nearest-neighbor-classifier-rarely-used-in-practice)
    - [Linear classification](#linear-classification)
- [numpy functions](#numpy-functions)
- [AI heroes](#ai-heroes)
- [My notes](#my-notes)
    - [Intuition about deep network](#intuition-about-deep-network)
        - [The only way to conquer a complex non-linear function is to abstract it into](#the-only-way-to-conquer-a-complex-non-linear-function-is-to-abstract-it-into)
        - [Thinking about how brain works](#thinking-about-how-brain-works)
        - [toy problems](#toy-problems)


#### Resources

[Image classification course](https://cs231n.github.io/)

[cs231n learning notes](#cs231n)

#### Application

Reading X-Ray images, image recognition, speeches, self-driving cars.


#### Deep learning

Traing neural network.

Relu function: Rectified linear unit function:
f(x) = max(0, ax + b)

Take a housing price prediction from house sizes. The linear regression
can be viewed as a neural network of a single neuron.

More complex housing price models: size and # bedrooms -> family size, zip code -> walkability,
zip code + wealth -> school quality, family size, walkability, school quality -> price.
This model is a more complex neural network. hiden layer is family size, walkability, school quality.

#### Concepts

* Supervised learning: matching input to expected output.
  Currently most economic value are from supervised learning.

  * Most lucrative ML is online advertising: displaying ads the user most likely to click on.
  * Speech
  * Image
  * Machine translation
  * Self driving

* Types of neural networks.

  * For home feature, Ads, usually use standard neural networks.
  * For image, photo tagging, use convolutional neural networks (CNN).
  * For sequence data like audio, language translation, usually use RNN: recurrent neural networks.
  * For more complex applications such as self-driving, use hybrid models.

* types of data

  * structured data. table of well-defined schema.
  * unstructured data: raw images, text.

#### Why is neural network starting work so well now?

Amount data vs. performance: bigger data and deeper neural network.
* progresses in: data, computation, algorithm (switch from sigmoid to RaLU function).
* effeciency of cycle of idea -> code -> experiment.

#### Logistic regression, forward/backward propogation, other implementation techniques

Logistic regression: Binary classification.

Feature matrix:
* For a*b images right rgb, feature vector size is a*b*3.
* Traing set size m and test set size t. Compact training set into a single matrix r * m.
  Label set is 1 * m.
* To apply linear regression on binary classification probome, y = wx + b, to make y,
  P(y=1|x), between 0 and 1, apply sigmoid function: f(x) = 1/(1 + e^-x). g(x) = f(x) - 1/2 =
  (1-e^-x)/(2(1+e^-x)), symmetrical to y axis.
* Logistic loss function over a single sample. L = 1/2(y-y^)^2, although this is not a good loss
  function, instead, use L = -(y logy^ + (1-y)log(1-y^)). Cost function J = sum m (L(yi, y^i).
* Gradient descent, above cost function J is a convex function of w,b, so can find global minimum
  using gradient descent.
* Computation graph of f(x), walk reverse direction to calculate derivatives.
* Computation graph for binary classification:

```
hi = wxi + b
si = 1/(1+e^(-hi))
Ji = -(y log si + (1-y) log (1-si))

ds = -(hi / si + (1-hi) / (1-si))
ds/dh = e^(-hi) / (1+e^(-hi))^2 = si - si^2 = si(1-si)
dh = - hi (1-si) - (1-hi)si
dw = xi dh
db = dh

on m examples:
dw, db, J will take average over m samples.
loop through all samples
initialize: dw = db = J = 0
for i from 1 to m:
  dw += dwi
  db += dbi
  j += Ji

dw /= m, db /= m, J /= m
adjust w += a * dw, b += a * db, repeat
```


* Python/numpy: vectorization, broadcast. Tips: Avoid using rank 1 array. Assert shapes. reshape.
* Intuition about the cost function:

```
when y = 1, y^ = p(y|x)
when y = 0, 1-y^ = p(y|x)
to cover both cases: p(y/x) = y^**y * (1-y^)**(1-y)
log it:
log p(y/x) = y log y^ + (1-y) log (1-y^) = - L(y^, y)
want to maximize (y/x), minimie cost function.
```

#### Activatoin function

<pre>
sigmoid function g(x) = 1/(1+e^(-x)) = e^x / (1 + e^x)
(g(x))' = -1/(1 + e^(-x))^2 * (-e^(-x)) = 1/(1 + e^(-x)) * (1/(1 + e^(-x)) -1)

tanh function: t(x) = 2(g(2x)) - 1 = (e^x - e^(-x)) / (e^x + e^(-x))
advantage: mean zero

Relu function: r(x) = max(0, z)
advantage: learn faster

Leaky Relu: l(x) = max(0.01 * z, z)
advantage: this usually works better than above

Why do we need a non-linear activation function?
otherwise, the whole network is just one linear function.

</pre>

#### Intuition of deep NN

* Emulate how brain works

Brain detect simple features first, then more complex features. For face recognition,
first edge of picture, then individual components (eyes, noses, etc), then faces.

* Circuitry

FOr example, xor Xi. One layer needs 2^n nodes. But if we do two variables per computation,
We can complete it by only log n layer, with total 2n nodes in all layers.

#### parameters and hyper parameters

Parameters: Wi, bi

Hyper parameters: number of hidden layers, nodes in each layer, learning rate, iterations,
activation functions, etc.

#### training/dev/test data set

Divide of traing/dev/test such as 60/20/20, but for big data, maybe 98/1/1, or even 99.5/0.4/0.1

#### bias and variance

bias = model too simple/underfit

variance = model too complex/overfit

just right = low bias and low variance

bad = high bias and high variance

#### regularization

1. penalize weight (size) of W by adding a component &lambda;||W||. If set &lambda; big,
   this is reducing w's, potentialy reducing some hidden units' effects, making the network
   a simpler one, thus reducing overfitting.
2. when W is small, Z is small, making activation function closer to linear,
   the model becomes to simpler/linear model, thus reducing overfitting.
3. Dropout regularition: for each node, say 0.5 chances of keeping/removing per node.
   With some nodes removed, the network becomes a smaller/simpler network.

   * Inverted dropout: Say keep-prob = 0.8,

     ```python
     d3 = np.random.rand(a3.shape[0], a3.sap[1]) < keep-prob
     a3 = np.multiply(a3, d3)
     a3 = a3 / keep-prob
     ```

   * intuition of dropout:


#### <a name="cs231n"></a> CS231 notes

##### Problem

An image is width * height * rgb, three demensional vector,
needs trun it into a single label.

##### Challenges

* Viewpoint variation
* Scale variation
* Deformation: object can change shapes drastically
* Occlusion
* Illumination conditions
* Background clutter
* Intra-class variation: each label may comprises variety of shapes, such as "chair"

##### Pipeline:

* Input set of images with labels attached.
* Learning: training
* Evaluation: validation

##### Nearest neighbor classifier: rarely used in practice.

* Hyper parameters: distance function: L1, L2, dot product etc. Number of neighbors: k.
* Cross validation vs. split validation
* KNN is easy to train, but hard to predict (need remeber all datasets, calc nearest neighbor). It has more to do with
  general color distribution vs. actual object shapes.

##### Linear classification

* Model: Each image pixels as a flattened vector, each pixel is assiend a weight. Score function
  is: Wi x + bi for each label i.
* Training figure out w vector (n * k) and b (k), n is number of pixcels per image. k is number of labels.
* Intuition:
  * For example, model may like or dislike certain color. For a ship, blue may have more weight.
  * Imagine instead of n * k dimension of data, think of just 2 dimension of data, then each
    wi + b per label is represented as a two dimensional line.
  * As template matching. Similar to KNN, but just one template per label to calculate distance from.
* Linear model is too weak to capture different colored cars, but deep neural network can.
* Data preprocessing: regularization
* Loss function

  * Multiclass support vector machine (SVM): wants correct label's score is at least fixed delta
    away from other labels' scores. Li = for j!= i sum(max(sj - si + delta, 0)), all distances begger
    than delta, count loss as zero. max(0, _) is sometimes called hinge loss. Squared hinge loss is
    max(0, _)^2.

#### numpy functions

'''
import numpy as np
np.arange
shape, reshape
exp, sin
linspace
np.linalg.norm: sqrt(sum of squres), devide each row by norm of the axis

'''

#### AI heroes

* Pieter Abbeel
  * deep reinforcement learning: data representation, credit assignment, exploration, short/long
    time horizon. Learn from scratch vs. reused knowledge.
* Kaggle competition
* Berkerley's deep learning course is online
* Andrewj Karparthy's AI online course
* Yoshua bengio: how brains works is related to neural networks
    * unsupervised learning, complete different ideas to explore


#### My notes
##### Intuition about deep network
###### The only way to conquer a complex non-linear function is to abstract it into
multiple levels of simpler functions.
###### Thinking about how brain works
* Use experience to come up with models and parameters/hyperparameters
* Use experience to tweak params/hyperparams
* Use experience to experiment with different models
###### toy problems
* easier to understand
* faster to iterate

