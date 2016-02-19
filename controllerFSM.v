module controllerFSM(clk, reset, opcode, op, nsel, controls, loadir, msel, mwrite, 
		     tsel, incp, execb);

//controls consists of:
//write, loada, loadb, loadc, loads, asel, bsel, vsel
//1 + 1 + 1 + 1 + 1 + 1 + 1 + 4 = 11 bits

  input reset, clk;
  input [2:0] opcode;
  input [1:0] op;

  output [2:0] nsel;
  output [10:0] controls;
  output loadir, msel, mwrite, tsel, incp, execb;

  reg [2:0] nsel;
  reg [10:0] controls;
  reg loadir, msel, mwrite, tsel, incp, execb;

  wire [3:0] present_state, next_state_reset;
  reg [3:0] next_state;

//States
  `define SW 4
  `define ResetPC 4'b0000
  `define LoadIR 4'b0001
  `define UpdatePC 4'b0010
  `define DecodeReadRn 4'b0011
  `define ReadRm 4'b0100
  `define ALU 4'b0101
  `define MOV2 4'b0110
  `define WriteRdOrRn 4'b0111
  `define EffAddtoC 4'b1000
  `define LdrOrStr 4'b1001
  `define ReadRd 4'b1010
  `define StrInMem 4'b1011
  `define WaitForMData 4'b1100
  `define ExBr1 4'b1101
  `define ExBr2 4'b1110
  `define End 4'b1111

//Inputs
  `define Rn 3'b100
  `define Rd 3'b010
  `define Rm 3'b001 
  `define MOVopcode 3'b110
  `define ALUopcode 3'b101
  `define LDRopcode 3'b011
  `define STRopcode 3'b100
  `define BRCopcode 3'b001
  `define BLBXopcode 3'b010
  `define HALTopcode 3'b111
  `define ADD 2'b00
  `define AND 2'b10
  `define MVN 2'b11
  `define CMP 2'b01
  `define firstMOV 2'b10
  `define secondMOV 2'b00
  `define LDR 2'b00
  `define BL 2'b11
  `define BX 2'b00

  vDFF #(`SW) STATE(clk, next_state_reset, present_state);
  assign next_state_reset = reset ? `ResetPC : next_state;

  always @(*) begin
    casex ({opcode, op, present_state})

      {3'bx, 2'bx, `ResetPC} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb} 
				= {`LoadIR, 3'b0,11'b0, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {3'bx, 2'bx, `LoadIR} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}   
				= {`UpdatePC, 3'b0,11'b0, 1'b1, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {3'bx, 2'bx,`UpdatePC} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}   
				= {`DecodeReadRn, 3'b0,11'b0, 1'b0, 1'b0, 1'b0, 1'bx, 1'b1, 1'b0};

      {`ALUopcode, 2'bx, `DecodeReadRn} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb} 
				= {`ReadRm, `Rn, 11'b01000000000, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {`MOVopcode, `secondMOV, `DecodeReadRn} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`ReadRm, 3'b0, 11'b0, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {`MOVopcode, `firstMOV, `DecodeReadRn} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`WriteRdOrRn, 3'b0, 11'b0, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      //Note that op value for LDR is same as STR
      {`LDRopcode, `LDR, `DecodeReadRn} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`EffAddtoC, `Rn, 11'b01000000000, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {`STRopcode, `LDR, `DecodeReadRn} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`EffAddtoC, `Rn, 11'b01000000000, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

//----------------------Additional cases for lab 7
      {`BRCopcode, 2'bx, `DecodeReadRn} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`ExBr1, 3'b0, 11'b0, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {`BLBXopcode, 2'bx, `DecodeReadRn} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`ExBr1, `Rd, 11'b01000000000, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {`BRCopcode, 2'bx, `ExBr1} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`ExBr2, 3'b0, 11'b0, 1'b0, 1'b0, 1'b0, 1'b1, 1'b0, 1'b1};

      {`BLBXopcode, `BL, `ExBr1} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`ExBr2, `Rn, 11'b10000000010, 1'b0, 1'b0, 1'b0, 1'b1, 1'b0, 1'b1};

      {`BLBXopcode, `BX, `ExBr1} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`ExBr2, 3'b0, 11'b0, 1'b0, 1'b0, 1'b0, 1'b0, 1'b0, 1'b1};
      //wait for ram
      {3'bx, 2'bx, `ExBr2} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`LoadIR, 3'b0, 11'b0, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

//----------------------
      {3'bx, 2'bx, `EffAddtoC} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`LdrOrStr, 3'b0, 11'b00010010000, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {`LDRopcode, `LDR, `LdrOrStr} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`WriteRdOrRn, 3'b0, 11'b0, 1'b0, 1'b1, 1'b0, 1'bx, 1'b0, 1'b0};

      {`STRopcode, `LDR, `LdrOrStr} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`ReadRd, 3'b0, 11'b0, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {`STRopcode, `LDR, `ReadRd} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`StrInMem, `Rd, 11'b00100000000, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {`STRopcode, `LDR, `StrInMem} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`WaitForMData, 3'b0, 11'b0, 1'b0, 1'b1, 1'b1, 1'bx, 1'b0, 1'b0};

      {3'bx, 2'bx, `WaitForMData} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`LoadIR, 3'b0, 11'b0, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {`ALUopcode, 2'bx, `ReadRm} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb} 
				= {`ALU, `Rm, 11'b00100000000, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {`MOVopcode, 2'bx, `ReadRm} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb} 
				= {`MOV2, `Rm, 11'b00100000000, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {3'bx, 2'bx, `MOV2} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb} 
				= {`WriteRdOrRn, `Rd, 11'b00010100001, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {3'bx, `ADD, `ALU} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb} 
				= {`WriteRdOrRn, 3'b0, 11'b00010000000, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {3'bx, `AND, `ALU} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`WriteRdOrRn, 3'b0, 11'b00010000000, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {3'bx, `MVN, `ALU} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`WriteRdOrRn, 3'b0, 11'b00010000000, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {3'bx, `CMP, `ALU} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`LoadIR, 3'b0, 11'b00001000000, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {`ALUopcode, 2'bx, `WriteRdOrRn} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}  
				= {`LoadIR, `Rd, 11'b10000000001, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {`MOVopcode, `secondMOV, `WriteRdOrRn} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}  
				= {`LoadIR, `Rd, 11'b10000000001, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {`MOVopcode, `firstMOV, `WriteRdOrRn} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}  
				= {`LoadIR, `Rn, 11'b10000000100, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {`LDRopcode, `LDR, `WriteRdOrRn} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`LoadIR, `Rd, 11'b10000001000, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {`HALTopcode, 2'bx, 4'bx} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`End, 3'b0,11'b0, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};

      {3'bx, 2'bx, `End} : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb}
				= {`End, 3'b0,11'b0, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};
      
      default : {next_state, nsel, controls, loadir, msel, mwrite, tsel, incp, execb} 
				= {`ResetPC, 3'b0,11'b0, 1'b0, 1'b0, 1'b0, 1'bx, 1'b0, 1'b0};
    endcase
  end
endmodule
	
	

	
	
