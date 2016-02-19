// Implementation of the register file
// for the simple RISC Machine

module regfile (writenum, write, data_in, clk, readnum, data_out, r0ToDE1);
  input [2:0] writenum;
  input write;
  input [15:0] data_in;
  input clk;
  input [2:0] readnum;
  output [15:0] data_out;
  
  wire [7:0] writeWhichReg;
  wire [7:0] readWhichReg; //one-hot select
  wire [7:0] writeSelReg; //load
  wire [15:0] reg0out;
  wire [15:0] reg1out;
  wire [15:0] reg2out; 
  wire [15:0] reg3out;
  wire [15:0] reg4out;
  wire [15:0] reg5out;
  wire [15:0] reg6out;
  wire [15:0] reg7out;
  output [9:0] r0ToDE1;

  Dec #(3, 8) dec38write(writenum, writeWhichReg);
  Dec #(3, 8) dec38read(readnum, readWhichReg); 

  //Each output bit of  is AND'ed with write
  assign writeSelReg[0] = writeWhichReg[0] & write;
  assign writeSelReg[1] = writeWhichReg[1] & write;
  assign writeSelReg[2] = writeWhichReg[2] & write;
  assign writeSelReg[3] = writeWhichReg[3] & write;
  assign writeSelReg[4] = writeWhichReg[4] & write;
  assign writeSelReg[5] = writeWhichReg[5] & write;
  assign writeSelReg[6] = writeWhichReg[6] & write;
  assign writeSelReg[7] = writeWhichReg[7] & write;

  `define SW 16

  vDFFWithLoad #(`SW) R0(clk, writeSelReg[0], data_in, reg0out );
  vDFFWithLoad #(`SW) R1(clk, writeSelReg[1], data_in, reg1out );
  vDFFWithLoad #(`SW) R2(clk, writeSelReg[2], data_in, reg2out );
  vDFFWithLoad #(`SW) R3(clk, writeSelReg[3], data_in, reg3out );
  vDFFWithLoad #(`SW) R4(clk, writeSelReg[4], data_in, reg4out );
  vDFFWithLoad #(`SW) R5(clk, writeSelReg[5], data_in, reg5out );
  vDFFWithLoad #(`SW) R6(clk, writeSelReg[6], data_in, reg6out );
  vDFFWithLoad #(`SW) R7(clk, writeSelReg[7], data_in, reg7out );
  
  assign r0ToDE1[9:0] = reg0out[9:0];


  Mux8In #(`SW) SelRegforRead(reg7out, reg6out, reg5out, 
                              reg4out, reg3out, reg2out, reg1out, 
                              reg0out, readWhichReg, data_out);

endmodule




