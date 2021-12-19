Procedure Hola is
	num1, num2, op,result,menu:Integer;
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
			result:=num1+num2;
			Put(result);
			Put("\n");
		elsif op=2 then
			Put("Ingrese Numero ");
			Get(num1);
			Put("Ingrese Numero ");
			Get(num2);
			result:=num1-num2;
			Put(result);
			Put("\n");
		elsif op=3 then
			Put("Ingrese Numero ");
			Get(num1);
			Put("Ingrese Numero ");
			Get(num2);
			result:=num1*num2;
			Put(result);
			Put("\n");
		elsif op=4 then
			Put("Ingrese Numero ");
			Get(num1);
			Put("Ingrese Numero ");
			Get(num2);
			result:=num1/num2;
			Put(result);
			Put("\n");
		elsif op=5 then
			menu:=1;
		end if;
	end loop;	
end Hola;
