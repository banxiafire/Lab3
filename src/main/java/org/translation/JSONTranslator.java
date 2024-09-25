package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    private final Map<String, Map<String, Object>> countries = new HashMap<>();
    private final Map<String, Map<String, String>> languages = new HashMap<>();

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            // pick appropriate instance variables for this class
            JSONArray info = new JSONArray(jsonString);
            for (int i = 0; i < info.length(); i++) {
                JSONObject blow = info.getJSONObject(i);
                String name = blow.getString("alpha3");
                Map<String, Object> others = new HashMap<>();
                Set<String> keys = blow.keySet();
                for (String key : keys) {
                    others.put(key, blow.get(key));
                }
                this.countries.put(name, others);
                keys.remove("id");
                keys.remove("alpha2");
                keys.remove("alpha3");
                Map<String, String> lan = new HashMap<>();
                for (String key : keys) {
                    lan.put(key, blow.getString(key));
                }
                this.languages.put(name, lan);

            }
            // Use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        Set<String> keys = this.languages.get(country).keySet();
        return new ArrayList<>(keys);
    }

    @Override
    public List<String> getCountries() {
        // return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        return new ArrayList<>(this.countries.keySet());
    }

    @Override
    public String translate(String country, String language) {
        // : complete this method using your instance variables as needed
        return this.languages.get(country).get(language);
    }
}
