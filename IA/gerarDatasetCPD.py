import numpy as np
import matplotlib.pyplot as plt
import os
import cv2
import random
import pickle

DATADIR = "C:/Users/nq3i4/Documents/dataset/crowdai"
CATEGORIES = ["c_0","c_1","c_2","c_3","c_4","c_5","c_6","c_7","c_8","c_9","c_10","c_11","c_12","c_13","c_14","c_15","c_16","c_17","c_18","c_19","c_20","c_21","c_22","c_23","c_24","c_25","c_26","c_27","c_28","c_29","c_30","c_31","c_32","c_33","c_34","c_35","c_36","c_37"]
IMG_SIZE = 150

training_data = []

def create_training_data():
    for category in CATEGORIES:
        path = os.path.join(DATADIR, category)
        class_num = CATEGORIES.index(category)
        for img in os.listdir(path):
            img_array = cv2.imread(os.path.join(path,img), cv2.IMREAD_GRAYSCALE)
            new_array = cv2.resize(img_array, (IMG_SIZE, IMG_SIZE))
            training_data.append([new_array, class_num])


create_training_data()
random.shuffle(training_data)

X = []
y = []

for feature, label in training_data:
    X.append(feature)
    y.append(label)
print(len(X))
print(len(y))

X = np.array(X).reshape(-1, IMG_SIZE, IMG_SIZE, 1)


pickle_out = open("X_gray_150.pickle","wb")
pickle.dump(X, pickle_out)
pickle_out.close()

pickle_out = open("y_gray_150.pickle","wb")
pickle.dump(y, pickle_out)
pickle_out.close()