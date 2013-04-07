This directory contains the ComponentWorld app's database. Currently this takes
the form of an Apache Derby database (in the `componentSearchDB` subdirectory).

In production use, of course, this database would be outside of the project
directory. It is here now only for purposes of version control while under
development.

To move the database to a different location, edit the `<url>` setting within
Cayenne's DomainNode.driver.xml file in the `src/main/resources/` folder.