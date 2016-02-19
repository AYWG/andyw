/*
One-hot select MUX with 8 inputs
*/

module Mux8In(a7, a6, a5, a4, a3, a2, a1, a0, s, b) ;
  parameter k = 1;
  input [k-1:0] a0, a1, a2, a3, a4, a5, a6, a7 ;  // inputs
  input [7:0]   s ; // one-hot select
  output[k-1:0] b ;
  reg [k-1:0] b ;

  always @(*) begin
    case(s) 
      8'b00000001: b = a0;
      8'b00000010: b = a1;
      8'b00000100: b = a2;
      8'b00001000: b = a3;
      8'b00010000: b = a4;
      8'b00100000: b = a5;
      8'b01000000: b = a6;
      8'b10000000: b = a7;
      default: b =  {k{1'bx}} ;
    endcase
  end
endmodule
