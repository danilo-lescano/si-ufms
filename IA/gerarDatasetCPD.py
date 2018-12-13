import numpy as np
import matplotlib.pyplot as plt
import os
import cv2
import random
import pickle

DATADIR = "C:/Users/nq3i4/Documents/dataset/crowdai"
CATEGORIES = ["c_0","c_1","c_2","c_3","c_4","c_5","c_6","c_7","c_8","c_9","c_10","c_11","c_12","c_13","c_14","c_15","c_16","c_17","c_18","c_19","c_20","c_21","c_22","c_23","c_24","c_25","c_26","c_27","c_28","c_29","c_30","c_31","c_32","c_33","c_34","c_35","c_36","c_37"]

def create_training_data(img_size, gray_sacale):
    training_data = []
    for category in CATEGORIES:
        path = os.path.join(DATADIR, category)
        class_num = CATEGORIES.index(category)
        for img in os.listdir(path):
            if(gray_sacale == 1):
                img_array = cv2.imread(os.path.join(path,img), cv2.IMREAD_GRAYSCALE)
            else:
                img_array = cv2.imread(os.path.join(path,img))
            new_array = cv2.resize(img_array, (img_size, img_size))
            training_data.append([new_array, class_num])


    random.shuffle(training_data)

    X = []
    y = []

    for feature, label in training_data:
        X.append(feature)
        y.append(label)

    X = np.array(X).reshape(-1, img_size, img_size, gray_sacale)

    aux = "_" + str(img_size)
    if(gray_sacale == 1):
        aux += "_gray" 
    pickle_out = open("X" + aux + ".pickle","wb")
    pickle.dump(X, pickle_out)
    pickle_out.close()

    pickle_out = open("y" + aux + ".pickle","wb")
    pickle.dump(y, pickle_out)
    pickle_out.close()



#create_training_data(25, 1)
#create_training_data(25, 3)
#create_training_data(50, 3)
#create_training_data(75, 1)
#create_training_data(75, 3)
#create_training_data(100, 1)
#create_training_data(100, 3)
#create_training_data(150, 1)
#create_training_data(150, 3)
