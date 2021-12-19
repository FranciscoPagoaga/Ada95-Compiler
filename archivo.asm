.data




.text
.globl main

main: 

_Hello: 
sw $t0,_b
sw $t0,_c
lw $t0, _b
sw $t0,_a
etiq5: 
sw $t0,_b
li $t0, 1
add $t1, null, $t0
sw $t1,_a
b etiq5
etiq6: 
li $v0, 10
syscall
