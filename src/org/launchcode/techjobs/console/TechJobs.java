package org.launchcode.techjobs.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by LaunchCode
 */
public class TechJobs {
// allows for user input
    private static Scanner in = new Scanner(System.in);
// this is the main function that sets a hashmap called columnChoices then enters key value pairs with the value = to the string of its thing
    public static void main (String[] args) {

        // Initialize our field map with key/name pairs

        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");
// This part makes a new hashmap called actionChoices that has key value pair with search and list
        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");
        // I ran a println to see what these Hashmaps currently contains. it contains all the  choices for the user
//        System.out.println(columnChoices);
//        System.out.println(actionChoices);

        // Allow the user to search until they manually quit
        while (true) {
            // this presents choices for the user based on the actionChoice HashMap
            String actionChoice = getUserSelection("View jobs by:", actionChoices);

            if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);
                // these are direction based on the user choices
                if (columnChoice.equals("all")) {
                    // goes down to line 119 passing in jobs data
                    printJobs(JobData.findAll());
                } else {
                    // here it goes into the JobData file and runs the find all method passing in columnChoice the result is an arraylist of strings that is a list of just one specific column...
                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println("\nSearch term: ");
                String searchTerm = in.nextLine();

                if (searchField.equals("all")) {
                    System.out.println("*********");
                    System.out.println();
                    System.out.println("*********");
                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                }
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary
    //HashMap with String keys and String values. How is this used? used to determine the size of choice keys
    //What will this HashMap contain when the method runs? either the action choices, or column choices
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        Integer choiceIdx;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        Integer i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println("\n" + menuHeader);

            // Print available choices
            for (Integer j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            choiceIdx = in.nextInt();
            in.nextLine();

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs
    //
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {

        if (someJobs.size() < 1){
            System.out.println("No Results");
        }else{
            for (int j= 0; j < someJobs.size(); j++) {
                System.out.println("\n*****");
                for (int i =0; i< someJobs.get(j).keySet().size(); i++){
                    Object keys = someJobs.get(j).keySet().toArray()[i];
                    Object values = someJobs.get(j).values().toArray()[i];

                    System.out.println(keys + ": " + values);
                }
                System.out.println("*****");
            }
        }
    }
}
