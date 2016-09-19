module controller_tb;
		reg clk;				//done
		reg pause;					//easy
		reg clk_desired;			//done
		reg [22:0] mem_address;			
		wire flash_mem_write;			 
		wire [5:0]flash_mem_burstcount;       
		wire flash_mem_read;
		wire [22:0] flash_mem_address;
		wire [31:0] flash_mem_writedata;       
		wire [3:0] flash_mem_byteenable;
		reg flash_mem_waitrequest;		//doesnt matter
		reg [31:0] flash_mem_readdata;		
		reg flash_mem_readdatavalid;
		wire [7:0] audio_sample;
		reg reverse;					//easyish

controller dut(clk,
	pause,
		clk_desired,
		mem_address,			//from other FSM
		 flash_mem_write,			//ignore 
		flash_mem_burstcount,       //ignore
		 flash_mem_read,
		 flash_mem_address,
		 flash_mem_writedata,      //ignore 
		flash_mem_byteenable,
		 flash_mem_waitrequest,
		flash_mem_readdata,	 
		flash_mem_readdatavalid,
		audio_sample,
		reverse
);

initial begin
	clk = 0;
	#10;
	forever begin
		clk = 1; #10;
		clk = 0; #10;
	end
end

initial begin 
	clk_desired = 0;
	flash_mem_readdatavalid = 0;
        #500;
	forever begin
		clk_desired = 1;
		#60; flash_mem_readdatavalid = 1;
		#20; flash_mem_readdatavalid = 0;
		#420;
		clk_desired = 0; #500;
	end
end

initial begin 
	mem_address = 50;
	#2000;
	forever begin
		mem_address = mem_address+1; 
		#2000;
	end
end

initial begin
	#560;
	flash_mem_readdata = 32'b00000000_00000000_00000000_00000000;
	#1000;
	flash_mem_readdata = 32'b00001000_00000000_00000000_00000000;
	#1000;
	forever begin
		if (reverse) begin
			flash_mem_readdata = flash_mem_readdata + 32'b00000001_00000000_00000000_00000000;
			#1000;
			flash_mem_readdata = flash_mem_readdata + 32'b00000000_00000000_00000001_00000000;
			#1000;
		end
		else begin
			flash_mem_readdata = flash_mem_readdata + 32'b00000000_00000000_00000001_00000000;
			#1000;
			flash_mem_readdata = flash_mem_readdata + 32'b00000001_00000000_00000000_00000000;
			#1000;
		end
	end
end

initial begin
	pause = 0;
	reverse = 0;
	flash_mem_waitrequest = 0;
	#10000;
	pause = 1;
        #6000;
	reverse = 1;
	#8000;
	pause = 0; 
	#10000;
	$stop;
end

endmodule