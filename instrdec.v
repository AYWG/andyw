module instrdec (IR, nsel, opcode, op, ALUop, sximm5, sximm8,
		shift, readnum, writenum, cond);

	input [15:0] IR;
	input [2:0] nsel;

	output [2:0] opcode, readnum, writenum, cond; 
	output [1:0] op, ALUop, shift; 
	output [15:0] sximm5, sximm8;

	wire [4:0] imm5 = IR[4:0];
	wire [7:0] imm8 = IR[7:0];
	wire [2:0] Rn = IR[10:8];
	wire [2:0] Rd = IR[7:5];
	wire [2:0] Rm = IR[2:0];
	wire [2:0] selReg;

	assign opcode = IR[15:13];
	assign op = IR[12:11];
	assign ALUop = IR[12:11];
	assign sximm5 = imm5[4] ? {{11{1'b1}}, imm5} : {{11{1'b0}}, imm5};
	assign sximm8 = imm8[7] ? {{8{1'b1}}, imm8} : {{8{1'b0}}, imm8}; 
	assign shift = IR[4:3];
	Mux3In #(3) regMux (Rn, Rd, Rm, nsel, selReg);
	assign readnum = selReg;
	assign writenum = selReg;
	assign cond = IR[10:8];

endmodule


