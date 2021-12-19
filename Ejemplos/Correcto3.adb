Procedure Hola is
	num1, num2, op,result,menu:Integer;
	Function factorial (n: In Out Integer) return Integer
	is
	begin
		if n=0 then
			return 1;
		end if;
		return n * factorial(n-1);
	end factorial;

	Function potencia (base,exponente: In Out Integer) return Integer
	is
	begin
		if exponente = 0 then
			return 1;
		elsif exponente<0 then
			return potencia(base,exponente+1);
		else 
			return base * potencia(base,exponente-1);
		end if;
	end potencia;
begin
	menu:= 0;
	loop
		Put("1 factorial\n");
		Put("2 Potencia\n");
		Put("3 Salir\n");
		Get(op);
		if op = 1 then
			Put("Ingrese Numero ");
			Get(num1);
			result := factorial(num1);
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
		exit when menu=1;
	end loop;
end Hola;
