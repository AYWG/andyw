module branchunit(execb, status, cond, taken);

input execb;
input [2:0] status, cond;

output taken;
reg taken;

always@(*) begin 
  casex ({execb, status, cond})
  //first case is for BL; second case represents B and BX
    {1'b1, 3'bx, 3'b111}   : taken = 1'b1;
    {1'b1, 3'bx, 3'b0}     : taken = 1'b1;
    {1'b1, 3'b1xx, 3'b001} : taken = 1'b1;
    {1'b1, 3'b0xx, 3'b010} : taken = 1'b1;
    {1'b1, 3'bx01, 3'b011} : taken = 1'b1;
    {1'b1, 3'bx10, 3'b011} : taken = 1'b1;
    {1'b1, 3'bx01, 3'b100} : taken = 1'b1;
    {1'b1, 3'bx10, 3'b100} : taken = 1'b1;
    {1'b1, 3'b1xx, 3'b100} : taken = 1'b1;
  default : taken = 1'b0;
  endcase
end

endmodule
