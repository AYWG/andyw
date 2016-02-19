module datapath (writenum, write, readnum, 
		clk, loada, loadb, loadc, loads, vsel, 
		asel, bsel, shift, ALUop, 
		reset, msel, mwrite, loadir, IR,
		sximm5, sximm8, r0ToDE1, tsel, incp, execb, cond);
	 
	input asel, bsel, loads, loada, loadb, loadc, write, clk,
	      reset, msel, mwrite, loadir, tsel, incp, execb;
	input [2:0] writenum, readnum, cond;
	input [1:0] shift, ALUop;
	input [15:0] sximm5, sximm8;

	input [3:0] vsel;
	output [15:0] IR;
	output [9:0] r0ToDE1;

	wire [15:0] data_in, data_out,
		    A_out, B_out,
		    ShiftOut, Ain, Bin,
		    ALUtoC, C_out, mdata;
		
	wire [7:0] loadpcmux, pc_in, PC, addr, 
		   pc_next, pc1, pctgt, pcrel;
	wire [2:0] ALUtoStat, status;
	wire taken, loadpc;

	vDFFWithLoad #(16) regA(clk, loada, data_out, A_out);
	vDFFWithLoad #(16) regB(clk, loadb, data_out, B_out);
	vDFFWithLoad #(16) regC(clk, loadc, ALUtoC, C_out);
	vDFFWithLoad #(3) regStatus(clk, loads, ALUtoStat, status);
    	shifter shiftInst(B_out, shift, ShiftOut);
	regfile mainReg(writenum, write, data_in, clk, readnum, data_out, r0ToDE1);
    	alu compute(Ain, Bin, ALUop, ALUtoC, ALUtoStat);
	
	//modifications for lab 6
	Mux4In #(16) datainmux(mdata, sximm8, {8'b0, PC}, C_out, vsel, data_in);
	assign Bin = bsel ? sximm5 : ShiftOut;

	assign Ain = asel ? 16'b0 : A_out;


	//Datapath additions for lab 6
	assign pc_in = reset ? {8'b0} : loadpcmux;
	vDFF #(8) PCreg(clk, pc_in, PC);
	assign addr = msel ? C_out[7:0] : PC;
	RAM #(16,8,"fib2.txt") r(clk, addr, addr, mwrite, B_out, mdata);
	vDFFWithLoad #(16) IRreg(clk, loadir, mdata, IR);

	//Datapath additions for lab 7
	branchunit bu(execb, status, cond, taken);
	assign loadpc = taken | incp;
	assign loadpcmux = loadpc ? pc_next : PC;
	assign pc_next = incp ? pc1 : pctgt;
	assign pc1 = PC + 1;
	assign pctgt = tsel ? pcrel : A_out[7:0];
	assign pcrel = sximm8[7:0] + PC;


endmodule









