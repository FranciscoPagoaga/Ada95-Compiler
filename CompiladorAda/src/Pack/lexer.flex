
package Pack; 
import java_cup.runtime.Symbol;


%%
%public
%cup
%char
%column
%ignorecase
%line
%unicode
%class Lexer
%int

//set basico
letra = [a-zA-Z]
digito = [0-9]
espacio = \t | \f | " " | \r | \n
signos="?"|"!"

//palabras reservadas, no importa si esta en mayuscula o minsucula, o una mezcla de ambos
if = ["i"|"I"]["f"|"F"]
then = ["t"|"T"]["h"|"H"]["e"|"E"]["n"|"N"]
else = ["e"|"E"]["l"|"L"]["s"|"S"]["e"|"E"]
elsif = ["e"|"E"]["l"|"L"]["s"|"S"]["i"|"I"]["f"|"F"]
end = ["e"|"E"]["n"|"N"]["d"|"D"]
for = ["f"|"F"]["o"|"O"]["r"|"R"]
while = ["w"|"W"]["h"|"H"]["i"|"I"]["l"|"L"]["e"|"E"]
loop = ["l"|"L"]["o"|"O"]["o"|"O"]["p"|"P"]
procedure = ["p"|"P"]["r"|"R"]["o"|"O"]["c"|"C"]["e"|"E"]["d"|"D"]["u"|"U"]["r"|"R"]["e"|"E"]
function = ["f"|"F"]["u"|"U"]["n"|"N"]["c"|"C"]["t"|"T"]["i"|"I"]["o"|"O"]["n"|"N"]
return = ["r"|"R"]["e"|"E"]["t"|"T"]["u"|"U"]["r"|"R"]["n"|"N"]
begin = ["b"|"B"]["e"|"E"]["g"|"G"]["i"|"I"]["n"|"N"]
is = ["i"|"I"]["s"|"S"]
when = ["w"|"W"]["h"|"H"]["e"|"E"]["n"|"N"]
and = ["a"|"A"]["n"|"N"]["d"|"D"]
or = ["o"|"O"]["r"|"R"]

//identificador
id = {letra}+("_"({letra}|{digito})+)*({letra}|{digito})*

//values
true = ["t"|"T"]["r"|"R"]["u"|"U"]["e"|"E"]
false =  ["F"|"f"]["a"|"A"]["l"|"L"]["s"|"S"]["e"|"E"]
punto = "."
signo_negativo = "-"
numero = ({signo_negativo}|""){digito}+(({punto}{digito}+)|"")

//delimitadores
op_declaracion = ":"
op_asignacion = ":="
op_rel = "=" | "/=" | "<" | "<=" | ">" | ">="
op_suma = "+" | "-"
op_mult = "*" | "/"
parentesis_izquierdo = "("
parentesis_derecho = ")"
puntoycoma = ";"
coma = ","

put = "Put"
get = "Get"

int = "Integer"
bool = "Boolean"
float = "Float"

//values
str = "\"" ({letra} | {digito} | {signos} | {espacio}  )* "\""

%%

<YYINITIAL>{

    {procedure}     { return new Symbol(sym.PROCEDURE, yycolumn, yyline, yytext()); }
    {function}      { return new Symbol(sym.FUNCTION, yycolumn, yyline, yytext()); }
    {return}        { return new Symbol(sym.RETURN, yycolumn, yyline, yytext()); }
    {is}            { return new Symbol(sym.IS, yycolumn, yyline, yytext()); }
    {begin}         { return new Symbol(sym.BEGIN, yycolumn, yyline, yytext()); }
    {end}           { return new Symbol(sym.END, yycolumn, yyline, yytext()); }
    {if}            { return new Symbol(sym.IF, yycolumn, yyline, yytext()); }
    {then}          { return new Symbol(sym.THEN, yycolumn, yyline, yytext()); }
    {else}          { return new Symbol(sym.ELSE, yycolumn, yyline, yytext()); }
    {elsif}         { return new Symbol(sym.ELSIF, yycolumn, yyline, yytext()); }
    {for}           { return new Symbol(sym.FOR, yycolumn, yyline, yytext()); }
    {while}         { return new Symbol(sym.WHILE, yycolumn, yyline, yytext()); }
    {loop}          { return new Symbol(sym.LOOP, yycolumn, yyline, yytext()); }
    {when}          { return new Symbol(sym.WHEN, yycolumn, yyline, yytext()); }
    {and}           { return new Symbol(sym.AND, yycolumn, yyline, yytext()); }
    {or}            { return new Symbol(sym.OR, yycolumn, yyline, yytext()); }
    {put}           { return new Symbol(sym.PUT, yycolumn, yyline, yytext()); }
    {get}           { return new Symbol(sym.GET, yycolumn, yyline, yytext()); }


    {op_rel}        { return new Symbol(sym.OPREL, yycolumn, yyline, yytext()); }
    {op_suma}       { return new Symbol(sym.OPSUM, yycolumn, yyline, yytext()); }
    {op_mult}       { return new Symbol(sym.OPMULT, yycolumn, yyline, yytext()); }
    {parentesis_izquierdo} { return new Symbol(sym.PARIZQ, yycolumn, yyline, yytext()); }
    {parentesis_derecho} { return new Symbol(sym.PARDER, yycolumn, yyline, yytext()); }
    {puntoycoma}            {return new Symbol(sym.PYC, yycolumn, yyline, yytext());}
    {coma}          { return new Symbol(sym.COMA, yycolumn, yyline, yytext()); }
    {str}           { return new Symbol(sym.STR, yycolumn, yyline, yytext()); }  

    {id}            { return new Symbol(sym.ID, yycolumn, yyline, yytext()); } 
    {numero}        { return new Symbol(sym.NUM, yycolumn, yyline, yytext()); }
    {espacio}       {}
    . { System.out.println("token no valido " + yytext()); }




    
}