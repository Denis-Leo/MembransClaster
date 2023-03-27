# importing the required packages and libraries 
from telnetlib import X3PAD
from scipy.optimize import curve_fit 
from numpy import array, exp 
import matplotlib.pyplot as plt 
import numpy as np
#from scipy.interpolate import interp1d
from scipy.integrate import quad

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
  


name_txt =  "D_5.0x5.0x5.0_3.0x3.0x0.001_without_Wall.txt"


name_txt = "tests/D_20.0x20.0x20.0_1.0x1.0x1.0_.txt"

name_txt = "tests/D_20.0x1.0x0.001_1.0x0.001x0.001_.txt"


name_txt =  "tests/D_10.0x0.001x1.0_0.03x0.001x0.001_.txt"

# name_txt = "tests/D_10.0x0.001x1.0_0.7x0.001x0.001_.txt"

# name_txt = "tests/old_D_20.0x20.0x20.0_1.0x1.0x1.0_.txt"






name_txt = "tests/D_100.0x100.0x10.0_5.0x5.0x0.001_.txt"

name_txt = "tests/D_100.0x100.0x10.0_1.0x1.0x0.001_.txt"

# name_txt = "tests/D_100.0x100.0x10.0_3.0x3.0x0.001_.txt"



# name_txt = "tests/D_300.0x300.0x10.0_5.0x5.0x0.001_.txt"
# name_txt = "tests/D_300.0x300.0x10.0_3.0x3.0x0.001_.txt"
name_txt = "tests/D_300.0x300.0x10.0_1.0x1.0x0.001_.txt"

# name_txt = "tests/"
# name_txt = "tests/"



if (len(sys.argv) >2):
    name_txt = sys.argv[2] 
# name_txt = 'tests/D_200.0x200.0x10.0_10.0x10.0x2.0_.txt'
    # 0*"tests/D_300.0x300.0x10.0_1.0x1.0x0.001_.txt"



sets, params = import_dat_plots(name_txt)




title = (f"Область: {params['LIMIT_X']}x{params['LIMIT_Y']}x{params['LIMIT_Z']}" + "; "
                    f"Элементы: {params['a']}x{params['b']}x{params['c']}"    )


X = params['LIMIT_X']
Y = params['LIMIT_Y']
Z = params['LIMIT_Z']

N_sets = len(sets[0])
C = N_sets*params['a']*params['b']*params['c']/(params['LIMIT_X']*params['LIMIT_Y']*params['LIMIT_Z'])
title = (f"Область: {params['LIMIT_X']}x{params['LIMIT_Y']}x{params['LIMIT_Z']}" + "; "
                  +'\n' + f"Элементы: {params['a']}x{params['b']}x{params['c']}" 
                  + '\n' + f"C~ {C}"  )



# print(sets)

mode = '3'




print(sys.argv)

mode = sys.argv[1]






if mode == '1':

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






    n_bins = 20
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


    # plt.subplot(2, 3, 6)
    # plt.hist(x[5],bins = n_bins)
    # plt.xlabel(r'$\gamma$')

    plt.savefig(name_txt.replace('.txt','.png'))



    plt.show()



if mode == '2':

    print(len(sets[0]))

    x = []
    for i in range(len(sets[0][0])):
        x.append([])
    
    for i in range(len(x)):
        x[i] = [ s[i] for s in sets[0]]
    points = list(range(len(x[0])))

    


    n_bins = 30
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

    plt.savefig(name_txt.replace('.txt','.png'))



    plt.show()


    # plt.figure(figsize=[11, 9])

    # #plt.suptitle( 'Со Стенкой' + '\n' +title)
    # plt.suptitle( 'Без Стенки' + '\n' +title)

    # plt.subplot(2, 3, 1)
    # plt.scatter(points, x[0])
    # plt.xlabel('x')

    # plt.subplot(2, 3, 2)
    # plt.scatter(points, x[1])
    # plt.xlabel('y')

    # plt.subplot(2, 3, 3)
    # plt.scatter(points, x[2])
    # plt.xlabel('z')


    # plt.subplot(2, 3, 4)
    # plt.scatter(points, x[3])
    # plt.xlabel(r'$\phi$')


    # plt.subplot(2, 3, 5)
    # plt.scatter(points, x[4])
    # plt.xlabel(r'$\theta$')


    # plt.subplot(2, 3, 6)
    # plt.scatter(points, x[5])
    # plt.xlabel(r'$\gamma$')

    # plt.savefig(name_txt.replace('.txt','.png'))



    plt.show()



set_index = 0 


n_layers = 9


N_sets = len(sets[set_index])
print('****',N_sets)


C = N_sets*params['a']*params['b']*params['c']/(params['LIMIT_X']*params['LIMIT_Y']*params['LIMIT_Z'])
title = (f"Область: {params['LIMIT_X']}x{params['LIMIT_Y']}x{params['LIMIT_Z']}" + "; "
                +'\n' + f"Элементы: {params['a']}x{params['b']}x{params['c']}" 
                + '\n' + f"C~ {C}"  )






if mode == '3':
    '''
    Послойный вывод расперделений 
    '''



    # title = (f"Область: {params['LIMIT_X']}x{params['LIMIT_Y']}x{params['LIMIT_Z']}" + "; "
    #               +'\n' + f"Элементы: {params['a']}x{params['b']}x{params['c']}"    )




    # set_index = 0

    # print(len(sets[set_index]))  

    # # *******


    # n_layers = 9

    index_var_layer = 2
    index_var_layer = 2
    
    var = [ s[index_var_layer] for s in sets[set_index]]

    var_min = min(var)
    var_max = max(var)
    var_delta = (var_max-var_min)/(n_layers)


    print(var_min)
    print(var_max)
    print(var_delta)
    

    x = []



    print(sets[set_index][1][index_var_layer])



   
    print(x)
    for i in range(n_layers):
        # x_l[i] = [ s for s in sets[0] if (var_max +i*var_delta <=  s[index_var_layer] <= var_min+(i+1)*var_delta) ]
        x.append( [ s for s in sets[set_index] if ((s[index_var_layer] <= var_min+(i+1)*var_delta)and(s[index_var_layer] >= var_min+(i)*var_delta)) ] )


    for i in range(n_layers):
        x[i] = np.transpose(x[i])



    n_bins = 20
    plt.figure(figsize=[5,2*n_layers])

    plt.suptitle(title)

    for i in range(len(x)):
        print(len(x[i][4]))
        # plt.title(str(i))
        # plt.subplot(int(n_layers/3)+0, 3, i+1)
        plt.subplot(n_layers, 1, i+1)
        plt.hist(x[i][4],bins = n_bins)
        plt.xlabel(r'$\theta$')
    plt.savefig(name_txt.replace('.txt',f'_{set_index}_var_.png'))    

    plt.show()




if mode == '4':
    '''
    Послойный вывод расперделений по времени
    '''


    # *******

    # set_index = 0 


    # n_layers = 9


    # N_sets = len(sets[set_index])
    # print('****',N_sets)


    # C = N_sets*params['a']*params['b']*params['c']/(params['LIMIT_X']*params['LIMIT_Y']*params['LIMIT_Z'])
    # title = (f"Область: {params['LIMIT_X']}x{params['LIMIT_Y']}x{params['LIMIT_Z']}" + "; "
    #               +'\n' + f"Элементы: {params['a']}x{params['b']}x{params['c']}" 
    #               + '\n' + f"C~ {C}"  )

 


# 
    N_delta = N_sets/(n_layers+1*0)
    print('/////',N_delta)





    x = []


    print(x)
    for i in range(n_layers):
        # x.append( sets[1][0:int((i+1)/(n_layers+1)*N_sets)-1])
        x.append( sets[set_index][0:int(N_delta*(i+1))])


    print('****************************')

    for i in range(n_layers):
        x[i] = np.transpose(x[i])
        print(len(x[i]))

    # print(len(x))
    # print(len(x[0]))
    # print(len(x[0][4]))
    # print(len(x[2]))


    n_bins = 20
    plt.figure(figsize=[5,2*n_layers])

    plt.suptitle(title)

    for i in range(len(x)):
        print(len(x[i][4]))
        # plt.title(str(i))
        # plt.subplot(int(n_layers/3)+0, 3, i+1)
        plt.subplot(n_layers, 1, i+1)
        plt.hist(x[i][4],bins = n_bins)
        # plt.hist(x[i][3],bins = n_bins)
        plt.xlabel(r'$\theta$')

    plt.savefig(name_txt.replace('.txt',f'_{set_index}_t_.png'))
    plt.show()





if mode =='0':

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

    # plt.savefig(name_txt.replace('.txt','.png'))



    plt.show()


























