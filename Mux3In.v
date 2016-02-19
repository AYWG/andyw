/*
One-hot select MUX with 3 inputs
*/

module Mux3In(a2, a1, a0, s, b) ;
  parameter k = 1;
  input [k-1:0] a0, a1, a2;  // inputs
  input [2:0]   s ; // one-hot select
  output[k-1:0] b ;
  reg [k-1:0] b ;

  always @(*) begin
    case(s) 
      3'b001: b = a0;
      3'b010: b = a1;
      3'b100: b = a2;
      default: b =  {k{1'bx}} ;
    endcase
  end
endmodule
