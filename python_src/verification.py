# importing the required packages and libraries 
from telnetlib import X3PAD
from scipy.optimize import curve_fit 
from numpy import array, exp 
import matplotlib.pyplot as plt 
import numpy as np
#from scipy.interpolate import interp1d
#from scipy.integrate import quad

import os
import sys










def import_dat_plots(file_name):
    '''
    Исмпортирует из .dat как в для gnuplot данные в список sets c параметрами 
    '''

    f = open(file_name,'r')
    sets = [] # набор наборов 
    params = {}

    isreading_line = False
    for line in f.readlines(): 
        isreading_lastline = isreading_line
        isreading_line = line[0] != '#' and  any( map(str.isdigit,line)) 
        isparams_line = line[0] == '#' and  '=' in line
        if isreading_line and  isreading_lastline is False:
            set =[]
        if isreading_lastline and  isreading_line is False:
            sets.append(set)    

        if isreading_line  :
            set.append([ float(var) for var in line.split()])
            # print(set)
        if isparams_line:
            key , value = line[1:].split('=')
            key  = key.replace(' ','')
            value = float(value)
            # print(key,value)
            # e = input()
            params[key] = value


    if  isreading_line:
            sets.append(set) 

#    [np.transpose(set) for set in sets]
    # return [list(np.transpose(set)) for set in sets]
    return  (sets,params)



def fun_ftheta(theta,H,a):
    def fun(x):
        return np.cos(x)/(x+0.0000000000001)
    
    # print(quad(fun,theta,np.pi/2))

    return (2/np.pi*(H-a)/H + a/H* quad(fun,theta,np.pi/2)[0])/(2/np.pi)*1/90




def inParallepiped(point_coord,p1,p2):
    '''
    point_coord = [ x ,y ,z ]
    p1 = [ x1 y1 z1]
    p2 = [ x2 ,y2, z2]
    '''

    return all([ (min( p1[i], p2[i]) < point_coord[i]< max( p1[i], p2[i])) for i in range(len(point_coord))])
  

#name_dir = '../Export/'





#name_txt = "Verification3.txt"

#name_txt = "Verification2.txt"

#name_txt = "Verification.txt"

#name_txt = "D_5.0x5.0x5.0_1.0x1.0x0.001_without_Wall.txt"
#name_txt = "D_5.0x5.0x5.0_1.0x1.0x0.001_with_Wall.txt"
#
#
name_txt =  "D_5.0x5.0x5.0_3.0x3.0x0.001_without_Wall.txt"


name_txt = "tests/D_20.0x20.0x20.0_1.0x1.0x1.0_.txt"


#name_txt =  "D_5.0x5.0x5.0_3.0x3.0x0.001_with_Wall.txt"







sets, params = import_dat_plots(name_txt)



title = (f"Область: {params['LIMIT_X']}x{params['LIMIT_Y']}x{params['LIMIT_Z']}" + "; "
                    f"Элементы: {params['a']}x{params['b']}x{params['c']}"    )


X = params['LIMIT_X']
Y = params['LIMIT_Y']
Z = params['LIMIT_Z']


# print(sets)


x = [ 0 ]*len(sets[0][0])


for j in range(len(x)):
    x[j] = []

print(x)

delta = 0

for i in sets:
#    print(len(i), i[0][4])
#    if (10 <i[0][2] < 90 ) or True :
    if inParallepiped([i[0][0],i[0][1],i[0][2]], [delta,delta,delta],[X - delta,Y - delta,Z - delta]):
        for j in range(len(x)):
            
            x[j].append((i[0][j]))
    
#        print(len(x[0])) 
    

print(len(x[0]))




#for j in range(len(x)):
#    fig = plt.figure()
#    plt.hist(x[j])
#
#plt.show()
#
#


n_bins = 50
# n_bins = 100

plt.figure(figsize=[11, 9])




#plt.suptitle( 'Со Стенкой' + '\n' +title)
plt.suptitle( 'Без Стенки' + '\n' +title)

plt.subplot(2, 3, 1)
plt.hist(x[0],bins = n_bins)
plt.xlabel('x')

plt.subplot(2, 3, 2)
plt.hist(x[1],bins = n_bins)
plt.xlabel('y')

plt.subplot(2, 3, 3)
plt.hist(x[2],bins = n_bins)
plt.xlabel('z')


plt.subplot(2, 3, 4)
plt.hist(x[3],bins = n_bins)
plt.xlabel(r'$\phi$')


plt.subplot(2, 3, 5)
plt.hist(x[4],bins = n_bins)
plt.xlabel(r'$\theta$')


plt.subplot(2, 3, 6)
plt.hist(x[5],bins = n_bins)
plt.xlabel(r'$\gamma$')

# plt.savefig('Fig_'+name_txt.replace('.txt','.png'))
plt.show()














