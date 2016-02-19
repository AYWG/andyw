module alu (Ain, Bin, ALUop, out, outToStat);
  // Inputs going into the module.
  input [15:0] Ain;
  input [15:0] Bin;
  input [1:0] ALUop;
  // Outputs leaving the module
  output [15:0] out;
  reg [15:0] out;
  output [2:0] outToStat; 

  always @(*) begin
    case (ALUop)
      2'b00: out = Ain + Bin;
      2'b01: out = Ain - Bin;
      2'b10: out = Ain & Bin;
      2'b11: out = ~Bin;
      default: out = 16'b0000000000000000;
    endcase
  end 
  //zero flag
  assign outToStat[2] = (out == 16'b0000000000000000);
  //negative flag
  assign outToStat[1] = (out[15] == 1);
  //overflow flag
  assign outToStat[0] = (Ain[15] == Bin[15] & out[15] != Ain[15] & ALUop == 2'b00) | (Ain[15] != Bin[15] & out[15] == Bin[15] & ALUop == 2'b01);

endmodule
