import numpy as np
import numpy.random
import matplotlib.pyplot as plt
from numpy import genfromtxt
from scipy.interpolate import interp1d

# Generate some test data
x = np.random.randn(8873)
y = np.random.randn(8873)

x_interpolated = [1.1,2,3.7,5.70,9.11,10]
y_interpolated = [1.1,2,3.7,6.04,8.57,9.1]
rng = numpy.linspace(1, 10, 90)
inter = interp1d(x_interpolated, y_interpolated, kind='cubic', bounds_error=False)

heatmap, xedges, yedges = np.histogram2d(x, y, bins=100)
extent = [xedges[0], xedges[-1], yedges[0], yedges[-1]]

scala_heatmap = genfromtxt('heatmap.csv', delimiter=',')

plt.clf()
# plt.imshow(heatmap, extent=extent)
# plt.imshow(scala_heatmap, origin='lower', vmin=0, vmax=5, extent=[1,10,1,10], interpolation='none', filterrad=2)
plt.imshow(scala_heatmap, origin='lower', vmin=0, vmax=5, extent=[1,10,1,10], interpolation='bicubic', filterrad=2)
plt.plot([1, 10], [1, 10], '-', color='black', linewidth=1)
# plt.plot(rng, inter(rng), '-', color='white', linewidth=1)
# plt.imshow(scala_heatmap)
plt.xlabel('IMDB users rating')
plt.ylabel('FilmWeb users rating')
plt.show()