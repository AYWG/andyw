//`timescale 1ns / 1ps
module controller( input clk,
		input pause,
		input clk_desired,
		input [22:0] mem_address,			//from other FSM
		output flash_mem_write,			//ignore 
		output [5:0]flash_mem_burstcount,       //ignore
		output reg flash_mem_read,
		output reg [22:0] flash_mem_address,		//goes into flash controller
		output [31:0] flash_mem_writedata,      //ignore 
		output reg [3:0] flash_mem_byteenable,
		input flash_mem_waitrequest,
		input [31:0] flash_mem_readdata,	 
		input flash_mem_readdatavalid,
		output reg [7:0] audio_sample,
		input reverse,
		output reg new_value_read
		);

wire [3:0] state;
reg [3:0] next_state = 4'b0000;
reg [7:0] audio_flash;

//MSB of state determines if on first or second sample of address  
parameter S0  = 3'b000;
parameter S1  = 3'b001;
parameter S2  = 3'b010;
parameter S3  = 3'b011;
parameter S4  = 3'b100;
parameter S5  = 3'b101;

//provided in the pdf  
assign flash_mem_write = 1'b0;
assign flash_mem_writedata = 32'b0;
assign flash_mem_burstcount = 6'b000001;  

widereg #(4) STATE(.indata(next_state), .outdata(state), .inclk(clk));

always @(*) begin
   casex ({state,clk_desired,flash_mem_readdatavalid}) 
    {S0,1'b0,1'bx}: {flash_mem_read, flash_mem_byteenable, new_value_read} = (reverse) ? {1'b0,4'b1100, 1'b0} : {1'b0,4'b0011, 1'b0};	// idle state0 and select bits
    {S0,1'b1,1'bx}: {next_state,flash_mem_read, flash_mem_address, new_value_read} = {S1,1'b1,mem_address, 1'b0};			//initiate read first bits
    {S1,1'b1,1'b0}: {flash_mem_read, new_value_read} = {1'b0, 1'b0};					// idle state1
    {S1,1'b1,1'b1}: {audio_flash, new_value_read} = {(flash_mem_byteenable == 4'b0011) ? flash_mem_readdata[15:8] : flash_mem_readdata[31:24], !pause};	//read
    {S1,1'b0,1'bx}: {next_state,flash_mem_read, flash_mem_byteenable, new_value_read} = {S2,1'b0,~flash_mem_byteenable, 1'b0};	// select bits
    {S2,1'b0,1'bx}: {flash_mem_read, new_value_read} = {1'b0, 1'b0};					// idle state2
    {S2,1'b1,1'bx}: {next_state,flash_mem_read, new_value_read} = {S3,1'b1, 1'b0};							//initiate read second bits
    {S3,1'b1,1'b0}: {flash_mem_read, new_value_read} = {1'b0, 1'b0};					// idle state3
    {S3,1'b1,1'b1}: {audio_flash, new_value_read} = {(flash_mem_byteenable == 4'b0011) ? flash_mem_readdata[15:8] : flash_mem_readdata[31:24], !pause};	//read
    {S3,1'b0,1'bx}: {next_state, new_value_read} = {S0, 1'b0};
    default: {next_state,flash_mem_read,flash_mem_address,flash_mem_byteenable, new_value_read} = {33{1'b0}};
   endcase
end

always @(posedge clk_desired)
	audio_sample <= pause ? 8'b00000000 : audio_flash;

endmodule


//When waitrequest is asserted, master control signals to the slave are to remain constant
//When readdatavalid is asserted, indicates that the readdata signal contains valid data