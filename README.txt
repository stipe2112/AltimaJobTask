IMPORTANT: If when trying to build project "invalid target release" occurs, go to File->Settings->Build, Execution,
Deployment->Compiler->Java Compiler and set Target bytecode version to 1.8
Also set 1.8 version in :   File->Project structure->Project->Project language level
                            File->Project structure->Module->Sources
                            File->Project structure->Module->Dependencies

Running application is simple, default input file is located in src/main/resources and its location is hardcoded in
static final string in line 17 of the Program class where the main method is located. If you want to change the input
file just change the string value to a path of another file.

For given file expected errors exist in some lines and the output is:

Error occurred in line[6]: Robert Stjepan --> Person you're trying to add as a child is already a descendant!
Error occurred in line[7]: Ivan Adam --> Cyclic relationships are not valid!
Error occurred in line[9]: Adam Filip --> Person cannot have more than two parents!
Error occurred in line[13]: Josip Josip --> Person class objects child and parent are the same!
Error occurred in line[16]: Ivan Mate --> Cyclic relationships are not valid!
Ivan
-----|Adam
----------|Stjepan
---------------|Marko
---------------|Robert
--------------------|Mate
-----|Fran
----------|Martin
---------------|Josip
----------|Vice
Luka
-----|Leopold
Ivana
-----|Adam
----------|Stjepan
---------------|Marko
---------------|Robert
--------------------|Mate
-----|Stipe

Running test is straightforward and simple, just like the tests themselves, and there is no need for any code adjustment
to make them work.