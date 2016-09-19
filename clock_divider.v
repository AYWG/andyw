`timescale 1ns / 1ps
module clock_divider(inclk,outclk,outclk_Not,div_clk_count,Reset);
    input inclk;
	input Reset;
    output outclk;
	output outclk_Not;
	input[31:0] div_clk_count;
	
	reg [31:0] div_clk_old = 32'b1;
	reg [31:0] q = 32'b0;
	reg outclk = 1'b0;

	assign outclk_Not = ~outclk;

	always @(posedge inclk)
	if (Reset) begin
		// if counter (q) has reached div_clk_old: update div_clk_old, reset counter, and invert outclk
		if (div_clk_old - 1 == q) begin
			{q, div_clk_old, outclk} <= {32'b0,div_clk_count,~outclk};
		end
		// otherwise, keep counting
		else begin
		{q, div_clk_old, outclk} <= {q+1,div_clk_old,outclk};
		end
	end
	// reset counter
	else begin
		{q,div_clk_old,outclk} <= {32'b0,div_clk_count,1'b0};
	end
endmodule