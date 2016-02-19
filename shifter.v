module shifter(in, shift, out);
  input [15:0] in;
  input [1:0] shift;
  output [15:0] out;
  reg [15:0] out;

  always @(*) begin
    case (shift)
      2'b00: out = in;
      //logical left shift; LSB replaced with 0
      2'b01: out = in << 1;
      //logical right shift; MSB replaced with 0
      2'b10: out = in >> 1;
      //arithmetic right shift; empty MSB replaced with copy of original MSB 
      //note: operator only performs arithmetic shift if first operant is signed
      2'b11: out = $signed(in) >>> 1;
      default: out = {16{1'bx}};
    endcase
  end
endmodule
