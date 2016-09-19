
`default_nettype none
 `define USE_PACOBLAZE
module 
picoblaze_template
#(
parameter clk_freq_in_hz = 25000000
) (
        output reg[9:0] led,
            inout [7:0] lcd_d,
            output reg lcd_rs,
            output lcd_rw,
            output reg lcd_e,
        input clk,
        input [7:0] input_data,
        input new_value_read,
  input switch9,
  input switch0
           );

  
//--
//------------------------------------------------------------------------------------
//--
//-- Signals used to connect KCPSM3 to program ROM and I/O logic
//--

wire[9:0]  address;
wire[17:0]  instruction;
wire[7:0]  port_id;
wire[7:0]  out_port;
reg[7:0]  in_port;
wire  write_strobe;
wire  read_strobe;
reg  interrupt;
wire  interrupt_ack;
wire  kcpsm3_reset;
reg[9:0] led_next;
//--
//-- Signals used to generate interrupt 
//--
reg[26:0] int_count;
reg event_1hz;

//-- Signals for LCD operation
//--
//--

reg        lcd_rw_control;
reg[7:0]   lcd_output_data;
pacoblaze3 led_8seg_kcpsm
(
                  .address(address),
               .instruction(instruction),
                   .port_id(port_id),
              .write_strobe(write_strobe),
                  .out_port(out_port),
               .read_strobe(read_strobe),
                   .in_port(in_port),
                 .interrupt(interrupt),
             .interrupt_ack(interrupt_ack),
                     .reset(kcpsm3_reset),
                       .clk(clk));

 wire [19:0] raw_instruction;
	
	pacoblaze_instruction_memory 
	pacoblaze_instruction_memory_inst(
     	.addr(address),
	    .outdata(raw_instruction)
	);
	
	always @ (posedge clk)
	begin
	      instruction <= raw_instruction[17:0];
	end

    assign kcpsm3_reset = 0;                       
  
//  ----------------------------------------------------------------------------------------------------------------------------------
//  -- Interrupt 
//  ----------------------------------------------------------------------------------------------------------------------------------
//  --
//  --
//  -- An interrupt that is triggered every time a new value is read from the Flash memory 
//  --
//  --


 always @ (posedge clk or posedge interrupt_ack)  //FF with clock "clk" and reset "interrupt_ack"
 begin
      if (interrupt_ack) //if we get reset, reset interrupt in order to wait for next clock.
            interrupt <= 0;
      else
		begin 
		      if (new_value_read) 
      		      interrupt <= 1;
          		else
		            interrupt <= interrupt;
      end
 end

//  --
//  ----------------------------------------------------------------------------------------------------------------------------------
//  -- KCPSM3 input ports 
//  ----------------------------------------------------------------------------------------------------------------------------------
//  --
//  --
//  -- The inputs connect via a pipelined multiplexer
//  --

 always @ (posedge clk)
 begin
    case (port_id[7:0])
        8'h0:    in_port <= input_data;
        default: in_port <= 8'bx;
    endcase
end
   
//
//  --
//  ----------------------------------------------------------------------------------------------------------------------------------
//  -- KCPSM3 output ports 
//  ----------------------------------------------------------------------------------------------------------------------------------
//  --
//  -- adding the output registers to the processor
//  --
//   

always @(*) begin
    if((switch9) && (!switch0)) begin
        led_next[9] = out_port[0]; 
        casex (out_port[7:1])
           7'bxxxx000: led_next[8:0] = 9'b000000000;
           7'bxxxx001: led_next[8:0] = 9'b000000001;
           7'bxxxx010: led_next[8:0] = 9'b000000011;
           7'bxxxx011: led_next[8:0] = 9'b000000111;
           7'bxxxx100: led_next[8:0] = 9'b000001111;
           7'bxxxx101: led_next[8:0] = 9'b000011111;
           7'bxxxx110: led_next[8:0] = 9'b000111111;
           7'bxxxx111: led_next[8:0] = 9'b001111111;     
           default:    led_next[8:0] = 9'b000000000;  
        endcase
    end
    else if ((!switch9) && (!switch0))begin
        led_next[0] = out_port[0];
        casex (out_port[7:1])
           7'bxxxx000: led_next[9:1] = 9'b000000000;
           7'bxxxx001: led_next[9:1] = 9'b100000000;
           7'bxxxx010: led_next[9:1] = 9'b110000000;
           7'bxxxx011: led_next[9:1] = 9'b111000000;
           7'bxxxx100: led_next[9:1] = 9'b111100000;
           7'bxxxx101: led_next[9:1] = 9'b111110000;
           7'bxxxx110: led_next[9:1] = 9'b111111000;
           7'bxxxx111: led_next[9:1] = 9'b111111100;
           default:    led_next[9:1] = 9'b000000000;
        endcase
    end
    else if((!switch9) && (switch0) ) begin
  led_next[0] = out_port[0];
  led_next[4:1] = out_port[7:4];
  led_next[9:5] = 1'b0;
    end
    else begin
  led_next[9] = out_port[0];
  led_next[8] = out_port[4];
  led_next[7] = out_port[5];
  led_next[6] = out_port[6];
  led_next[5] = out_port[7];
  led_next[4:0] = 1'b0;
    end
end
  
  
  always @ (posedge clk)
  begin

        //LED is port 80 hex 
        if (write_strobe & port_id[7])  //clock enable 
          led <= led_next;
       
//        -- 8-bit LCD data output address 40 hex.
        if (write_strobe & port_id[6])  //clock enable
          lcd_output_data <= out_port;
      
//        -- LCD controls at address 20 hex.
        if (write_strobe & port_id[5]) //clock enable
	  begin
             lcd_rs <= out_port[2];
             lcd_rw_control <= out_port[1];
             lcd_e <= out_port[0];
        end

  end

//  --
//  ----------------------------------------------------------------------------------------------------------------------------------
//  -- LCD interface  
//  ----------------------------------------------------------------------------------------------------------------------------------
//  --
//  -- The LCD will be accessed using the 8-bit mode.  
//  -- lcd_rw is '1' for read and '0' for write 
//  --
//  -- Control of read and write signal

  assign lcd_rw = lcd_rw_control;

//  -- use read/write control to enable output buffers.
  assign lcd_d = lcd_rw_control ? 8'bZZZZZZZZ : lcd_output_data;


endmodule
