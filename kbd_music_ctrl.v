/* FSM for interfacing ASCII codes received from the keyboard with music control*/

module kbd_music_ctrl(clk, music_ctrl_clk, kbd_received_ascii_code, update_code, increment, pause, restart);

input clk, music_ctrl_clk, update_code;
input [7:0] kbd_received_ascii_code;
output increment, pause, restart;

// valid keys
parameter character_E = 8'h45;
parameter character_D = 8'h44;
parameter character_B = 8'h42;
parameter character_F = 8'h46;
parameter character_R = 8'h52;

// states
parameter incHigh_pauseHigh 	= 3'b000; 		// starting state
parameter incLow_pauseLow 		= 3'b110;
parameter incLow_pauseHigh 		= 3'b100;
parameter incHigh_pauseLow		= 3'b010;

// same as above states, but restart is high
parameter r_incHigh_pauseHigh  	= 3'b001;
parameter r_incLow_pauseLow 	= 3'b111;
parameter r_incLow_pauseHigh 	= 3'b101;
parameter r_incHigh_pauseLow 	= 3'b011;

reg [2:0] state = incHigh_pauseHigh;

// output assignments adjusted so that starting state can be encoded with all zeroes
assign increment = ~state[2];
assign pause 	 = ~state[1];
assign restart	 = state[0];

wire E_PRESSED, D_PRESSED, B_PRESSED, F_PRESSED, R_PRESSED;

assign E_PRESSED = (character_E == kbd_received_ascii_code);
assign D_PRESSED = (character_D == kbd_received_ascii_code);
assign B_PRESSED = (character_B == kbd_received_ascii_code);
assign F_PRESSED = (character_F == kbd_received_ascii_code);
assign R_PRESSED = (character_R == kbd_received_ascii_code);
reg R_GET;  // signal to prevent song from constantly restarting 

wire synced_music_ctrl_clk;

async_trap_and_reset sync_music_ctrl_clk
(.async_sig(music_ctrl_clk),
.outclk(clk),
.out_sync_sig(synced_music_ctrl_clk),
.auto_reset(1'b1),
.reset(1'b1));

always @(posedge synced_music_ctrl_clk, posedge update_code) begin
	if (update_code) R_GET <= 1'b0; 	// allows R to be repeatedly pressed consecutively to restart music, even though
										// kbd_received_ascii_code does not technically change value
	else begin
		case(state)
			// restart lasts one cycle
			r_incLow_pauseLow:   state <= incLow_pauseLow;
			r_incLow_pauseHigh:  state <= incLow_pauseHigh;
			r_incHigh_pauseLow:  state <= incHigh_pauseLow;
			r_incHigh_pauseHigh: state <= incHigh_pauseHigh;

			incHigh_pauseHigh:	if (R_PRESSED & !R_GET) {state, R_GET} <= {r_incHigh_pauseHigh, 1'b1}; 			
								else if (E_PRESSED) state <= incHigh_pauseLow;
								else if (B_PRESSED) state <= incLow_pauseHigh;
								else state <= incHigh_pauseHigh;

			incLow_pauseLow: 	if (R_PRESSED & !R_GET) {state, R_GET} <= {r_incLow_pauseLow, 1'b1};
								else if (D_PRESSED) state <= incLow_pauseHigh;
								else if (F_PRESSED) state <= incHigh_pauseLow;
								else state <= incLow_pauseLow;

			incLow_pauseHigh:	if (R_PRESSED & !R_GET) {state, R_GET} <= {r_incLow_pauseHigh, 1'b1};
								else if (E_PRESSED) state <= incLow_pauseLow;
								else if (F_PRESSED) state <= incHigh_pauseHigh;
								else state <= incLow_pauseHigh;

			incHigh_pauseLow: 	if (R_PRESSED & !R_GET) {state, R_GET} <= {r_incHigh_pauseLow, 1'b1};
								else if (D_PRESSED) state <= incHigh_pauseHigh;
								else if (B_PRESSED) state <= incLow_pauseLow;
								else state <= incHigh_pauseLow;

			default: {state, R_GET} <= {incHigh_pauseHigh, 1'b0};
		endcase
	end
end

endmodule