Procedure Hola is
	num1, num2, op,result,menu:Integer;
	doble: Float;
	Function factorial (n: In Integer) return Integer
	is
	begin
		n:=1; --Asignacion a Parametro modo in
		if n=0.0 then --comparacion int x float
			return 1;
		end if;
		return n * factorial(n-1);
	end factorial;

	Function potencia (base: In Out Integer; exponente: Out Integer) return Integer
	is
	begin
		if exponente = 0 then
			return 1;
		elsif exponente<0 then
			--Usa parametro tipo out en operacion
			return potencia(base,exponente+1);
		else 
			--Usa parametro tipo out en operacion
			return base * potencia(base,exponente-1.0);
		end if;
	end potenciaa; --identificador al final no es el mismo del inicio
	
	Procedure potencia(asd: In Out Float)
	is
	Begin
		Put("Procedimiento"); 
	end potencia;
begin
	doble:= 0; --Float asignado con int
	menu:= doble +0; --Asignacion a int con una operacion int x float
	menu:= 0;
	loop
		Put("1 factorial\n");
		Put("2 Potencia\n");
		Put("3 Salir\n");
		Get(op);
		if op = 1 then
			Put("Ingrese Numero ");
			Get(num1);
			result := factorial(num1,0); --Cantidad incorrecta de params
			Put("El factorial del numero ingresado es de: ");
			Put(result);
			Put("\n");
		elsif op=2 then
			Put("Ingrese Numero Base ");
			Get(num1);
			Put("Ingrese Potencia ");
			Get(num2);
			result:= potencia(num1,num2);
			Put("El resultado es: ");
			Put(result);
			Put("\n");
		elsif op=3 then
			menu:=1;
		end if;
		
	end loop;
end Hola;



