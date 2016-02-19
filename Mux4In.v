/*
One-hot select MUX with 4 inputs
*/

module Mux4In(a3, a2, a1, a0, s, b) ;
  parameter k = 1;
  input [k-1:0] a0, a1, a2, a3;  // inputs
  input [3:0]   s ; // one-hot select
  output[k-1:0] b ;
  reg [k-1:0] b ;

  always @(*) begin
    case(s) 
      4'b0001: b = a0;
      4'b0010: b = a1;
      4'b0100: b = a2;
      4'b1000: b = a3;
      default: b =  {k{1'bx}} ;
    endcase
  end
endmodule
