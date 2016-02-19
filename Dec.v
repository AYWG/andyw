/*
Decoder
a - binary input   (n bits wide)
b - one hot output (m bits wide)
*/ 

module Dec(a, b);
  parameter n = 2;
  parameter m = 4;
  
  input [n-1:0] a;
  output [m-1:0] b;

  wire [m-1:0] b = 1<<a;
endmodule
