package school.parent.model.mapper;



import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements the AttributeConverter interface to convert a List of Integer
 * objects to a single String for storage in the database and vice versa.
 */
@Converter
public class IntegerListConverter implements AttributeConverter<List<Integer>, String> {

    // Define a separator to use when converting the list to a string
    private static final String SEPARATOR = ",";

    /**
     * Converts a List of Integer objects to a single String to be stored in the database.
     * @param attribute the List of Integer objects to be converted
     * @return a String representation of the List, with elements separated by commas
     */
    @Override
    public String convertToDatabaseColumn(List<Integer> attribute) {
        // If the list is null or empty, return an empty string
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        // Convert each Integer to a String, join them with the SEPARATOR, and return the resulting String
        return attribute.stream()
                .map(String::valueOf)  // Convert each Integer to String
                .collect(Collectors.joining(SEPARATOR));  // Join all Strings with the SEPARATOR
    }

    /**
     * Converts a String from the database back into a List of Integer objects.
     * @param dbData the String representation of the List from the database
     * @return a List of Integer objects
     */
    @Override
    public List<Integer> convertToEntityAttribute(String dbData) {
        // If the string is null or empty, return an empty list
        if (dbData == null || dbData.isEmpty()) {
            return List.of();
        }
        // Split the string by the SEPARATOR, convert each substring to an Integer, and collect them into a List
        return Arrays.stream(dbData.split(SEPARATOR))  // Split the string by SEPARATOR into an array of strings
                .map(Integer::valueOf)  // Convert each string to an Integer
                .collect(Collectors.toList());  // Collect the results into a List
    }
}
