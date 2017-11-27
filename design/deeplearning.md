### Deep learning
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