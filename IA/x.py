class_weight = {
    0: 270.,
    1: 280.,
    2: 124.,
    3: 713.,
    4: 586.,
    5: 510.,
    6: 358.,
    7: 191.,
    8: 519.,
    9: 374.,
    10: 445.,
    11: 476.,
    12: 552.,
    13: 420.,
    14: 171.,
    15: 2321.,
    16: 962.,
    17: 108.,
    18: 428.,
    19: 577.,
    20: 412.,
    21: 380.,
    22: 64.,
    23: 240.,
    24: 1917.,
    25: 749.,
    26: 390.,
    27: 214.,
    28: 792.,
    29: 405.,
    30: 726.,
    31: 360.,
    32: 734.,
    33: 720.,
    34: 547.,
    35: 2100.,
    36: 139.,
    37: 643.
}
for key,val in class_weight.items():
    class_weight[key] = val/2321


import pickle
pickle_in = open("y.pickle","rb")
y = pickle.load(pickle_in)
from keras.utils import np_utils
y = np_utils.to_categorical(y, num_classes=None)
print(y[0])