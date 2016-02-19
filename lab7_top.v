module lab7_top(SW, LEDR, CLOCK_50);

  input [9:0] SW;
  input CLOCK_50;
  //input [3:0] KEY;
  output [9:0] LEDR; 

  wire reset = SW[0];
  //wire clk = CLOCK_50;
  //wire clk = ! KEY[0]; 

  cpu physical( reset, CLOCK_50, LEDR );


endmodule
     



