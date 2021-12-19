.data
_num1:	.word	0
_num2:	.word	0
_op:	.word	0
_result:	.word	0
_menu:	.word	0
_msg0:	.asciiz	"Ingrese numero: "
_msg1:	.asciiz	"El factorial es: "

.text
.globl main

main: 

_Hola: 
li $v0, 4
la $a0, _msg0
syscall
li $v0, 5
syscall
sw $v0, _num1
li $v0, 4
la $a0, _msg1
syscall
lw $t0, _num1
move $a0, $t0
jal _Hola.factorial
move $t0,$v0
sw $t0,_result
li $v0, 1
lw $a0, _result
syscall
li $v0, 10
syscall
_Hola.factorial: 
sw $fp, -4($sp) 
sw $ra, -8($sp) 
move $fp, $sp 
sw $s0 , -12($sp)
move $s0, $a0
sub $sp, $sp, 12
move $t0, $s0
li $t1,0
beq $t0, $t1, etiq28
b etiq27
etiq28: 
li $t0, 1
move $v0, $t0
b _fin_Hola.factorial
etiq27: 
move $t0, $s0
li $t1, 1
sub $t2, $t0, $t1
move $a0, $t2
jal _Hola.factorial
move $t0,$v0
move $t1, $s0
mul $t2, $t1, $t0
move $v0, $t2
b _fin_Hola.factorial
_fin_Hola.factorial:
move $sp,$fp 
lw $s0 , -12($fp)
lw $ra, -8($fp) 
lw $fp, -4($fp) 
jr $ra
