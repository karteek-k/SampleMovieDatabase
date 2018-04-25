To Build:

1. Clone the repository
2. Update the MovieService.java file to provide the path for movies_list_sheet.xlsx file (preferrably absoluth path)
3. Build the package using the command "mvn clean package" in the project pom.xml file directory
4. Run the application using java -jar <path to the built jar file> (since the project contains embedded tomcat container it can be run as a jar)
5. If you wish to run the same in your existing container, update the pom.xml file to modify the scope for tomcat starter to be provided and update the packaging to war instea
d of jar.

To Test:

1. To fetch all the records use get request on the movie resource with name=all parameter, and to search for a movie matching a specific string, pass that particular string as name parameter to the same resource request.
2. To delete a record use the delete request along with id of the particular movie as parameter.
3. To create a new record, pass the movie name using movieName and the description using movieDescription parameters to the movie resource using put request.
4. To update a record, pass the entire updated movie record with proper id in the request body along with the push request on the movie resource.

The above are tested and verified using Postman tool.

