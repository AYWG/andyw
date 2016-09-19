module kbd_music_ctrl_tb;

reg clk, music_ctrl_clk, update_code;
reg [7:0] kbd_received_ascii_code;
wire increment, pause, restart;

parameter character_E = 8'h45;
parameter character_D = 8'h44;
parameter character_B = 8'h42;
parameter character_F = 8'h46;
parameter character_R = 8'h52;

kbd_music_ctrl dut(clk, music_ctrl_clk, kbd_received_ascii_code, update_code, increment, pause, restart);

initial begin
	clk = 0; #5;
	forever begin
		clk = 1; #5;
		clk = 0; #5;
	end
end

initial begin
	music_ctrl_clk = 0; #22;
	forever begin
		music_ctrl_clk = 1; #22;
		music_ctrl_clk = 0; #22;
	end
end

initial begin
	update_code = 0;
	#50;
	kbd_received_ascii_code = character_E;
	update_code = 1;
	#30;
	update_code = 0;
	#500;
	kbd_received_ascii_code = character_R;
	update_code = 1;
	#30;
	update_code = 0;
	#500;
	kbd_received_ascii_code = character_R;
	update_code = 1;
	#30;
	update_code = 0;
	#500;
	kbd_received_ascii_code = character_B;
	update_code = 1;
	#30;
	update_code = 0;
	#500;
	kbd_received_ascii_code = character_F;
	update_code = 1;
	#30;
	update_code = 0;
	#500;
	kbd_received_ascii_code = character_D;
	update_code = 1;
	#30;
	update_code = 0;
	#500;
	$stop;
end

endmodule
