import os

#os.system("rm build/*  ")
#os.system("javac --source-path src -d build  src/Main.java ")
#

# os.system("java -cp build Main 11 11 11 1 2 1 tests/ ")


# os.system("java -cp build Main 20 20 20 1 1 1 tests/  10000 ")





#os.system("java -cp build Main 10 0.001 1 0.7 0.001 0.001 tests/  5 500 ")

# os.system("java -cp build Main 100 100 10 5 5 0.001 tests/ 5000 2 ")



#   new work
# os.system("java -cp build Main 100 100 10 3 3 0.001 tests/ 5 5000 ")



# os.system("java -cp build Main 300 300 10 1 1 0.001 tests/ 1 900000 ")


# os.system("java -cp build Main 300 300 10 1 1 0.0011 tests/ 1000000 1 ")

# os.system("java -cp build Main 20 2 5 5 1 2 tests/ 1 10 ")


args = [200,200,10,10,10,2]

args = [500,500,10,10,10,2]

N = 4000
iter = 1



args = [5000,5000,10,10,10,2]

N = 100000
iter = 1


mode = 2

if mode == 0:

    args = [50,50,10,10,10,2]

    N = 30
    iter = 1
    os.system(f"java -cp build Main {args[0]} {args[1]} {args[2]} {args[3]} {args[4]} {args[5]} tests/ {iter} {N} ")



if mode == 1:



    args = [200,200,10,10,10,2]
    args = [500,500,10,10,10,2]
    N = 4000
    iter = 1



    args = [5000,5000,10,10,10,2]

    N = 100000
    iter = 1

    os.system(f"java -cp build Main {args[0]} {args[1]} {args[2]} {args[3]} {args[4]} {args[5]} tests/ {iter} {N} ")
    # os.system("java -cp build Main 200 200 10 10 10 2 tests/ 20 500 ")

    name = f"tests/D_{args[0]}.0x{args[1]}.0x{args[2]}.0_{args[3]}.0x{args[4]}.0x{args[5]}.0_.txt"
    # os.system("java -cp build Main 300 300 10 3 3 0.001 tests/ 1 125000 ")

    # os.system(f"python3 python_src/analyse.py 0 {name} ")
    os.system(f"python3 python_src/analyse.py 2 {name} ")
    os.system(f"python3 python_src/analyse.py 3 {name} ")
    os.system(f"python3 python_src/analyse.py 4 {name} ")


if mode == 2:



    args = [500,500,100,100,100,5]

    N = 180
    iter = 1

    # os.system(f"java -cp build Main {args[0]} {args[1]} {args[2]} {args[3]} {args[4]} {args[5]} tests/ {iter} {N} ")
    # os.system("java -cp build Main 200 200 10 10 10 2 tests/ 20 500 ")

    name = f"tests/D_{args[0]}.0x{args[1]}.0x{args[2]}.0_{args[3]}.0x{args[4]}.0x{args[5]}.0_.txt"
    os.system("java -cp build Main 300 300 10 3 3 0.001 tests/ 1 125000 ")

    # os.system(f"python3 python_src/analyse.py 0 {name} ")
    os.system(f"python3 python_src/analyse.py 2 {name} ")
    os.system(f"python3 python_src/analyse.py 3 {name} ")
    os.system(f"python3 python_src/analyse.py 4 {name} ")



# os.system("java -cp build Main 300 300 10 5 5 0.001 tests/ 5 45000 ")

# os.system("java -cp build Main 100 100 10 5 5 0.001 tests/ 5 5000 ")
# os.system("java -cp build Main 100 100 10 1 1 0.001 tests/ 5 50000 ")











# os.system("java -cp build Main 20 20 20 1 1 1 tests/  1000000 ")

# os.system("java -cp build Main 20 20 20 1 1 1 tests/ ")

# os.system("java -cp build Main 11 11 11 1 2 10 tests/ ")

#os.system("java -cp build Main 21 21 21 1 1 1 tests/ ")

