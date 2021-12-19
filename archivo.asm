.data
_num1:	.word	0
_num2:	.word	0
_op:	.word	0
_result:	.word	0
_menu:	.word	0
_msg0:	.asciiz	"1 Suma\n"
_msg1:	.asciiz	"2 Resta\n"
_msg2:	.asciiz	"3 Multiplicacion\n"
_msg3:	.asciiz	"4 Division\n"
_msg4:	.asciiz	"5 Salir\n"
_msg5:	.asciiz	"Ingrese Numero "
_msg6:	.asciiz	"\n"

.text
.globl main

main: 

_Hola: 
li $t0, 0
sw $t0,_menu
etiq165: 
lw $t0, _menu
li $t1,1
bne $t0, $t1, etiq166
b etiq164
etiq166: 
li $v0, 4
la $a0, _msg0
syscall
li $v0, 4
la $a0, _msg1
syscall
li $v0, 4
la $a0, _msg2
syscall
li $v0, 4
la $a0, _msg3
syscall
li $v0, 4
la $a0, _msg4
syscall
li $v0, 5
syscall
sw $v0, _op
lw $t0, _op
li $t1,1
beq $t0, $t1, etiq174
b etiq175
etiq174: 
li $v0, 4
la $a0, _msg5
syscall
li $v0, 5
syscall
sw $v0, _num1
li $v0, 4
la $a0, _msg5
syscall
li $v0, 5
syscall
sw $v0, _num2
lw $t0, _num1
lw $t1, _num2
add $t2, $t0, $t1
sw $t2,_result
li $v0, 1
lw $a0, _result
syscall
li $v0, 4
la $a0, _msg6
syscall
b etiq173
etiq175: 
lw $t0, _op
li $t1,2
beq $t0, $t1, etiq184
b etiq185
etiq184: 
li $v0, 4
la $a0, _msg5
syscall
li $v0, 5
syscall
sw $v0, _num1
li $v0, 4
la $a0, _msg5
syscall
li $v0, 5
syscall
sw $v0, _num2
lw $t0, _num1
lw $t1, _num2
sub $t2, $t0, $t1
sw $t2,_result
li $v0, 1
lw $a0, _result
syscall
li $v0, 4
la $a0, _msg6
syscall
b etiq173
etiq185: 
lw $t0, _op
li $t1,3
beq $t0, $t1, etiq194
b etiq195
etiq194: 
li $v0, 4
la $a0, _msg5
syscall
li $v0, 5
syscall
sw $v0, _num1
li $v0, 4
la $a0, _msg5
syscall
li $v0, 5
syscall
sw $v0, _num2
lw $t0, _num1
lw $t1, _num2
mul $t2, $t0, $t1
sw $t2,_result
li $v0, 1
lw $a0, _result
syscall
li $v0, 4
la $a0, _msg6
syscall
b etiq173
etiq195: 
lw $t0, _op
li $t1,4
beq $t0, $t1, etiq204
b etiq205
etiq204: 
li $v0, 4
la $a0, _msg5
syscall
li $v0, 5
syscall
sw $v0, _num1
li $v0, 4
la $a0, _msg5
syscall
li $v0, 5
syscall
sw $v0, _num2
lw $t0, _num1
lw $t1, _num2
div $t2, $t0, $t1
sw $t2,_result
li $v0, 1
lw $a0, _result
syscall
li $v0, 4
la $a0, _msg6
syscall
b etiq173
etiq205: 
lw $t0, _op
li $t1,5
beq $t0, $t1, etiq214
b etiq173
etiq214: 
li $t0, 1
sw $t0,_menu
b etiq173
etiq173: 
b etiq165
etiq164: 
li $v0, 10
syscall
