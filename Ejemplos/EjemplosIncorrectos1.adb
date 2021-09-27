Procedure Main is
	--Procedimiento con parentesis, pero sin valores de parametros dentro
	Procedure Hola () is begin 
		--Put sin un valor valido dentro
		Put(_);
		
		--Get con valor numerico, en vez de ID
		Get(1);
	--cierra con palabra reservada, no ID
	end Procedure;

begin
	for valor in 1..True loop
		Put("ENtro");
	end loop;
	
	--no hay condicion
	if then 
		Put(1); 
	end if;
	
	Put(1);
end Main;
