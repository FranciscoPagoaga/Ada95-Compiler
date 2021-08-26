%%
%unicode
%class Lexer
%int

//set basico
letra = [a-zA-Z]
numero = [0-9]
espacio = \t | \f | " " | \r | \n
signos="?"|"!"

//palabras reservadas, no importa si esta en mayuscula o minsucula, o una mezcla de ambos
if = ["i"|"I"]["f"|"F"]
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

//identificador
id = {letra}+("_"({letra}|{numero})+)*({letra}|{numero})*

//values
booleanValue=["t"|"T"]["r"|"R"]["u"|"U"]["e"|"E"] | ["F"|"f"]["a"|"A"]["l"|"L"]["s"|"S"]["e"|"E"]
integerValue={numero}*
floatValue={numero}*"."{numero}{1,38}

//delimitadores
op_declaracion = ":"
op_asignacion = ":="
op_rel = "=" | "/=" | "<" | "<=" | ">" | ">="
op_suma = "+" | "-"
op_mult = "*" | "/"
parentesis_izquierdo = "("
parentesis_derecho = ")"
puntoycoma = ";"

int = "Integer"
bool = "Boolean"
float = "Float"

//values
str = "\"" ({letra} | {numero} | {signos} | {espacio}  )* "\""

%%

<YYINITIAL>{
    {if}            {System.out.println("<IF> "+yytext());}
    {else}          {System.out.println("<ELSE> "+yytext());}
    {elsif}         {System.out.println("<ELSIF> "+yytext());}
    {end}           {System.out.println("<END> "+yytext());}
    {for}           {System.out.println("<FOR> "+yytext());}
    {while}         {System.out.println("<WHILE> "+yytext());}
    {loop}          {System.out.println("<loop> "+yytext());}
    {puntoycoma}    {System.out.println("<puntoycoma> "+yytext());}
    {procedure}     {System.out.println("<procedure> "+yytext());}
    {booleanValue} {System.out.println("<booleanValue> "+yytext());}
    {integerValue}  {System.out.println("<integerValue> "+yytext());}
    {floatValue}    {System.out.println("<floatValues> "+yytext());}
    {id}            {System.out.println("<id> "+yytext());} 
    {espacio}       {}
    .       {}
}