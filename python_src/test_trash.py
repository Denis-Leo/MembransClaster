


from random import randint
 
list1 = []
# generate random size of list
n = randint(10,20)


# generate random list1
for i in range(n):
    list1.append(randint(0,9))

print('Случайный список: ',list1)

#Список частот
list2 = [0]*10

for x in list1:
    list2[x]+=1

# Вывод частот
for i in range(len(list2)):
    print(f"{i} : {list2[i]}")










# # importing the required packages and libraries 
# from telnetlib import X3PAD
# from scipy.optimize import curve_fit 
# from numpy import array, exp 
# import matplotlib.pyplot as plt 
# import numpy as np
# #from scipy.interpolate import interp1d
# from scipy.integrate import quad

# import os
# import sys


# N = 200 
# m = 4
# x = []
# for j in range(m):
#     x.append( [ np.sin(5*np.pi*(i/N)**j) for i in range(N)])

# y = [ np.sin(i) for i in range(N)]

# n_bins = 20
# plt.figure(figsize=[11,6])


# plt.subplot(1, 1, 1)
# for j in range(m-2):
#     plt.hist(x[j],bins = n_bins,histtype ='step')
# # plt.hist(x[-1],bins = n_bins,histtype ='barstacked',label='1')
# # plt.hist(x[-2],bins = n_bins,histtype ='stepfilled',label='2')
# # histtype = bar', 'barstacked', 'step', 'stepfilled'}
# plt.hist(y,bins = n_bins,histtype ='step')
# plt.xlabel(r'$\theta$')
# plt.legend()

# plt.show()














