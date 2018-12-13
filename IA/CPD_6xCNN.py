#CPD = convolutional plantvillage disease
import tensorflow as tf
from tensorflow.keras.datasets import cifar10
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Dropout, Activation, Flatten
from tensorflow.keras.layers import Conv2D, MaxPooling2D
from tensorflow.keras.callbacks import TensorBoard
from keras.utils import np_utils
import pickle
import time

NAME = "PD-6xConv(128)-16batch-100xImgSize-noDense-adadelta-{}".format(int(time.time()))

tensorboard = TensorBoard(log_dir='logs/{}'.format(NAME))

gpu_options = tf.GPUOptions(per_process_gpu_memory_fraction=0.333)
sess = tf.Session(config=tf.ConfigProto(gpu_options=gpu_options))

pickle_in = open("X_100.pickle","rb")
X = pickle.load(pickle_in)

pickle_in = open("y_100.pickle","rb")
y = pickle.load(pickle_in)
y = np_utils.to_categorical(y, num_classes=38)

X = X/255.0


class_weight = {0: 0.11632916846186989, 1: 0.12063765618267988, 2: 0.05342524773804395, 3: 0.30719517449375267, 4: 0.25247738043946577, 5: 0.2197328737613098, 6: 0.15424386040499785, 7: 0.08229211546747092, 8: 0.2236105127100388, 9: 0.16113744075829384, 10: 0.1917277035760448, 11: 0.2050840155105558, 12: 0.23782852218871176, 13: 0.1809564842740198, 14: 0.07367514002585093, 15: 1.0, 16: 0.4144765187419216, 17: 0.046531667384747954, 18: 0.18440327445066781, 19: 0.24859974149073674, 20: 0.17750969409737183, 21: 0.16372253339077983, 22: 0.027574321413183972, 23: 0.1034037052994399, 24: 0.8259370960792761, 25: 0.32270573028866867, 26: 0.16803102111158982, 27: 0.09220163722533391, 28: 0.3412322274881517, 29: 0.17449375269280482, 30: 0.3127962085308057, 31: 0.15510555794915984, 32: 0.3162429987074537, 33: 0.3102111158983197, 34: 0.23567427832830676, 35: 0.9047824213700991, 36: 0.05988797931925894, 37: 0.2770357604480827}



model = Sequential()

model.add(Conv2D(128, (3, 3), input_shape=X.shape[1:]))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))

model.add(Conv2D(128, (3, 3)))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))

model.add(Conv2D(128, (3, 3)))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))

model.add(Conv2D(128, (3, 3)))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))

model.add(Dropout(0.25))

model.add(Conv2D(128, (3, 3)))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))

model.add(Conv2D(128, (3, 3)))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))

model.add(Conv2D(128, (3, 3)))
model.add(Activation('relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))

model.add(Dropout(0.25))

model.add(Flatten())  # this converts our 3D feature maps to 1D feature vectors
#model.add(Dense(128))
#model.add(Activation('relu'))
#model.add(Dropout(0.5))

model.add(Dense(38))
model.add(Activation('sigmoid')) # || model.add(Activation('softmax'))




#model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
# ||
model.compile(loss='categorical_crossentropy', optimizer='adadelta', metrics=['accuracy'])

model.fit(X, y, batch_size=16, epochs=20, validation_split=0.3, class_weight=class_weight, callbacks=[tensorboard])

model.save('PD-6xConv(128)-16batch-100xImgSize-noDense-adadelta-model')