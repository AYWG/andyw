s1 = serial('COM3', 'BaudRate', 9600, 'Parity', 'none', 'DataBits', 8, 'StopBits', 1, 'FlowControl', 'none'); %creates a serial port object
fopen(s1); %opens s1
x = 1:100; %sets variable x to be a value between 1 and 100
y = sin(2*pi*x/1000); %sets y as an arbitrary function
hLine = plot(x,y); %creates a plot of x and y
stripchart('Initialize',gca) %initializes stripchart and the axis used
while 1 %infinite loop
    val = fscanf(s1); %read the value on the arduino serial monitor
    y = sscanf(val, '%f'); %store it in variable y
    if isa(y, 'double') %check if the value is of type double
        stripchart('Update',hLine(1),y); %if it is, update the graph with this value
    end
    x = x+1; %increment x by 1
    if x > 100 %reset x to 1if it is above its upper bound
        x = 1;
    end
    title('Plot of Ambient Light Level vs Time') %add title of graph
    xlabel('Time(ms)') %add x-axis label
    ylabel('Ambient Light Level(mV)') %add y-axis label
end
fclose(s1); %close s1