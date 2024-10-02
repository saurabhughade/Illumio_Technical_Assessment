# Illumio_Technical_Assessment
Technical Assessment for Illumio
The answer to the assessment question consists of a singular Java program called FlowLogTags.java. It is a standalone program that does not require any specific environment, dependencies or libraries to compile and execute as long as Java is installed on the machine. This in keeping with the requirements to be very minimal and not utilising any third party code. 

# Assumptions
1. Only the default format is supported by the program. Custom format is not supported.
2. Only version 2 is supported.
3. Path of the "flow_logs.txt" and "lookup_table.csv" is provided in the code, if placed in a different directory than "FlowLogTags.java".
4. The protocols are only between "tcp", "udp" and "icmp". 

# How to compile
Since this is a basic standalone Java program, it can be run on any machine with Java installed. It is assumed that Java has been added to the PATH variable for Windows environment.
1. Place the FlowLogTags.java in any directory of your choosing.
2. "flow_logs.txt" should contain the flow logs and "lookup_table.csv" should contain the lookup table mappings. By default they should be placed in the same directory as the Java file. But if they are placed in a different directory, the file paths should be updated in the Java file accordingly.
3. Open the command prompt in Windows and Terminal in Mac.
4. Navigate to the directory where the FlowLogTags.java resides.
5. Execute "javac FlowLogTags.java" command. This will compile the program and output a class file.
6. Execute "java FlowLogTags.java" command. This will execute the compiled file and output the desired result as output.txt in the path as defined in the code.
