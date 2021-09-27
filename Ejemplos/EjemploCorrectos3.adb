Procedure Hello is
	Procedure Hola (asd: In Out Float) is
		asd,fdvfd,asdasd: Integer; 
	Begin
		Put(1); 
	end Hola;
	
	Function Prueba (asd: Out Float) Return Integer 
	is 
		asd,asd1:Float;
		cvzzc,dasf:Integer;
		prueba1,prueab2:Boolean;
	Begin 
		Put(2); 
		asd:= 1.0;
		Return Prueba(1.20+2.0);
	end Prueba;
	
	function Factorial(A : in Integer) return Integer is
	begin
		  if A = 0 then -- Stop recursion if A <= 0.
		    return 1;
		 else
		   return A * Factorial(A - 1);   -- recurse.
		 end if;
	end Sum;
	
begin
	Put(1);
	Put("num");
	Get(num);
	for variable in 1..2 loop
		Put(1);
		loop
			Put(1); 
			exit when x<2; 
		end loop; 
	END loop;
	
	x:=Factorial(5);
	
	while x<2 loop 
		Get(loquesea);
		Put("Entro al while");
		exit when x<4;
	end loop;
end Hello;
