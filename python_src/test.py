from random import randint
 
# generate random size of list
n = randint(10,20)


# generate random list1
list1 = []
for i in range(n):
    list1.append(randint(0,9))

print('Случайный список: ',list1)

#Список частот
list2 = [0]*10
#Заполняем список частот ( код в стиле Python)
for x in list1:
    list2[x]+=1

# Aльтернативный вариант в стиле С++
# for i in range(len(list1)):
#     list2[list1[i]]+=1


# Вывод частот
for i in range(len(list2)):
    print(f"{i} : {list2[i]}")
