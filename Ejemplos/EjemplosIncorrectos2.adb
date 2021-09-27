Procedure Main is
	--Procedimiento con parentesis, pero sin valores de parametros dentro
	Procedure Hola (asd,asd: Integer prueba, asd: Boolean) is begin 
		
		loop 
			if 12 then 
			--Error en put
				Put("asd);
			end if;
		end loop;
	end Hola;

begin
	for valor in 1..True loop
		Put("ENtro");
	end loop;
	
	--no hay condicion
	if then 
		Put(1);
	--Sin condicion
	elsif then  
		Put(asd);
	else
		--Else sin content
	end if;
	
	Put(1);
end Main;
