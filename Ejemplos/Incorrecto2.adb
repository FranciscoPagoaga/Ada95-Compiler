Procedure Hola is
	num1, num2, op,result,menu:Integer;
	
	Function Suma (num1,num2:In Out Integer) Return Integer
	is
		result: Integer;
	begin
		result := num1 + num2;
		--No hay return en funcion
	end Suma;
	
	Procedure Resta (num1,num2:In Out Integer)
	is
		result: Integer;
	begin
		result := num1 - num2;
		return result; --Procedimiento no debe de tener return
	end Resta;
	
	Function Mul (num1,num2:In Out Integer) Return Integer 
	is
		result: Integer;
		cont: Float;
	begin
		result:=0;
		num2:= num2+1;
		For cont in 1..num2 loop
			result:=result + num1;
		end loop;
		return result; 
	end Mul;
	
	Function Div (num1,num2:In Out Integer) Return Integer
	is
		result: Integer;
	begin
		result := num1 / num2;
		return result;
	end Div;
begin
	menu:= 0;
	while menu/=1 loop
		Put("1 Suma\n");
		Put("2 Resta\n");
		Put("3 Multiplicacion\n");
		Put("4 Division\n");
		Put("5 Salir\n");
		Get(op);
		if op = 1 then
			Put("Ingrese Numero ");
			Get(num1);
			Put("Ingrese Numero ");
			Get(num2);
			result:= Suma(num1,num2);
			Put(result);
			Put("\n");
		elsif op=2 then
			Put("Ingrese Numero ");
			Get(num1);
			Put("Ingrese Numero ");
			Get(num2);
			result:= Resta(num1, num2);
			Put(result);
			Put("\n");
		elsif op=3 then
			Put("Ingrese Numero ");
			Get(num1);
			Put("Ingrese Numero ");
			Get(num2);
			result:= Mul(num1,num2);
			Put(result);
			Put("\n");
		elsif op=4 then
			Put("Ingrese Numero ");
			Get(num1);
			Put("Ingrese Numero ");
			Get(num2);
			result:= Div(num1,num2);
			Put(result);
			Put("\n");
		elsif op=5 then
			menu:=1;
		end if;
	end loop;	
end Hola;
