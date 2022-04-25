package org.launchcode.techjobs.console;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LaunchCode
 */
public class JobData {

    private static final String DATA_FILE = "resources/job_data.csv";
    private static Boolean isDataLoaded = false;

    private static ArrayList<HashMap<String, String>> allJobs;

    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *
     * @param field The column to retrieve values from
     * @return List of all of the values of the given field
     */
    // this function is first called on Tech Jobs  on line 47 this calls the loadData method on line 91
    public static ArrayList<String> findAll(String field) {

        // load data, if not already loaded
        loadData();

        ArrayList<String> values = new ArrayList<>();
    // called from TechJobs line 48 , when user chooses to see all jobs column choice is passed in as field(the catagories of the jobs)
        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }

        return values;
    }

    public static ArrayList<HashMap<String, String>> findAll() {

        // load data, if not already loaded
        loadData();

        return allJobs;
    }

    /**
     * Returns results of search the jobs data by key/value, using
     * inclusion of the search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column   Column that should be searched.
     * @param value Value of teh field to search for
     * @return List of all jobs matching the criteria
     */
    // part 2 : make a findByValue method
    public static ArrayList<HashMap<String, String>> findByValue(String value){
        // load data, if not already loaded
        loadData();
        // after loadData() is run the following code populates the data in to alljobs and returns jobs (which is all jobs)
        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs){
            for (String key : row.keySet()){
                String aValue = row.get(key);
                if (aValue.toLowerCase().contains(value.toLowerCase())){
                    jobs.add(row);
                    break;
                }

            }
        }

        return jobs;
    }
    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {

        // load data, if not already loaded
        loadData();
        // after loadData() is run the following code populates the data in to alljobs and returns jobs (which is all jobs)
        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {

            String aValue = row.get(column);

            if (aValue.contains(value)) {
                jobs.add(row);
            }
        }

        return jobs;
    }

    /**
     * Read in data from a CSV file and store it in a list
     */
    // called on line 35 this function looks to see if the isDataLoaded is true if so then kick out of here otherwise

    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }
        // the try opens the CSV file, parses the data what is try?  why not just make a regular method??
        try {

            // Open the CSV file and set up pull out column header info and records
            Reader in = new FileReader(DATA_FILE);
            // pulls the fist line as a header row using the .withFirstRecordAsHeader built in method
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            // gets the records?? not exactly sure how this works???
            // from commons.apache.org List<CVSRecord> is a method designed to parse the CSV input according to the given format and returns the content ats a list of CSV records
            // what is records at this point?? how can i see it?
            List<CSVRecord> records = parser.getRecords();
            //a variable called numberOfColumns records is a list on 107 What is the chain .get and .size do???
            // records is a list created on 109 and .get() and.size() is used like .length to create the number of columns needed
            Integer numberOfColumns = records.get(0).size();
            // declaring the header variables to make a new heads of columns and set it to number of Columns??- setting number of columns is set by the work on line 112.
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            //creation of new array list called all jobs
            // what is this right now? how can I see it?? it should be nothing but an empty arraylist
            allJobs = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }

    }

}
