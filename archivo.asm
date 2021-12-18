.data
_asd:	.word	0

.text
.globl main

main: 

_Hello: 
li $t0, 5
move $a0, $t0
jal _Hello.prueba
move $v0,$t0
sw $t0,_asd
li $v0, 1
lw $a0, _asd
syscall
li $v0, 10
syscall
_Hello.Hola: 
sw $fp, -4($sp) 
sw $ra, -8($sp) 
move $fp, $sp 
sw $s0 , -12($sp)
move $s0, $a0
sub $sp, $sp, 20
lw $t0, -16($fp)
move $t1, $s0
add $t2, $t0, $t1
sw $t2,-20($fp)
_fin_Hello.Hola:
move $sp,$fp 
lw $s0 , -12($fp)
lw $ra, -8($fp) 
lw $fp, -4($fp) 
jr $ra
_Hello.prueba: 
sw $fp, -4($sp) 
sw $ra, -8($sp) 
move $fp, $sp 
sw $s0 , -12($sp)
move $s0, $a0
sub $sp, $sp, 16
li $t0,1
li $t1, 2
add $t2, $t0, $t1
sw $t2,-16($fp)
li $t0,1
li $t1, 2
add $t2, $t0, $t1
move $v0, $t2
b _fin_Hello.prueba
_fin_Hello.prueba:
move $sp,$fp 
lw $s0 , -12($fp)
lw $ra, -8($fp) 
lw $fp, -4($fp) 
jr $ra
