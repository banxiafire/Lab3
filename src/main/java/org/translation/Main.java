package org.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    private static String quit = "quit";
    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */

    public static void main(String[] args) {

        // once you finish the JSONTranslator,
        //            you can use it here instead of the InLabByHandTranslator
        //            to try out the whole program!
        Translator translator = new JSONTranslator();
        // Translator translator = new InLabByHandTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        while (true) {
            String country = promptForCountry(translator);
            //  CheckStyle: The String "quit" appears 3 times in the file.
            //  Checkstyle: String literal expressions should be on the left side of an equals comparison
            if (quit.equals(country)) {
                break;
            }
            //          : Once you switch promptForCountry so that it returns the country
            //            name rather than the 3-letter country code, you will need to
            //            convert it back to its 3-letter country code when calling promptForLanguage
            String language = promptForLanguage(translator, country);
            if (quit.equals(language)) {
                break;
            }
            //            Once you switch promptForLanguage so that it returns the language
            //            name rather than the 2-letter language code, you will need to
            //            convert it back to its 2-letter language code when calling translate.
            //            Note: you should use the actual names in the message printed below though,
            //            since the user will see the displayed message.
            CountryCodeConverter conv = new CountryCodeConverter();
            LanguageCodeConverter dictionary = new LanguageCodeConverter();
            String newCountry = conv.fromCountry(country);
            String newLanguage = dictionary.fromLanguage(language);
            System.out.println(country + " in " + language + " is " + translator.translate(newCountry, newLanguage));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if ("quit".equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();
        //  replace the following println call, sort the countries alphabetically,
        //            and print them out; one per line
        //      hint: class Collections provides a static sort method
        //  convert the country codes to the actual country names before sorting
        ArrayList<String> counts = new ArrayList<>();
        CountryCodeConverter conv = new CountryCodeConverter();
        for (String country : countries) {
            counts.add(conv.fromCountryCode(country));
        }
        Collections.sort(counts);
        for (String count:counts) {
            System.out.println(count);
        }

        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {

        // Task: replace the line below so that we sort the languages alphabetically and print them out; one per line
        // Task: convert the language codes to the actual language names before sorting
        CountryCodeConverter conv = new CountryCodeConverter();
        String three = conv.fromCountry(country);
        List<String> languages = translator.getCountryLanguages(three);
        LanguageCodeConverter dictionary = new LanguageCodeConverter();
        ArrayList<String> theWorld = new ArrayList<>();
        for (String language:languages) {
            theWorld.add(dictionary.fromLanguageCode(language));
        }
        Collections.sort(theWorld);
        for (String single: theWorld) {
            System.out.println(single);
        }
        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
