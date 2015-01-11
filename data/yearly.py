# -*- coding: utf-8 -*-
import numpy as np
import numpy.random
import matplotlib.pyplot as plt
from numpy import genfromtxt
from scipy.interpolate import interp1d

data = genfromtxt('by_year.csv', delimiter=',')
years = data[0]
scores = data[1]

plt.clf()
plt.xlim(1880, 2015)
plt.ylim(10, 100)
plt.plot(years, scores , 'bo', color='black')

data = genfromtxt('averages_yearly.csv', delimiter=',')
avgs_years = data[0]
avgs_avgs = data[1]

plt.plot(avgs_years, avgs_avgs, 'g-', color='green', linewidth=3)

# trendline
# plt.plot(rng, inter(rng), '-', color='white', linewidth=1)

# labels and stuff
plt.legend(['films', 'average rating'], loc='upper left')
plt.xlabel('years')
plt.ylabel("IMDB users' rating")
plt.grid()
plt.show()