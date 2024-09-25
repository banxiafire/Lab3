package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    // Task: pick appropriate instance variable(s) to store the data necessary for this class
    private final Map<String, Map<String, Object>> countries = new HashMap<>();
    private final Map<String, Map<String, Object>> throughname = new HashMap<>();

    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                Map<String, Object> eachc = new HashMap<>();
                String[] allinfo = line.strip().split("\t");
                eachc.put("id", Integer.parseInt(allinfo[allinfo.length - 1]));
                eachc.put("alpha3", allinfo[allinfo.length - 2].toLowerCase());
                eachc.put("alpha2", allinfo[allinfo.length - 2 - 1].toLowerCase());
                String[] newArray = new String[allinfo.length - 2 - 1];
                System.arraycopy(allinfo, 0, newArray, 0, allinfo.length - 2 - 1);
                StringBuilder na = new StringBuilder();
                for (String s : newArray) {
                    na.append(s);
                    na.append(" ");
                }
                String name = na.toString().trim();
                eachc.put("en", name);
                String three = allinfo[allinfo.length - 2].toLowerCase();
                this.countries.put(three, eachc);
                this.throughname.put(name, eachc);
            }
            // use lines to populate the instance variable(s)

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        // Task: update this code to use an instance variable to return the correct value
        return this.countries.get(code).get("en").toString();
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        // Task: update this code to use an instance variable to return the correct value
        return this.throughname.get(country).get("alpha3").toString();
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        // Task: update this code to use an instance variable to return the correct value.
        return this.countries.size();
    }
}
