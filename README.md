# Key-Value-Data-Store
File based Data Store to perform Create,Read and Delete key-Value Data in File

This File Contains 1 .jar file
     json-simple-1.1.jar
 
This file need to add in your project in order to use the functionality.
This jar files allow you to CREATE,READ and DELETE the data in the file.

To use all the functionality of this project you can call any the Function by creating "Object" of class "FileStore".
Eg:
     FileStore Obj = new FileStore;
     
Function Supported:

CreateFile()
CreateFile("Specific Path")

AddData("Your-key",#Integer-Value)
AddData("Your-key",#Integer-Value,#Timeout-session-in-seconds)

ReadData("Your-Key")

DeleteData("Your-Key")


Eg.: obj.CreateFile();
     obj.AddData("FreshWorks", 123456);
