module cpu_tb;
 
  reg reset, clk;
  wire [9:0] r0ToDE1;
  cpu DUT(reset, clk, r0ToDE1 );

  initial begin
    clk = 0; #10;
    forever begin
      clk = 1; #10;
      clk = 0; #10;
    end
  end

  initial begin

  reset = 1;
  #100;
  reset = 0;
  //#5000;
  //#50000;
  #1000000;
  $stop;
  end
endmodule

