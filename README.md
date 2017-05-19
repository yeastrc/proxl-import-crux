Crux (search-for-xlinks) to Proxl XML Converter
=====================================================

Use this program to convert the results of a Crux (search-for-xlinks) cross-linking analysis to Proxl XML suitable for import into the proxl web application.

How To Run
-------------
1. Download the [latest release](https://github.com/yeastrc/proxl-import-crux/releases).
2. Run the program ``java -jar crux2ProxlXML.jar`` with no arguments to see the possible parameters.
3. Run the program, e.g., ``java -jar crux2ProxlML.jar -r ./crux-output -f yeast.fasta -l dss -m 138.0680742 -o ./output.proxl.xml``

In the above example, ``output.proxl.xml`` will be created and be suitable for import into proxl.

For more information on importing data into Proxl, please see the [Proxl Import Documentation](http://proxl-web-app.readthedocs.io/en/latest/using/upload_data.html).

More Information About Proxl
-----------------------------
For more information about Proxl, visit http://proxl-ms.org/.
