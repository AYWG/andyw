/*
Generates the next address to be read, depending
on value of increment, pause, and restart
*/

module fsm_get_address(clk, get_addr_clk, increment, pause, restart, curr_addr);

// audio samples are stored on flash within 
// this address range
parameter first_addr = 32'h0;
parameter last_addr = 32'h7FFFF;

input clk; 						// CLK_50
input get_addr_clk; 			// Clock 11 KHz
input increment;				// increment - HIGH, decrement - LOW
input pause;					// Pause address counter
input restart; 					// Reset address counter
output [31:0] curr_addr;
reg [31:0] curr_addr = first_addr;
wire synced_get_addr_clk;

async_trap_and_reset sync_addr_clk
(.async_sig(get_addr_clk),
.outclk(clk),
.out_sync_sig(synced_get_addr_clk),
.auto_reset(1'b1),
.reset(1'b1));

always @(posedge synced_get_addr_clk, posedge restart) begin
	// reset logic
	if (restart) begin
		if (increment)
			curr_addr <= first_addr;
		else
			curr_addr <= last_addr;
	end
	else begin
		if (increment & !pause) begin
			// check if it has reached last_addr
			if (curr_addr == last_addr)
				curr_addr <= first_addr;
			// otherwise, increment
			else
				curr_addr <= curr_addr + 1;
		end
		else if (!increment & !pause) begin
			// check if it has reached first_addr
			if (curr_addr == first_addr)
				curr_addr <= last_addr;
			// otherwise, decrement
			else
				curr_addr <= curr_addr - 1;
		end
	end
end

endmodule