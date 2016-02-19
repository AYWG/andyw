module cpu (reset, clk, r0ToDE1);

//controls consists of:
//write, loada, loadb, loadc, loads, asel, bsel, vsel
//1 + 1 + 1 + 1 + 1 + 1 + 1 + 4 = 11 bits

  input reset, clk;
  output [9:0] r0ToDE1;
  wire [15:0] IR, sximm5, sximm8;
  wire [10:0] controls;
  wire [2:0] nsel, opcode, readnum, writenum, cond; 
  wire [1:0] op, ALUop, shift;
  wire mwrite, msel, loadir, tsel, incp, execb; 
  
  
  wire write = controls[10];
  wire loada = controls[9];
  wire loadb = controls[8];
  wire loadc = controls[7];
  wire loads = controls[6];
  wire asel = controls[5];
  wire bsel = controls[4];
  wire [3:0] vsel = controls[3:0];

  controllerFSM cfsm(clk, reset, opcode, op, nsel, controls, loadir, msel, mwrite, tsel, incp, execb);
  instrdec IDEC(IR, nsel, opcode, op, ALUop, sximm5, sximm8, shift, readnum, writenum, cond);
  datapath dp (writenum, write, readnum, clk, loada, loadb, loadc, loads, vsel, asel, 
	       bsel, shift, ALUop, reset, msel, mwrite, loadir, IR, sximm5, sximm8, r0ToDE1,
	       tsel, incp, execb, cond);


endmodule