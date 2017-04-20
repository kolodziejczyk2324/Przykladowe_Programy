#Przemyslaw Kolodziejczyk
#
#Zad 1

using matrixgen
using blocksys

multMatrixAndOnes_writeToFile("A_1000.txt", 0, 1, "output1.txt")
my_GaussianElimination("A_1000.txt", "b_1000.txt", "output2.txt")
my_GaussianEliminationPartialPivoting("A_1000.txt", "b_1000.txt", "output3.txt")

n1, l1, m1 = getLU_fromFile("A_1000.txt")
n2, l2, m2, p2 = getLUPartialPivoting_fromFile("A_1000.txt")

my_GaussianElimination_withLU(m1, n1, l1, "b_1000.txt", "output4.txt")
my_GaussianEliminationPartialPivoting_withLU(m2, n2, l2, p2, "b_1000.txt", "output5.txt")

