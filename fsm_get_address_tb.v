module fsm_get_address_tb;

reg clk, get_addr_clk, increment, pause, restart; 
wire [31:0] curr_addr;

fsm_get_address dut(clk, get_addr_clk, increment, pause, restart, curr_addr);

initial begin
	clk = 0; #5;
	forever begin
		clk = 1; #5;
		clk = 0; #5;
	end
end

initial begin
	get_addr_clk = 0; #22;
	forever begin
		get_addr_clk = 1; #22;
		get_addr_clk = 0; #22;
	end
end

initial begin
	restart = 0;
	increment = 1;
	pause = 0;
	#200;
	increment = 0;
	#200;
	pause = 1;
	#100;
	pause = 0;
	increment = 1;
	#300;
	restart = 1;
	#10;
	restart = 0;
	#200;
	increment = 0;
	#200;
	restart = 1;
	#10;
	restart = 0;
	#1000;
	$stop;
end

endmodule