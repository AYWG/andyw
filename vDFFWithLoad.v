/*
Register with load enable
*/

module vDFFWithLoad (clk, load, in, out);
  parameter n = 1;  // width
  input clk;
  input load;
  input [n-1:0] in; //outside input
  wire [n-1:0] regin; //input to register
  output [n-1:0] out;
  reg [n-1:0] out;

  assign regin = load ? in : out;

  always @(posedge clk)
    out = regin;
endmodule