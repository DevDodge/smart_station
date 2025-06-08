package Options;

import Database.MainConnection;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.json.JSONArray;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.Date;

import static Database.MainConnection.Con;

/**
 * The MyOptions class provides a collection of static utility methods
 * that perform various operations related to lists, tables, form fields,
 * alerts, and other functionalities. It serves as an organizational
 * class for commonly used methods to streamline application development.
 */
public class MyOptions {
    
    private static String[] data;

    /**
     * Applies a regex pattern validation to multiple text fields.
     * Only allows text input that matches the provided regular expression pattern.
     *
     * @param regex  The regular expression pattern to validate text input
     * @param fields Variable number of TextField objects to apply the validation to
     */
    public static void applyRegexToFields(String regex, TextField... fields) {
        for (TextField field : fields) {
            field.setTextFormatter(new TextFormatter<String>(change -> {
                String newText = change.getControlNewText();
                if (newText.matches(regex)) {
                    return change;
                }
                return null;
            }));
        }
    }

    /**
     * Updates the static field `data` with the provided array of strings.
     * <p>
     * This method is used to assign a new `String[]` value to the `data` field,
     * replacing any previously stored value. The passed array is directly
     * referenced, so any external modifications to this array will reflect
     * in the field as well.
     *
     * @param Data the array of strings to be set to the `data` field
     */
    public static void setData(String[] Data) {
        data = Data;
    }
    
    /**
     * Converts a given Vector of strings to an ObservableList of strings.
     * <p>
     * This method iterates through the Vector of strings provided as input,
     * adds each string to a newly created ObservableList, and returns the populated ObservableList.
     * The method ensures that the conversion maintains the order of elements as in the original Vector.
     *
     * @param docList the input Vector containing strings to be converted to an ObservableList
     * @return an ObservableList containing all the strings from the input Vector
     */
    public static ObservableList<String> list(Vector<String> docList) {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (String d : docList) {
            list.add(d);
        }
        return list;
    }
    
    /**
     * Retrieves text data from an array of TextField objects and returns it as a String array.
     * Each element in the returned array corresponds to the text content of the respective TextField.
     * <p>
     * The method iterates over the provided TextField objects, extracts their text content,
     * and populates a new String array with these values.
     *
     * @param Fields an array of TextField objects from which text data is to be extracted
     * @return a String array containing the text data from each provided TextField
     */
    public static String[] getFieldsData(TextField... Fields) {
        String[] Data = new String[Fields.length];
        int i = 0;
        for (TextField field : Fields) {
            Data[i] = field.getText();
            i++;
        }
        return Data;
    }
    
    /**
     * Disables the specified buttons by setting their `disable` property to `true`.
     * <p>
     * This method iterates through the provided list of Button objects
     * and applies the `setDisable(true)` method to each one, ensuring
     * all buttons in the argument array become non-interactive.
     *
     * @param btns The array of Button objects to be disabled.
     */
    public static void disableBtns(Button... btns) {
        for (Button b : btns) {
            b.setDisable(true);
        }
    }
    
    /**
     * Enables a list of buttons by setting their disabled property to false.
     * <p>
     * Iterates through an array of Button objects and disables the restriction
     * that prevents user interaction with these buttons.
     *
     * @param btns an array of Button objects that need to be enabled.
     */
    public static void enableBtns(Button... btns) {
        for (Button b : btns) {
            b.setDisable(false);
        }
    }
    
    /**
     * Displays an alert dialog based on the specified type and content.
     * Supports "Warning" and "ConfirmDialog" alert types.
     * <p>
     * If the type is "Warning", a warning alert is displayed.
     * If the type is "ConfirmDialog", a confirmation alert is shown with "OK" and "Cancel" options.
     * This method returns a response based on the user's interaction with the dialog.
     *
     * @param type        the type of alert to display ("Warning" or "ConfirmDialog")
     * @param title       the title of the alert dialog
     * @param HeaderText  the header text for the alert dialog
     * @param ContentText the main message content of the alert dialog
     * @return a string value indicating the user's choice:
     * "NO" for a warning dialog or a confirmation dialog with no "OK" selected,
     * "OK" if "OK" is selected in the confirmation dialog
     */
    public static String showAlert(String type, String title, String HeaderText, String ContentText) {
        String returnMsg = "NO";
        if (type.equalsIgnoreCase("Warning")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText(HeaderText);
            alert.setContentText(ContentText);
            alert.showAndWait();
        } else if (type.equalsIgnoreCase("ConfirmDialog")) {
            Alert alert
                    = new Alert(Alert.AlertType.WARNING,
                    ContentText,
                    ButtonType.OK,
                    ButtonType.CANCEL);
            alert.setTitle(title);
            alert.setHeaderText(HeaderText);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                returnMsg = "OK";
            }
        }
        return returnMsg;
    }
    
    /**
     * Displays a notification indicating incomplete data entry.
     * <p>
     * This method creates and shows a notification at the bottom-right corner of the screen
     * with a predefined title, message, and icon. The notification includes a click handler
     * to perform an action when the notification is clicked.
     * <p>
     * Steps:
     * 1. Creates an ImageView instance using the URL for an icon resource.
     * 2. Configures a notification object with the following properties:
     * - Title: Specifies the notification's title text.
     * - Text: Provides the body text message of the notification.
     * - Graphic: Sets the icon to be displayed in the notification.
     * - Position: Defines the screen position of the notification (BOTTOM_RIGHT).
     * - HideAfter: Specifies the duration after which the notification automatically
     * disappears (6 seconds).
     * - onAction: Adds a click event handler that prints a message to the console
     * when the notification is clicked.
     * 3. Calls the `show` method to display the notification.
     */
    public static void showNullMessage() {
        ImageView icon = new ImageView(MyOptions.class.getResource("/FXMLs/images/Notificaiton4.png").toExternalForm());
        Notifications.create()
                .title("بيانات غير كاملة !!")
                .text("الرجاء ادخال البيانات كاملة")
                .graphic(icon)
                .position(Pos.BOTTOM_RIGHT)
                .hideAfter(Duration.seconds(6))
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Notification Clicked");
                    }
                })
                .show();
    }
    
    /**
     * Displays a notification message indicating an invalid login attempt.
     * <p>
     * This method creates a visual notification with a custom message and icon
     * using the Notifications library. The notification automatically hides after
     * a specific duration.
     *
     * @param Massege The message to be displayed within the notification,
     *                typically explaining the invalid login reason.
     *                <p>
     *                The method executes the following steps:
     *                1. Initializes an ImageView with a predefined icon from the application's resources.
     *                2. Creates a notification instance using the Notifications library.
     *                3. Configures the notification with the specified message, title, icon, and display duration.
     *                4. Displays the notification to the user.
     */
    public static void showInvalidLoginMessage(String Massege) {
        ImageView icon = new ImageView(MyOptions.class.getResource("/FXMLs/images/Notificaiton4.png").toExternalForm());
        Notifications.create()
                .hideAfter(Duration.millis(6000))
                .title(" بيانات غير كاملة!!")
                .text(Massege)
                .graphic(icon)
                .show();
        
    }
    
    /**
     * Displays a custom notification with specified title and message.
     * The notification includes a graphical icon and automatically hides after a fixed duration.
     * <p>
     * This method leverages the `org.controlsfx.control.Notifications` class to show notifications.
     * An image icon is added to the notification for visual representation.
     * <p>
     * Workflow:
     * 1. A new `ImageView` object is created and initialized with the icon resource path.
     * 2. A notification instance is generated using the `Notifications.create` method.
     * 3. The following properties are set for the notification:
     * - Title using the provided `title` parameter.
     * - Text using the provided `Message` parameter.
     * - Icon graphic using the initialized `ImageView` object.
     * - Duration for the notification's visibility (`6000` milliseconds).
     * 4. The notification is displayed by invoking the `show` method.
     *
     * @param title   The title of the notification to display.
     * @param Message The detailed message content to display in the notification.
     */
    public static void showCustomMessage(String title, String Message) {
        ImageView icon = new ImageView(MyOptions.class.getResource("/FXMLs/images/Notificaiton4.png").toExternalForm());
        Notifications.create()
                .hideAfter(Duration.millis(6000))
                .title(title)
                .text(Message)
                .graphic(icon)
                .show();
    }
    
    /**
     * Displays a custom animated dialog with the specified title and message.
     * This method loads a predefined FXML file, initializes the dialog's controller,
     * sets the title and message, and applies animations along with keyboard event handling.
     *
     * @param title   The title text to be displayed in the dialog.
     * @param Message The message text to be displayed in the dialog.
     *                <p>
     *                The method performs the following steps:
     *                1. Instantiates or retrieves a `MessageController` instance by loading the FXML from
     *                the specified location and obtaining its controller.
     *                2. Invokes the `displayMsj` method of the `MessageController` to set the title and message.
     *                3. Applies a predefined animation and adds key event handlers to the root pane of
     *                the `MessageController` to enhance the dialog's appearance and interaction.
     */
//    public static void showCustomAnimatedDialog(String title, String Message) {
//        MessageController c = null;
//        c = Scene.OpenScene_getController("/SubForms/Message/Message.fxml").getController();
//        c.displayMsj(title, Message);
//        Animation.AnimationUtility.applyAnimationAndKeyEvents(c.getRootPane());
//
//    }
    
    /**
     * Masks a given email address to hide its core parts, with the exception of the first and last
     * letters of the username and the domain name, effectively anonymizing the email address.
     * <p>
     * The method performs the following steps:
     * 1. Extracts the username portion of the email by removing the last 10 characters,
     * assuming a fixed domain such as "@gmail.com".
     * 2. Takes the first and last characters of the extracted username for later concatenation.
     * 3. Replaces all characters in the central part of the username (except the first and last)
     * with asterisks (*) to mask the sensitive information.
     * 4. Constructs and returns the anonymized email address by combining the masked username,
     * the retained first and last characters, and appending the domain name.
     *
     * @param Email the original email address to be masked in the format of a Gmail address.
     * @return a masked email address where the core part of the username is replaced with asterisks.
     */
    public static String starEmail(String Email) {
        String oldStr = Email.substring(0, Email.length() - 10);
        char fisrtLetter = oldStr.charAt(0);
        char lastletter = oldStr.charAt(oldStr.length() - 1);
        String oldEmail = oldStr.substring(0, oldStr.length() - 2);
        String staredEmail = oldEmail.replaceAll(".", "*");
        return fisrtLetter + staredEmail + lastletter + "@gmail.com";
    }
    
    /**
     * Generates a random six-digit integer within a specified range.
     * The method calculates a random number between 111111 (inclusive) and 999999 (exclusive).
     * <p>
     * Steps:
     * 1. Define the starting range (111111) and ending range (999999).
     * 2. Use Math.random() to generate a random double between 0 (inclusive) and 1 (exclusive).
     * 3. Multiply the random value by the end range value.
     * 4. Add the start range value to ensure the result falls within the defined range.
     * 5. Cast the result to an integer and return it.
     *
     * @return A randomly generated six-digit integer between 111111 and 999999.
     */
    public static int generateRandomCode() {
        int s = 111111;//Start Range
        int e = 999999;//End Range
        return s + (int) (Math.random() * e);
    }
    
    /**
     * Generates a random integer within a specified range.
     *
     * @param s The start of the range (inclusive).
     * @param e The range size to add to the start value.
     * @return A randomly generated integer between s (inclusive) and s + e (exclusive).
     * <p>
     * The method works by:
     * 1. Using Math.random() to generate a random double value between 0 (inclusive) and 1 (exclusive).
     * 2. Multiplying the random value by the range size e to scale the result.
     * 3. Casting the scaled result to an integer, discarding the fractional part.
     * 4. Adding the start value s to shift the range accordingly.
     */
    public static int getRandom(int s, int e) {
        return s + (int) (Math.random() * e);
    }
    
    /**
     * Calculates the total number of columns present in the specified TableView.
     * <p>
     * This method iterates through the columns of the given TableView and counts
     * them until an exception is encountered, which signifies the end of the column list.
     *
     * @param table the TableView whose columns are to be counted
     * @return the total count of columns in the given TableView
     */
    public static int getTableColumns(TableView<?> table) {
        ObservableList<? extends TableColumn<?, ?>> col;
        col = table.getColumns();
        int Num = 0;
        boolean go = true;
        for (int i = 0; go; i++) {
            try {
                col.get(i);
                Num++;
            } catch (Exception ex) {
                go = false;
            }
        }
        return Num;
    }
    
    /**
     * Retrieves the data from all columns of a specified table row and returns it as an array of strings.
     * Each element in the returned array corresponds to the data in a column of the table at the specified row index.
     *
     * @param table    the TableView from which data is retrieved
     * @param rowIndex the index of the row whose data is to be extracted
     * @return an array of strings representing the data in each column of the specified row
     * - If a cell contains null, an empty string is returned for that column.
     * - If the row index is out of bounds, an empty string is returned for all columns.
     * - If an unexpected exception occurs, "Error" is returned for the affected columns.
     */
    public static String[] getTableCellData(TableView<?> table, int rowIndex) {
        ObservableList<? extends TableColumn<?, ?>> columns = table.getColumns();  // No cast needed
        int numberOfCols = columns.size();
        String[] data = new String[numberOfCols];
        
        for (int i = 0; i < numberOfCols; i++) {
            try {
                Object cellData = columns.get(i).getCellData(rowIndex);
                // Convert the cell data to a string
                data[i] = cellData != null ? cellData.toString() : "";
            } catch (IndexOutOfBoundsException ex) {
                // Handle cases where rowIndex is out of bounds
                data[i] = "";
            } catch (Exception e) {
                // Catch any other exceptions and handle accordingly
                data[i] = "Error";
                e.printStackTrace();
            }
        }
        return data;
    }
    
    /**
     * Checks if a given value exists in a specified column of a TableView.
     * Iterates through all rows in the table and retrieves the data for the specified column
     * using an auxiliary method. Compares the specified value with the column data for each row.
     *
     * @param table the TableView to be searched, containing rows of data
     * @param value the value to search for in the specified column
     * @param colNo the zero-based index of the column to search
     * @return true if the value is found in the specified column, otherwise false
     */
    public static boolean ifInTable(TableView<?> table, String value, int colNo) {
        int TableRowNumbers = table.getItems().size();
        for (int i = 0; i < TableRowNumbers; i++) {
            String[] data = getTableCellData(table, i);
            String patient_id = data[colNo];
            if (value.equals(patient_id)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Searches for a specific value in a given column of a TableView and returns the row index if the value is found.
     * <p>
     * The method iterates over the rows of the TableView, retrieving the data of each row. It then compares the value
     * at the specified column index with the provided value. If a match is found, the corresponding row index is returned.
     * If no match is found, the method returns -1.
     *
     * @param table The TableView object where the search will be performed.
     * @param value The value to search for within the specified column.
     * @param colNo The zero-based index of the column where the search will be conducted.
     * @return The zero-based row index of the first match if the value is found, otherwise -1.
     */
    public static int ifInTableReturnIndex(TableView<?> table, String value, int colNo) {
        int TableRowNumbers = table.getItems().size();
        for (int i = 0; i < TableRowNumbers; i++) {
            String[] data = getTableCellData(table, i);
            String colVal = data[colNo];
            if (value.equals(colVal)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Calculates the number of distinct values present in a specific column of a TableView.
     * The method iterates through all rows in the specified column, tracks unique values,
     * and counts their occurrences without duplicates.
     *
     * @param table the TableView object whose column is being analyzed.
     * @param colNo the index of the column (0-based) to analyze for distinct values.
     * @return the count of unique values found in the specified column.
     */
    public static int numOfvaluesInCol(TableView<?> table, int colNo) {
        int num = 0;
        String AddedVal = "";
        int TableRowNumbers = table.getItems().size();
        for (int i = 0; i < TableRowNumbers; i++) {
            String ColData = getTableCellData(table, i)[colNo];
            if (!AddedVal.contains(ColData)) {
                AddedVal = AddedVal + " " + ColData;
                num++;
            }
        }
        return num;
    }
    
    /**
     * Calculates the sum of all numeric values in a specified column of a TableView.
     * The method iterates through each row of the TableView, retrieves the data from the given column,
     * converts it to an integer, and computes the cumulative sum.
     *
     * @param table the TableView from which data is retrieved. It must not be null.
     * @param colNo the zero-based index of the column whose values will be summed.
     * @return the total sum of the values in the specified column. Returns 0 if the table is empty or column contains no valid numeric data.
     * @throws NumberFormatException if any cell in the specified column cannot be converted to an integer.
     */
    public static int calcSumOfColumn(TableView<?> table, int colNo) {
        int sum = 0;
        int tableRowNumbers = table.getItems().size();
        
        for (int i = 0; i < tableRowNumbers; i++) {
            // Retrieve the cell value and parse it as a double
            String cellData = getTableCellData(table, i)[colNo];
            if (cellData != null && !cellData.trim().isEmpty()) {
                try {
                    double colData = Double.parseDouble(cellData);
                    sum += colData;
                } catch (NumberFormatException e) {
                    // Log or print a warning if needed, but continue processing other rows
                    System.err.println("Invalid numeric value in column " + colNo + ": " + cellData);
                }
            }
        }
        return sum;
    }
    
    
    /**
     * Calculates the sum of values in a specific column of a TableView, considering only rows
     * with distinct values in another specified column.
     * <p>
     * Steps:
     * 1. Initialize a counter for the sum and a string to track distinct values from the specified column.
     * 2. Iterate through all rows of the TableView.
     * 3. Retrieve the value of the target column (to be added to the sum) and the value of the
     * check column (to check for distinctiveness) for the current row.
     * 4. Check if the value from the check column has already been encountered.
     * - If not, add the target column's value to the sum and mark the check column's value as encountered.
     * 5. Return the calculated sum after completing the iteration.
     *
     * @param table             The TableView object containing the data.
     * @param colNo             The index of the column whose values will be added to the sum.
     * @param CheckValueOfColNO The index of the column to check for distinct values.
     * @return The sum of values in the specified column for rows with distinct values in the check column.
     */
    public static int distinctRowValueToCalcSumOfColumn(TableView<?> table, int colNo, int CheckValueOfColNO) {
        double num = 0;
        String CheckedValues = "";
        int TableRowNumbers = table.getItems().size();
        for (int i = 0; i < TableRowNumbers; i++) {
            double ColData = Double.parseDouble(getTableCellData(table, i)[colNo]);
            String Checking_Value = getTableCellData(table, i)[CheckValueOfColNO];
            if (!CheckedValues.contains(Checking_Value)) {
                num = num + ColData;
                CheckedValues = CheckedValues + Checking_Value;
            }
        }
        return approximateDoubleToInt(num);
    }
    
    public static int approximateDoubleToInt(double value) {
        // Use Math.round to approximate the double value
        return (int) Math.round(value);
    }
    
    /**
     * Multiplies the values of two specified columns in a TableView and calculates the sum of the results.
     * <p>
     * This method iterates through each row of a given TableView, retrieves the values from the specified
     * columns, converts them to double, and computes the product of these values. These products are then
     * summed up and returned as a final result.
     *
     * @param table  The TableView containing the data to process.
     * @param colNo1 The index of the first column to be multiplied.
     * @param ColNo2 The index of the second column to be multiplied.
     * @return The sum of the products of the values in the specified columns across all rows.
     */
    public static double multiplyTwoColumns(TableView<?> table, int colNo1, int ColNo2) {
        double SumOfMultRes = 0;
        int TableRowNumbers = table.getItems().size();
        for (int i = 0; i < TableRowNumbers; i++) {
            String[] data = getTableCellData(table, i);
            double Num1 = Double.parseDouble(data[colNo1]),
                    Num2 = Double.parseDouble(data[ColNo2]);
            SumOfMultRes = SumOfMultRes + (Num1 * Num2);
        }
        return SumOfMultRes;
    }
    
    /**
     * Formats a numeric value into a monetary representation without the currency symbol.
     * The method uses the default locale to create a currency formatter, strips the currency symbol,
     * and formats the number accordingly.
     *
     * @param n the numeric value to be formatted as a monetary value
     * @return a string representation of the formatted monetary value without the currency symbol
     */
    public static String MoneyFormatter(Double n) {
//        NumberFormat nf = NumberFormat.getCurrencyInstance();
//        return nf.format(n);
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        String pattern = ((DecimalFormat) nf).toPattern();
        String newPattern = pattern.replace("\u00A4", "").trim();
        NumberFormat newFormat = new DecimalFormat(newPattern);
        return newFormat.format(n);
    }
    
    /**
     * Sorts a specified column of a TableView either in ascending or descending order.
     * Adjusts the sorting order of the TableColumn in the TableView based on the provided type.
     *
     * @param table the TableView to which the column belongs
     * @param col   the TableColumn to be sorted
     * @param Type  specifies the sorting order ("Desc" for descending, otherwise ascending)
     *              <p>
     *              The method performs the following steps:
     *              1. Checks the provided Type parameter for the sorting order.
     *              2. If Type equals "Desc", sets the column's sort type to DESCENDING.
     *              3. Otherwise, sets the column's sort type to ASCENDING.
     *              4. Adds the column to the table's sort order to ensure it is considered in sorting.
     *              5. Finally, triggers the table's sorting mechanism to apply the desired sort order.
     */
    public static void sortTableCol(TableView<?> table, TableColumn col, String Type) {
        if (Type.equals("Desc")) {
            col.setSortType(TableColumn.SortType.DESCENDING);
            table.getSortOrder().add(col);
            table.sort();
        } else {
            col.setSortType(TableColumn.SortType.ASCENDING);
            table.getSortOrder().add(col);
            table.sort();
        }
        
    }
    
    /**
     * Filters rows in a given TableView by removing rows that do not match
     * a specific value in the specified column.
     * <p>
     * This method iterates through the rows of the provided TableView,
     * retrieves the data for the specified column using the helper method
     * `getTableCellData`, and compares it to the given value. Rows where
     * the data in the specified column do not match the provided value are removed.
     *
     * @param table the TableView object from which rows will be filtered
     * @param col   the index of the column to check for the specified value
     * @param value the value to match against the data in the specified column
     */
    public static void filterRowsAccordingToValue(TableView<?> table, int col, String value) {
        int TableRowNumbers = table.getItems().size();
        for (int i = TableRowNumbers - 1; i >= 0; i--) {
            String data = getTableCellData(table, i)[col];
            if (!data.equals(value)) {
                table.getItems().remove(i);
                TableRowNumbers = table.getItems().size();
            }
        }
    }
    
    /**
     * Prints a message to the standard output.
     * <p>
     * This method takes a string input and displays it on the console
     * using System.out.println().
     *
     * @param Msj the message to be printed to the standard output
     */
    public static void print(String Msj) {
        System.out.println(Msj);
    }
    
    /**
     * Updates the text of the provided array of TextField elements with corresponding data from the static class field `data`.
     * <p>
     * The method iterates through the array and assigns the text of each `TextField` using values from the `data` array.
     * If an exception occurs, it is caught but no specific handling is implemented.
     *
     * @param fields the array of TextField objects to be updated with corresponding data. The size of the array should match the length of the `data` array.
     */
    public static void setTextFieldData(TextField[] fields) {
        try {
            for (int i = 0; i < data.length; i++) {
                fields[i].setText(data[i]);
            }
        } catch (Exception e) {
        
        }
        
    }
    
    /**
     * Sets the text of a series of Label elements using data from the `data` array.
     * <p>
     * This method iterates over the `data` array sequentially, assigning each item
     * as the text for each provided Label. The iteration continues until all Labels
     * have been updated or the `data` array is exhausted.
     *
     * @param fields Varargs of Label objects whose text will be set using the values from the `data` array.
     *               Each Label's text is updated with consecutive elements from the `data` array.
     *               <p>
     *               Technical steps:
     *               1. Initialize a counter `i` to track the current index in the `data` array.
     *               2. Use a `while` loop that continues until all elements in the `data` array have been processed.
     *               3. Within the loop:
     *               - Iterate through each Label in the provided `fields`.
     *               - Set the text of the current Label to the value at index `i` in the `data` array.
     *               - Increment the counter after each assignment.
     *               4. The method stops when all elements in the `data` array have been used, regardless of the number of Labels provided.
     */
    public static void setLabelsData(Label... fields) {
        int i = 0;
        while (i < data.length) {
            for (Label lbl : fields) {
                lbl.setText(data[i]);
                i++;
            }
        }
    }
    
    /**
     * Calculates the sum of the numeric values of the text content of the provided Label objects.
     * <p>
     * This method iterates through the provided array of Label objects, extracts their text values,
     * converts them to integers, and returns the cumulative sum. It assumes that each Label's
     * text represents an integer.
     *
     * @param Lbl an array of Label objects whose text values are to be summed.
     * @return the sum of integer values parsed from the text of the provided Label objects.
     * @throws NumberFormatException if any Label's text cannot be converted to an integer.
     */
    public static int sumOfLbls(Label... Lbl) {
        int num = 0;
        for (Label l : Lbl) {
            num += Integer.valueOf(l.getText());
        }
        return num;
    }
    
    /**
     * Checks whether any of the provided string values are empty.
     * <p>
     * This method iterates through the variable-length string arguments (values)
     * and verifies if any of them is an empty string. Returns true if at least
     * one of the strings is empty; otherwise, returns false.
     *
     * @param Values The variable-length array of string values to check for emptiness.
     * @return true if at least one of the provided strings is empty, false otherwise.
     */
    public static boolean isNull(String... Values) {
        for (String v : Values) {
            if (v == null) return true;
            if (v.trim().equals("")) return true;
            try {
                if (v.isEmpty()) return true;
            } catch (NullPointerException ex) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if any of the provided string values are empty strings.
     * <p>
     * The method iterates over the given varargs array and verifies if any string
     * in the array is an empty string (""). If an empty string is found, the method
     * immediately returns true. If no empty strings are present, the method returns false.
     *
     * @param Values a varargs array of strings to be checked for empty strings
     * @return true if at least one string in the array is empty, otherwise false
     */
    public static boolean isVariableNull(String... Values) {
        for (String v : Values) {
            if (v == null) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Exports the database to a specified file path by executing a database dump command.
     * Utilizes the `mysqldump` utility to create a backup of the specified database.
     * <p>
     * Steps:
     * - Constructs the database dump command using the database credentials and target path.
     * - Executes the command via the `Runtime.exec` method.
     * - Waits for the process to complete and checks the result.
     * - Returns a success message if the process completes successfully, otherwise returns a failure message.
     * - Handles exceptions in case of errors during execution.
     *
     * @param path The relative path where the database backup will be stored, including the file name.
     * @return A String indicating the status of the operation: "Success" upon successful backup, "Failed" if the backup failed, or null if an exception is thrown.
     */
    public static String exportDB(String path) {
        
        Process process = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            process = runtime.exec("mysqldump.exe -u"
                    + MainConnection.dbUser + " -p" + MainConnection.dbPass
                    + " --add-drop-database" + " -B " + MainConnection.dbName
                    + " -r " + System.getProperty("user.dir") + path);
            int processDone = process.waitFor();
            if (processDone == 0) {
                process.destroy();
                print("Success");
                return "Success";
            } else {
                process.destroy();
                print("Backup Failed!");
                return "Failed";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        process.destroy();
        return null;
    }
    
    /**
     * Imports a database from a specified file path using the MySQL command-line tool.
     * <p>
     * This method constructs and executes a system-level command to restore a database
     * from a dump file (.sql) using the `mysql.exe` executable. The method monitors the
     * process to determine success or failure of the import operation.
     * <p>
     * Steps:
     * 1. Retrieve database user credentials from `MainConnection`.
     * 2. Construct the MySQL restore command using the provided file path and credentials.
     * 3. Execute the command using the `Runtime` API to start the external process.
     * 4. Wait for the process to complete and evaluate the return code:
     * - If the return code is `0`, the import is successful.
     * - For any other return code, the import is considered failed.
     * 5. Log a corresponding success or failure message.
     * 6. Handle any exceptions by printing the stack trace.
     *
     * @param path the file system path to the database dump file to be imported.
     */
    public static void importDB(String path) {
        String user = MainConnection.dbUser;
        String Password = MainConnection.dbPass;
        String[] restoreCMD = new String[]{"mysql.exe",
                "--user=" + user, "--password=" + Password, "-e", " SOURCE " + path};
        Process process;
        try {
            process = Runtime.getRuntime().exec(restoreCMD);
            int PoroCom = process.waitFor();
            if (PoroCom == 0) {
                print("Import Success");
            } else {
                print("Import Failed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Calculates the total sum of all numeric values in a specified column within a TableView.
     * <p>
     * This method iterates through all the rows of the provided TableView and sums up
     * the numeric values found in the specified column index. The column index is zero-based.
     *
     * @param table The TableView object containing the data to process.
     * @param col   The index of the column to calculate the total for, starting from 0.
     * @return The total sum of numeric values in the specified column.
     * Returns 0 if the column has no numeric values or the table is empty.
     */
    public static double calcColumnTotal(TableView<?> table, int col) {
        int TableRowNumbers = table.getItems().size();
        double total = 0;
        for (int i = 0; i < TableRowNumbers; i++) {
            total = total + Double.valueOf(getTableCellData(table, i)[col]);
        }
        return total;
    }
    
    /**
     * Calculates daily inventory statistics based on the data from a given TableView.
     * This method iterates through rows of a table and counts the occurrence
     * of specific types within a specified column, storing the results in a HashMap.
     *
     * @param table the TableView containing data to be processed. The data must be in such a format
     *              where the fourth column (index 3) contains categories of type String that need
     *              to be analyzed.
     * @return a HashMap where the keys are category names ("NewExamination", "consultation",
     * "newbook", "bookCancelled", "follow") and the values are their respective counts as strings.
     */
    public static HashMap<String, String> calcDailyInventory(TableView<?> table) {
        int NewExamination = 0;
        int consultation = 0;
        int newbook = 0;
        int bookCancelled = 0;
        int follow = 0;
        int TableRowNumbers = table.getItems().size();
        ObservableList<? extends TableColumn<?, ?>> col = table.getColumns();
        for (int i = 0; i < TableRowNumbers; i++) {
            String ConsultantType = (String) col.get(3).getCellData(i);
            switch (ConsultantType) {
                case "كشف جديد":
                    NewExamination++;
                    break;
                case "إستشارة":
                    consultation++;
                    break;
                case "تم حجز ميعاد جديد":
                    newbook++;
                    break;
                case "تم إلغاء الحجز":
                    bookCancelled++;
                    break;
                case "متابعة":
                    follow++;
                    break;
                default:
                    break;
            }
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("NewExamination", String.valueOf(NewExamination));
        map.put("consultation", String.valueOf(consultation));
        map.put("newbook", String.valueOf(newbook));
        map.put("bookCancelled", String.valueOf(bookCancelled));
        map.put("follow", String.valueOf(follow));
        return map;
    }
    
    /**
     * Calculates various counts and remaining counts of patient visits based on the data present in the provided table.
     * The function processes table data row by row, identifying different types of patient appointments,
     * and calculates the total visits, consultations, cancellations, and the remaining counts for each category.
     *
     * @param table A TableView containing patient appointment data, where specific columns represent
     *              various attributes such as appointment type and status.
     * @return A HashMap containing calculated counts, including:
     * "NewExamination" (total new examinations),
     * "Remain_Examination" (remaining new examinations),
     * "consultation" (total consultations),
     * "Remain_Consultant" (remaining consultations),
     * "follow" (total follow-ups),
     * "Remain_Follow" (remaining follow-ups),
     * "TotalRemain" (total remaining patients across all categories),
     * "Total" (total patients across all categories).
     */
    public static HashMap<String, String> calcWaitingPatients(TableView<?> table) {
        int NewExamination = 0, consultation = 0, newbook = 0, bookCancelled = 0, follow = 0, NewExRemain = 0,
                ConsultantRemain = 0, FollowRemain = 0, TotalRemain = 0, Total = 0;
        String State = "";
        int TableRowNumbers = table.getItems().size();
        ObservableList<? extends TableColumn<?, ?>> col = table.getColumns();
        for (int i = 0; i < TableRowNumbers; i++) {
            State = (String) col.get(5).getCellData(i);
            String ConsultantType = (String) col.get(3).getCellData(i);
            switch (ConsultantType) {
                case "كشف جديد":
                    NewExamination++;
                    if (State.equals("تم الكشف")) {
                        NewExRemain++;
                    }
                    break;
                case "إستشارة":
                    consultation++;
                    if (State.equals("تم الكشف")) {
                        ConsultantRemain++;
                    }
                    break;
                case "تم حجز ميعاد جديد":
                    newbook++;
                    break;
                case "تم إلغاء الحجز":
                    bookCancelled++;
                    break;
                case "متابعة":
                    follow++;
                    if (State.equals("تم الكشف")) {
                        FollowRemain++;
                    }
                    break;
                default:
                    break;
            }
        }
        int Remain_Examination = NewExamination - NewExRemain;
        int Remain_Consultant = consultation - ConsultantRemain;
        int Remain_Follow = follow - FollowRemain;
        TotalRemain = Remain_Examination + Remain_Consultant + Remain_Follow;
        Total = follow + consultation + NewExamination;
        HashMap<String, String> map = new HashMap<>();
        map.put("NewExamination", String.valueOf(NewExamination));
        map.put("Remain_Examination", String.valueOf(Remain_Examination));
        map.put("consultation", String.valueOf(consultation));
        map.put("Remain_Consultant", String.valueOf(Remain_Consultant));
        map.put("follow", String.valueOf(follow));
        map.put("Remain_Follow", String.valueOf(Remain_Follow));
        map.put("TotalRemain", String.valueOf(TotalRemain));
        map.put("Total", String.valueOf(Total));
        return map;
    }
    
    /**
     * Moves rows within a TableView based on specific column data values.
     * <p>
     * This method processes the rows of the provided TableView, identifies rows based on content
     * in a specific column (index 5), and reorders or removes rows accordingly. Specifically, rows
     * marked as "تم الكشف" are removed from the table, while others are retained. The method performs
     * the following steps:
     * <p>
     * 1. Counts the number of rows in the table.
     * 2. Initializes an array (`DoneRows`) to track the status of each row based on the value in column 5.
     * 3. Iterates through the rows to populate the `DoneRows` array:
     * - If the value in column 5 is "تم الكشف", the row's index is stored.
     * - Otherwise, the row is marked as "منتظر".
     * 4. Iterates through the `DoneRows` array:
     * - For each row marked "تم الكشف", retrieves its data using `getTableCellData()`, removes
     * the row from the TableView, and adjusts the iteration pointer accordingly.
     * - Skips rows marked "منتظر".
     *
     * @param table the TableView containing the rows to process. Each row's column 5 value determines
     *              whether the row is moved or retained.
     */
    public static void MoveRow(TableView<?> table) {
        int NoOfRows = table.getItems().size();
        String[] DoneRows = new String[NoOfRows];
        ObservableList<? extends TableColumn<?, ?>> col = table.getColumns();
        for (int z = 0; z < NoOfRows; z++) {
            String ValueOFstate = (String) col.get(5).getCellData(z);
            if (ValueOFstate.equals("تم الكشف")) {
                DoneRows[z] = String.valueOf(z);
            } else {
                DoneRows[z] = "منتظر";
            }
        }
        //-----------------------------------------
        String[] data = new String[getTableColumns(table)];
        
        int i = 0;
        int z = 0;
        while (z < NoOfRows) {
            if (DoneRows[z].equals("منتظر")) {
                i++;
            } else {
                data = getTableCellData(table, i);
                table.getItems().remove(i);
            }
            z++;
        }
    }
    
    /**
     * Sets the text of multiple TextField objects to null or empty.
     * <p>
     * This method iterates through the provided array of TextField objects and
     * clears their current text by setting each field's text to an empty string.
     * It is useful for resetting the state of input fields in a graphical user interface.
     *
     * @param fields A varargs parameter of TextField objects whose text will be cleared.
     */
    public static void setFieldsNull(TextField... fields) {
        for (TextField field : fields) {
            field.setText("");
        }
    }
    
    /**
     * Sets the text of the provided TextArea fields to null (empty string).
     * <p>
     * This method iterates over a variable number of TextArea objects passed as arguments
     * and clears their text by setting it to an empty string.
     *
     * @param fields One or more TextArea objects whose text will be cleared.
     */
    public static void setFieldsNull(TextArea... fields) {
        for (TextArea field : fields) {
            field.setText("");
        }
    }
    
    /**
     * Sets the visibility of the given FontIcon objects to true.
     * <p>
     * This method iterates through an array of FontIcon objects and
     * sets the visibility of each FontIcon to true. It ensures that
     * all provided FontIcon elements are made visible.
     *
     * @param fields an array of FontIcon objects whose visibility
     *               will be set to true.
     */
    public static void setVisibleTrue(FontIcon... fields) {
        for (FontIcon field : fields) {
            field.setVisible(true);
        }
    }
    
    /**
     * Sets the visibility of the provided Label elements to true.
     * <p>
     * This method iterates over an array of Label objects and sets their visible property to true.
     * It ensures all the passed Label objects become visible in the user interface.
     *
     * @param fields an array of Label objects whose visibility will be updated to true
     */
    public static void setVisibleTrue(Label... fields) {
        for (Label field : fields) {
            field.setVisible(true);
        }
    }
    
    /**
     * Sets the visibility of the given FontIcon objects to false.
     * <p>
     * This method takes a variable number of FontIcon arguments and iterates through
     * each of them, setting their visibility property to false.
     *
     * @param fields one or more FontIcon objects whose visibility will be set to false
     */
    public static void setVisibleFalse(FontIcon... fields) {
        for (FontIcon field : fields) {
            field.setVisible(false);
        }
    }
    
    /**
     * Sets the visibility of the provided Label objects to false.
     * <p>
     * This method iterates through an array of Label objects and ensures each
     * Label's visibility is set to "false," effectively hiding them from view.
     *
     * @param fields an array of Label objects whose visibility will be set to false
     */
    public static void setVisibleFalse(Label... fields) {
        for (Label field : fields) {
            field.setVisible(false);
        }
    }
    
    /**
     * Sets the text of each provided Label field to an empty string.
     * <p>
     * This method iterates over a variable number of Label objects, and for each Label,
     * it sets its text content to an empty string, effectively "nullifying" its display content.
     *
     * @param fields One or more Label objects whose text content needs to be cleared.
     */
    public static void setFieldsNull(Label... fields) {
        for (Label field : fields) {
            field.setText("");
        }
    }
    
    /**
     * Resets the text content of the provided PasswordField objects to an empty string.
     * <p>
     * This method takes a variable number of PasswordField objects and
     * iterates through each one, setting its text property to an empty
     * string (""). This effectively clears any value present in the
     * password fields, ensuring they appear blank.
     *
     * @param fields One or more PasswordField objects to be cleared.
     */
    public static void setPassFieldsNull(PasswordField... fields) {
        for (PasswordField field : fields) {
            field.setText("");
        }
    }
    
    /**
     * Sets the text of all provided Label fields to an empty string.
     * <p>
     * This method iterates through a variable number of Label objects
     * and assigns an empty string to each Label's text property.
     *
     * @param fields One or more Label objects whose text properties will be cleared.
     */
    public static void setLabelsNull(Label... fields) {
        for (Label field : fields) {
            field.setText("");
        }
    }
    
    /**
     * Initializes a spinner with a specified range of integer values.
     *
     * @param s     The Spinner instance to be initialized.
     * @param start The starting integer value of the spinner range.
     * @param end   The ending integer value of the spinner range.
     *              <p>
     *              This method configures a Spinner by setting its value factory to an
     *              IntegerSpinnerValueFactory. The value factory defines the valid range
     *              of integers from the specified start to end values. Once set, the Spinner
     *              can only accept and display values within this range.
     */
    public static void initSpinner(Spinner s, int start, int end) {
        s.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(start, end)
        );
    }
    
    /**
     * Translates the given English day of the week into its corresponding Arabic name.
     * If the input does not match any recognized day, it returns the input unchanged.
     * The comparison is case-insensitive.
     *
     * @param day The English name of the day of the week to be translated.
     * @return The Arabic name of the given day if it matches a valid day of the week,
     * or the original input if no match is found.
     */
    public static String translateDayOfWeek(String day) {
        if (day.equalsIgnoreCase("Saturday")) {
            return "السبت";
        } else if (day.equalsIgnoreCase("Sunday")) {
            return "الأحد";
        } else if (day.equalsIgnoreCase("Monday")) {
            return "الإثنين";
        } else if (day.equalsIgnoreCase("Tuesday")) {
            return "الثلاثاء";
        } else if (day.equalsIgnoreCase("Wednesday")) {
            return "الأربعاء";
        } else if (day.equalsIgnoreCase("Thursday")) {
            return "الخميس";
        } else {
            return day;
        }
    }
    
    /**
     * Converts a given date string in the format "yyyy-MM-dd" to a {@link LocalDate} object.
     * <p>
     * This method uses a predefined {@link DateTimeFormatter} with the pattern "yyyy-MM-dd"
     * to parse the provided string into a {@link LocalDate}. If parsing fails due to an invalid
     * input format or other errors, the method catches the exception and returns null.
     *
     * @param str the input string representing a date in the format "yyyy-MM-dd"
     * @return a {@link LocalDate} object representing the parsed date, or null if parsing fails
     */
    public static LocalDate strToLocalDate(String str) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(str, formatter);
        } catch (Exception ex) {
            return null;
        }
    }
    
    /**
     * Converts a string representation of time in the format "hh:mm AM/PM" into a LocalTime object.
     * <p>
     * This method extracts the hour, minute, and AM/PM designation from the input string. It then formats
     * these components into a compatible "hh:mm:ss a" format before parsing it into a `LocalTime` object.
     * If the input string is improperly formatted, an error message is displayed and the method returns null.
     * <p>
     * Technical steps:
     * 1. Extract the hour and minute portion from the string.
     * 2. Append ":00" to include seconds in the time representation.
     * 3. Extract the AM/PM part from the string.
     * 4. Concatenate the formatted time and AM/PM designation into a single string.
     * 5. Parse the resulting string into a `LocalTime` object using the DateTimeFormatter of pattern "hh:mm:ss a".
     * 6. Handle exceptions for invalid input strings and display an error message if formatting fails.
     *
     * @param str the string representation of the time in "hh:mm AM/PM" format.
     * @return the equivalent LocalTime object, or null if the input string is improperly formatted or an exception occurs.
     */
    public static LocalTime strToLocalTime(String str) {
        try {
            String from = str.substring(0, 5),// without PM  03:00
                    From = from + ":00",// 03:00:00
                    Type = str.substring(6, 8),// AM or PM
                    startTime = From + " " + Type; // 03:00:00 AM or PM
            return LocalTime.parse(startTime, DateTimeFormatter.ofPattern("hh:mm:ss a"));
        } catch (StringIndexOutOfBoundsException e) {
            showCustomMessage("تحذير", "ادخل الوقت بهذا الشكل \n"
                    + "( 03:00 PM )");
        }
        return null;
    }

//    public static void TransitionLR(FontIcon myIcon) {
//        TranslateTransition translate = new TranslateTransition();
//        translate.setNode(myIcon);
    
    /**
     * Formats the given number to a decimal string with up to two decimal places.
     * <p>
     * This method utilizes the DecimalFormat class to ensure consistent and localized
     * formatting of a double value. It formats the number to the pattern "#.##", truncating
     * or rounding the number to up to two decimal places, and converts it into a string output.
     *
     * @param num The double value to be formatted.
     * @return A string representation of the formatted number with up to two decimal places.
     */
//        translate.setByX(20);
//        translate.setCycleCount(TranslateTransition.INDEFINITE);
//        translate.setAutoReverse(true);
//        translate.play();
//    }
    public static String DecimalFormat(Double num) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(num);
    }
    
    /**
     * Computes and returns the average of a list of double values formatted as a string.
     *
     * @param nums the list of double values for which the average is to be calculated
     * @return the average of the given list as a formatted string
     * <p>
     * The method performs the following steps:
     * 1. Initializes a variable `sum` to accumulate the total of all numbers in the list.
     * 2. Iterates through the list and adds each number to the `sum`.
     * 3. Divides the total sum by the size of the list to compute the average.
     * 4. Formats the computed average using a `DecimalFormat` instance and returns it as a string.
     */
    public static String getAverage(List<Double> nums) {
        double sum = 0;
        //compute sum
        for (double num : nums) {
            sum += num;
        }
        //compute average
        return DecimalFormat((sum / nums.size()));
    }
    
    /**
     * Returns the current date in the format "YYYY-MM-dd".
     * <p>
     * This method uses the SimpleDateFormat class to format the current date
     * and time into a standardized string representation. It retrieves the
     * current system date using the Date class and converts it to the
     * specified format.
     *
     * @return A string representing the current date in the format "YYYY-MM-dd".
     */
    public static String getCurDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
        return formatter.format(new Date());
    }
    
    /**
     * Constructs and returns the canonical file path of a given JRXML file location, ensuring
     * it is formatted with forward slashes for standardization.
     *
     * @param Location The relative or additional directory path to be appended to the current directory's path.
     * @return A string representing the canonical file path with standardized slashes, or null if an IOException occurs.
     */
    public static String jrxmlLocation(String Location) {
        try {
            File currDir = new File(new File(".").getAbsolutePath());
            return currDir.getCanonicalPath().replace("\\", "/").concat(Location);
        } catch (IOException ex) {
        }
        return null;
    }
    
    /**
     * Constructs and returns the full directory path for a specified image file.
     * Combines the current working directory with a predefined subdirectory and the image name.
     *
     * @param ImageName The name of the image file to locate, including its extension.
     * @return A string representing the full path to the image file, combining the current
     * working directory, a predefined library directory, and the specified image name.
     */
    public static String jrxmlImageDir(String ImageName) {
        String currentDir = System.getProperty("user.dir"),// E:\CenterApp
                imageDir = "\\lib\\" + ImageName;
        return currentDir + imageDir;
    }
    
    /**
     * Retrieves the text of the currently selected RadioButton in a given ToggleGroup.
     * <p>
     * This method casts the selected Toggle in the provided ToggleGroup to a RadioButton
     * and retrieves its text property.
     *
     * @param toggleGroup the ToggleGroup containing the toggles, typically RadioButtons,
     *                    from which the selected toggle is retrieved.
     * @return the text property of the selected RadioButton in the ToggleGroup,
     * or null if no toggle is selected or the selected toggle is not a RadioButton.
     */
    public static String getToggleGroupSelected(ToggleGroup toggleGroup) {
        // Cast the selected toggle to a RadioButton
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        return selectedRadioButton.getText();
    }
    
    /**
     * Retrieves the list of selected items from a given ListView.
     * <p>
     * This method uses the selection model of the provided ListView
     * to obtain the currently selected items and converts them from
     * an ObservableList to a standard Java List for easier handling
     * and manipulation.
     *
     * @param listView the ListView from which the selected items are retrieved. It must be properly initialized and not null.
     * @return a List containing the currently selected items from the ListView. Returns an empty List if no items are selected.
     */
    public static <T> List<T> getListViewSelectedItems(ListView<T> listView) {
        // Get the selected items from the ListView
        ObservableList<T> selectedItems = listView.getSelectionModel().getSelectedItems();
        
        // Convert ObservableList to a regular List
        return new ArrayList<>(selectedItems);
    }
    
    /**
     * Updates the selection state of multiple RadioButton objects.
     * <p>
     * This method iterates over each RadioButton in the provided array and sets
     * its selected state based on the specified action parameter. It ensures that
     * all the passed RadioButton instances have a consistent selection state.
     *
     * @param action       a boolean value indicating the desired selection state. If true,
     *                     all RadioButton objects will be marked as selected; if false,
     *                     they will be unselected.
     * @param radioButtons a variable number of RadioButton instances to update.
     */
    public static void setRadioButtonsSelected(boolean action, RadioButton... radioButtons) {
        for (RadioButton radioButton : radioButtons) {
            radioButton.setSelected(action);
        }
    }
    
    /**
     * Converts a variable-length argument list of strings into a JSON array representation.
     *
     * @param values a variable-length argument list of string values to be included in the JSON array
     * @return a string representing the JSON array containing the provided values
     * <p>
     * The method creates a new instance of a JSONArray. It iterates over the given string values
     * and adds each value into the JSON array. Finally, it converts the JSON array to its string
     * representation and returns that string.
     */
    public static String toJsonArray(String... values) {
        JSONArray jsonArray = new JSONArray();
        for (String value : values) {
            jsonArray.put(value);
        }
        return jsonArray.toString();
    }
    
    /**
     * Selects specific items in a ListView based on a provided list of strings.
     * Ensures that the ListView supports multiple selection and clears any previous selections
     * before selecting the specified items.
     *
     * @param itemsToSelect A list of strings representing the items to be selected.
     * @param listView      The ListView in which the items will be selected.
     *                      <p>
     *                      The method performs the following steps:
     *                      1. Configures the ListView to allow multiple item selection.
     *                      2. Clears any existing selections in the ListView.
     *                      3. Retrieves the list of items currently present in the ListView.
     *                      4. Iterates through the ListView items and checks if each item exists in the provided list of strings to select.
     *                      5. Selects matching items in the ListView using the selection model.
     */
    // Method to select items in ListView based on the provided list of strings
    public static void selectItemsInListView(List<String> itemsToSelect, ListView<String> listView) {
        // Ensure the ListView allows multiple selection
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        // Clear previous selections
        listView.getSelectionModel().clearSelection();
        
        // Get the items from the ListView
        List<String> listViewItems = listView.getItems();
        
        // Iterate over the items in the ListView
        for (String item : listViewItems) {
            // Check if the current item should be selected
            if (itemsToSelect.contains(item)) {
                listView.getSelectionModel().select(item);
            }
        }
    }
    
    /**
     * Clears the selection of items in the provided ListView objects.
     * <p>
     * This method takes a variable number of ListView objects as input and ensures that
     * their current selections are cleared, if any. The method checks that each
     * ListView is non-null before attempting to clear its selection.
     *
     * @param listViews one or more ListView objects whose selections should be cleared.
     */
    public static void clearSelectedItems(ListView<?>... listViews) {
        for (ListView<?> listView : listViews) {
            if (listView != null) {
                listView.getSelectionModel().clearSelection();
            }
        }
    }
    
    /**
     * Applies a rounded border effect to one or more ImageView instances. This involves
     * setting clipping behavior and using listener bindings to ensure the shape adjusts
     * dynamically as the size of the ImageView changes.
     * <p>
     * The method works by setting up initial properties and dynamically adjusting a clipping
     * region for each ImageView provided. It ensures the images maintain aspect ratio and
     * smooth scaling for better display quality.
     * <p>
     * Technical Steps:
     * 1. Set the `preserveRatio` and `smooth` properties of each ImageView to ensure proper scaling.
     * 2. Add listeners to both `fitWidthProperty` and `fitHeightProperty` to track size changes.
     * 3. Use the `updateClip()` method to set an initial clip region and update it as needed
     * when size adjustments occur.
     *
     * @param imageViews One or more ImageView objects to which the rounded border effect
     *                   will be applied.
     */
    public static void applyRoundedBorder(ImageView... imageViews) {
        for (ImageView imageView : imageViews) {
            // Set properties for the ImageView to ensure it scales appropriately
            imageView.setPreserveRatio(true); // Preserve aspect ratio of the image
            imageView.setSmooth(true);        // Smooth scaling of the image
            
            // Add a listener to adjust the clip size based on the ImageView's size
            imageView.fitWidthProperty().addListener((obs, oldVal, newVal) -> {
                updateClip(imageView);
            });
            imageView.fitHeightProperty().addListener((obs, oldVal, newVal) -> {
                updateClip(imageView);
            });
            
            // Initial clip application
            updateClip(imageView);
        }
    }
    
    /**
     * Updates the given ImageView to apply rounded corners and optionally allows adding effects
     * like shadows while preserving transparency. This is achieved by creating a clip, taking
     * a snapshot of the clipped image, and reapplying it as a rounded image.
     *
     * @param imageView the ImageView to be updated with rounded corners and optional effects.
     *                  Must have a valid fitWidth and fitHeight set to apply the clip properly.
     */
    private static void updateClip(ImageView imageView) {
        // Get actual width and height of the ImageView
        double width = imageView.getFitWidth();
        double height = imageView.getFitHeight();
        
        if (width > 0 && height > 0) {
            // Create a Rectangle clip with rounded corners
            Rectangle clip = new Rectangle(width, height);
            clip.setArcWidth(30);  // Adjust this value to change roundness
            clip.setArcHeight(30);
            
            // Apply the clip to the ImageView
            imageView.setClip(clip);
            
            // Snapshot the image with the applied clip
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT); // Make the background transparent
            WritableImage snapshot = imageView.snapshot(parameters, null);
            
            // Remove the clip so that shadow or effects can be applied
            imageView.setClip(null);
            
            // Optional: Add a drop shadow effect
            // imageView.setEffect(new DropShadow(10, Color.BLACK));
            
            // Apply the snapshot (rounded) image back to the ImageView
            imageView.setImage(snapshot);
        }
    }
    
    /**
     * Applies a hover effect to the given ImageView by wrapping it in a StackPane
     * with animated visuals. This includes a moving "splash line" and an optional
     * reflection effect. The hover effect triggers visual animations when the
     * mouse enters or exits the image area.
     * <p>
     * The splash line is a white rectangle that moves across the image horizontally
     * with a fade in and out animation. The reflection adds a mirror-like effect
     * beneath the image if enabled.
     *
     * @param imageView the ImageView object to which the hover effect will be applied.
     *                  It is assumed that the ImageView has its dimensions (fitWidth and fitHeight)
     *                  set prior to calling this method.
     * @return a StackPane containing the given ImageView with the hover effect applied and
     * additional optional visual elements.
     */
    public static StackPane applyHoverEffect(ImageView imageView) {
        // Create a pane to hold the ImageView
        StackPane imagePane = new StackPane(imageView);
        imagePane.setStyle("-fx-background-color: black;"); // Set background if needed
        
        // Create a splash line (rectangle) that will move across the image
        Rectangle splashLine = new Rectangle(imageView.getFitWidth(), 10);
        splashLine.setFill(Color.WHITE);
        splashLine.setOpacity(0); // Initially hidden
        
        // Add the splash line to the pane (on top of the ImageView)
        imagePane.getChildren().add(splashLine);
        
        // Create a translate transition for the splash effect
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), splashLine);
        translateTransition.setFromX(-imageView.getFitWidth());
        translateTransition.setToX(imageView.getFitWidth());
        
        // Create a fade transition to make the splash visible and disappear
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), splashLine);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setAutoReverse(true);
        
        // Hover effect to start animation on hover
        imagePane.setOnMouseEntered(event -> {
            splashLine.setOpacity(1);
            fadeTransition.play();
            translateTransition.play();
        });
        
        // Remove the effect when the mouse exits
        imagePane.setOnMouseExited(event -> {
            splashLine.setOpacity(0); // Hide the splash after hover
            fadeTransition.stop();
            translateTransition.stop();
        });
        
        // Optional: Add reflection effect for a mirror-like effect
        Reflection reflection = new Reflection();
        reflection.setFraction(0.7);
        imageView.setEffect(reflection);
        
        return imagePane; // Return the StackPane containing the image and effects
    }
    
    /**
     * Calculates the total number of rows in a given ResultSet.
     * The method navigates to the last row of the ResultSet to fetch the row count.
     * It resets the ResultSet cursor to its initial position before returning.
     *
     * @param resultSet The ResultSet object from which the row count is to be determined.
     *                  Must support cursor movement operations.
     * @return An integer representing the total number of rows in the ResultSet.
     * Returns 0 if the ResultSet is null or an error occurs during processing.
     * @throws SQLException If an SQL exception occurs while accessing or manipulating the ResultSet.
     */
    public static int getResultSetRows(ResultSet resultSet) throws SQLException {
        Statement stmt = Con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        
        if (resultSet == null) {
            return 0;
        }
        
        try {
            resultSet.last();
            return resultSet.getRow();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            try {
                resultSet.beforeFirst();
            } catch (SQLException exp) {
                exp.printStackTrace();
            }
        }
        
        return 0;
    }
    
    /**
     * Animates an ImageView by applying a continuous scaling effect, alternating between reducing
     * and restoring its size. The scale effect uses random values for both scale percentage
     * and duration to produce a dynamic, non-repetitive animation.
     *
     * @param imageView The ImageView to be animated. The animation will modify its scale properties.
     *                  <p>
     *                  The method performs the following steps:
     *                  1. Generates a random scale percentage between 20% and 50%.
     *                  2. Retrieves the original width and height of the ImageView.
     *                  3. Calculates the new width and height using the random scale percentage.
     *                  4. Creates a `ScaleTransition` (scaleDown) to shrink the ImageView to the calculated dimensions.
     *                  5. Assigns a random duration (between 1 and 3 seconds) to the scaleDown transition.
     *                  6. Configures a second `ScaleTransition` (scaleUp) to restore the ImageView to its original size.
     *                  7. Uses the same duration for the scaleUp transition as used for scaleDown.
     *                  8. Sets up an event listener on the completion of scaleDown, triggering scaleUp animation.
     *                  9. Sets another listener on scaleUp to repeat the process by recursively calling `animateImageView`.
     *                  10. Starts the animation sequence by playing the scaleDown transition.
     */
    public static void animateImageView(ImageView imageView) {
        // Get a random scale percentage between 20% and 50%
        double scalePercentage = getRandom(20, 50);
        
        // Get original size
        double originalWidth = imageView.getFitWidth();
        double originalHeight = imageView.getFitHeight();
        
        // Calculate new size based on percentage
        double newWidth = originalWidth * (scalePercentage / 100);
        double newHeight = originalHeight * (scalePercentage / 100);
        
        // Create ScaleTransition for shrinking
        ScaleTransition scaleDown = new ScaleTransition();
        scaleDown.setNode(imageView);
        scaleDown.setFromX(1.0);
        scaleDown.setFromY(1.0);
        scaleDown.setToX(newWidth / originalWidth);
        scaleDown.setToY(newHeight / originalHeight);
        
        // Set random duration between 1 and 3 seconds
        double randomDuration = getRandom(2, 6); // Random between 1 and 3
        scaleDown.setDuration(Duration.seconds(randomDuration));
        
        // Create ScaleTransition for growing back to original size
        ScaleTransition scaleUp = new ScaleTransition();
        scaleUp.setNode(imageView);
        scaleUp.setFromX(newWidth / originalWidth);
        scaleUp.setFromY(newHeight / originalHeight);
        scaleUp.setToX(1.0);
        scaleUp.setToY(1.0);
        scaleUp.setDuration(Duration.seconds(randomDuration));
        
        // Play the animations in sequence and repeat
        scaleDown.setOnFinished(event -> {
            scaleUp.play();
            scaleUp.setOnFinished(event1 -> animateImageView(imageView)); // Restart the animation
        });
        
        scaleDown.play();
    }
    
    /**
     * Formats a given LocalTime object into a string representation with the pattern "hh:mm a".
     * <p>
     * The method uses a DateTimeFormatter with a 12-hour clock format and includes an AM/PM marker.
     * The formatted string does not include seconds or milliseconds.
     *
     * @param time the LocalTime object to be formatted
     * @return a string representation of the formatted time in the "hh:mm a" format
     */
    public static String formatTime(LocalTime time) {
        if (time == null) {
            throw new NullPointerException("The provided LocalTime object is null.");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return time.format(formatter);
    }
    
    /**
     * Converts a given 12-hour format time string (with AM/PM) to a 24-hour format {@link LocalTime} object.
     * <p>
     * The method uses a predefined input format to parse the provided time string.
     * If the input string does not match the expected pattern, the method handles the exception
     * and returns null to indicate invalid format.
     *
     * @param time12 the input time string in 12-hour format with AM/PM, e.g., "02:30 PM"
     * @return a {@link LocalTime} object representing the time in 24-hour format, or null if the input is invalid
     */
    public static LocalTime convertTo24HourLocalTime(String time12) {
        // Define the formatter for the input (12-hour format with AM/PM)
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        
        try {
            // Parse the input time
            return LocalTime.parse(time12, inputFormatter);
        } catch (DateTimeParseException e) {
            // Handle the case where the input format is incorrect
            System.out.println("Invalid time format: " + time12);
            return null; // Return null if the input is invalid
        }
    }
    
    public static boolean ifTableExistsInDB(Connection Con, String TableName) {
        try {
            String query = "SELECT 1 FROM " + TableName + " LIMIT 1";
            PreparedStatement stmt = Con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // If a row exists, return true
        } catch (SQLException ex) {
            if (ex.getMessage().toLowerCase().contains("doesn't exist") ||
                    ex.getMessage().toLowerCase().contains("unknown table") ||
                    ex.getSQLState().equals("42S02")) { // SQLState for MySQL table not found
                return false; // Table does not exist
            } else {
                ex.printStackTrace(); // Print stack trace for other exceptions
                return false;
            }
        }
    }
    
    // Function to set visibility of multiple components to false
    public static void hideComponents(Node... components) {
        for (Node component : components) {
            component.setVisible(false);
        }
    }
    
    // Function to set preferred height of multiple rows to zero
    public static void setPrefHeightZero(RowConstraints... rows) {
        for (RowConstraints row : rows) {
            row.setPrefHeight(0); // Set preferred height to zero
        }
    }
    
    // Function to concatenate Division, Subject Name, and Teacher Name
    public static String getSubjectName(String Division, String SubjectName, String TeacherName) {
        // Handle logic for abbreviating Division properly
        String formattedDivision = "";
        SubjectName = (SubjectName == null) ? "" : SubjectName;
        TeacherName = (TeacherName == null) ? "" : TeacherName;
        if (Division.endsWith("الابتدائي")) {
            formattedDivision = getDivisionNumber(Division) + "ب";
        } else if (Division.endsWith("الاعدادي")) {
            formattedDivision = getDivisionNumber(Division) + "ع";
        } else if (Division.endsWith("الثانوي")) {
            formattedDivision = getDivisionNumber(Division) + "ث";
        } else {
            // Default case, retain original Division
            formattedDivision = Division;
        }
        
        // Concatenate and return in the required format
        return "إختر الصف".equals(formattedDivision) ? "" : formattedDivision + " - " + SubjectName + " - " + TeacherName;
    }
    
    private static String getDivisionNumber(String Division) {
        if (Division.startsWith("الاول")) {
            return "1";
        } else if (Division.startsWith("الثاني")) {
            return "2";
        } else if (Division.startsWith("الثالث")) {
            return "3";
        } else if (Division.startsWith("الرابع")) {
            return "4";
        } else if (Division.startsWith("الخامس")) {
            return "5";
        } else if (Division.startsWith("السادس")) {
            return "6";
        } else {
            return ""; // If division does not match any pattern
        }
    }
    
    /**
     * Parses a formatted division string to extract the division, subject name, and teacher name.
     *
     * @param formattedDivision a formatted string representing a division, where the components
     *                          are separated by " - ". The format is expected to be:
     *                          "Division - Subject Name - Teacher Name".
     * @return an array of strings containing the extracted components:
     * [division, subject name, teacher name]. If the input format is invalid,
     * it returns an array of [null, null, null].
     */
    // Function to extract Division, Subject Name, and Teacher Name from formatted division
    public static String[] parseFormattedDivision(String formattedDivision) {
        try {
            formattedDivision = formattedDivision.trim(); // Remove extra spaces
            System.out.println("Parsing formattedDivision: " + formattedDivision);
            
            String[] parts = formattedDivision.split("\\s-\\s"); // Split with exact format
            if (parts.length != 3) {
                System.err.println("Invalid input format, expected 3 parts but got: " + parts.length);
                return new String[]{null, null, null};
            }
            
            String division = parseDivision(parts[0].trim());
            String subjectName = parts[1].trim();
            String teacherName = parts[2].trim();
            
            System.out.println("Parsed Successfully: Division = " + division + ", Subject = " + subjectName + ", Teacher = " + teacherName);
            return new String[]{division, subjectName, teacherName};
        } catch (Exception ex) {
            System.err.println("Error parsing formattedDivision: " + ex.getMessage());
            return new String[]{null, null, null}; // Fallback to null values
        }
    }
    
    // Helper function to parse Division back from abbreviation
    private static String parseDivision(String abbreviatedDivision) {
        if (abbreviatedDivision.endsWith("ب")) {
            return getFullDivision(abbreviatedDivision.substring(0, abbreviatedDivision.length() - 1), "الابتدائي");
        } else if (abbreviatedDivision.endsWith("ع")) {
            return getFullDivision(abbreviatedDivision.substring(0, abbreviatedDivision.length() - 1), "الاعدادي");
        } else if (abbreviatedDivision.endsWith("ث")) {
            return getFullDivision(abbreviatedDivision.substring(0, abbreviatedDivision.length() - 1), "الثانوي");
        } else {
            // Default case, just return the abbreviated division
            return abbreviatedDivision;
        }
    }
    
    // Helper to map the numeric part back to its Arabic name
    private static String getFullDivision(String number, String level) {
        switch (number) {
            case "1":
                return "الاول " + level;
            case "2":
                return "الثاني " + level;
            case "3":
                return "الثالث " + level;
            case "4":
                return "الرابع " + level;
            case "5":
                return "الخامس " + level;
            case "6":
                return "السادس " + level;
            default:
                return "إختر الصف"; // Handle unexpected cases
        }
    }
    
    
    // Helper function to copy a string to the clipboard
    private static void copyToClipboard(String text) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
        
        // Optionally, provide feedback to the user
        System.out.println("Copied to clipboard: " + text);
    }
//
//    public static void sendMessagesViaWhatsApp(String instance, String token, List<Student> studentList, String content) {
//        if (studentList == null || studentList.isEmpty()) {
//            System.err.println("Student list is empty or null. No messages to send.");
//            return;
//        }
//
//        ExecutorService executor = Executors.newFixedThreadPool(4); // Create a fixed thread pool with 4 threads
//        final int delayInMillis = 1500; // Delay between sending each message
//
//        System.out.println("Starting to send messages...");
//
//        try {
//            for (Student student : studentList) {
//
//                // Introduce explicit logging for the student being processed
//                System.out.println("Preparing message for student: " + student.getName());
//
//                // Delay between tasks
//                Thread.sleep(delayInMillis);
//
//                // Submit the task to the executor
//                executor.submit(() -> {
//                    System.out.println("Task submitted for student: " + student.getName());
//                    int retryCount = 10; // Allow up to 10 retries
//                    boolean success = false;
//
//                    while (retryCount > 0 && !success) {
//                        try {
//                            // Prepare message content
//                            String studentMessage = replacePlaceholders(student, content);
//                            System.out.println("Sending message for student: " + student.getName() + " - Message: " + studentMessage);
//
//                            // Mock API call to send the message
//                            Map<String, Object> response = WaPilotAPI.sendWaPilotMessage(
//                                    instance, token, student.getPhone(), studentMessage
//                            );
//
//                            // Check the response
//                            if (response == null) {
//                                System.err.println("Error: API response is null for student: " + student.getName() + ". Retrying...");
//                                retryCount--;
//                                continue;
//                            }
//
//                            success = "Message sent successfully.".equals(response.get("message"));
//                            if (!success) {
//                                System.err.println("Error: Failed to send message for student: " + student.getName() + ". Retrying...");
//                                retryCount--;
//                            } else {
//                                System.out.println("Message sent successfully for student: " + student.getName());
//                            }
//
//                        } catch (Exception e) {
//                            System.err.println("Error during message sending for student: " + student.getName() + ": " + e.getMessage());
//                            retryCount--;
//                        }
//                    }
//                });
//            }
//        } catch (Exception e) {
//            System.err.println("Unexpected error: " + e.getMessage());
//        } finally {
//            executor.shutdown(); // Initiate shutdown gracefully
//            try {
//                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) { // Wait for all tasks to finish
//                    executor.shutdownNow();
//                    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
//                        System.err.println("Executor did not terminate properly.");
//                    }
//                }
//            } catch (InterruptedException e) {
//                executor.shutdownNow();
//                Thread.currentThread().interrupt();
//            }
//        }
//
//        System.out.println("All tasks submitted. Waiting for completion...");
//
//    }
//
//    public static void sendMessageToSingleStudent(String instance, String token, Student student, String content) {
//        if (content == null || content.length() <= 5) {
//            showCustomMessage("تحذير", "يجب إضافة محتوي الرسالة اولا، ليتم إرسالها لأولياء الامور");
//            return;
//        }
//        if (student == null) {
//            System.err.println("Student data is null. Cannot send message.");
//            return;
//        }
//
//        int retryCount = 10; // Allow up to 10 retries
//        boolean success = false;
//
//        System.out.println("Preparing to send message to student: " + student.getName());
//
//        while (retryCount > 0 && !success) {
//            try {
//                // Prepare message content with placeholders replaced
//                String studentMessage = replacePlaceholders(student, content);
//                System.out.println("Sending message for student: " + student.getName() + " - Message: " + studentMessage);
//
//                // Mock API call to send the message
//                Map<String, Object> response = WaPilotAPI.sendWaPilotMessage(
//                        instance, token, student.getParentPhone1(), studentMessage
//                );
//
//                // Check the response
//                if (response == null) {
//                    System.err.println("Error: API response is null for student: " + student.getName() + ". Retrying...");
//                    retryCount--;
//                    continue;
//                }
//
//                success = "Message sent successfully.".equals(response.get("message"));
//                if (!success) {
//                    System.err.println("Error: Failed to send message for student: " + student.getName() + ". Retrying...");
//                    retryCount--;
//                } else {
//                    System.out.println("Message sent successfully for student: " + student.getName());
//                }
//
//            } catch (Exception e) {
//                System.err.println("Error during message sending for student: " + student.getName() + ". " + e.getMessage());
//                e.printStackTrace();
//                retryCount--;
//            }
//
//            // Delay after a retry
//            if (!success && retryCount > 0) {
//                try {
//                    Thread.sleep(1500); // Add delay between retries (1.5 seconds)
//                } catch (InterruptedException ex) {
//                    System.err.println("Error in delay between retries for student: " + student.getName());
//                    Thread.currentThread().interrupt();
//                }
//            }
//        }
//
//        if (!success) {
//            System.err.println("Failed to send message to student: " + student.getName() + " after 10 retries.");
//        }
//    }
//
//    private static String replacePlaceholders(Student sData, String content) {
//        StudentData Student = new StudentData();
//        if (sData.getID() != null) {
//            Student.setStudentID(Integer.parseInt(sData.getID()));
//        }
//        if (sData.getName() != null) {
//            Student.setStudentName(sData.getName());
//        }
//        if (sData.getParentPhone1() != null) {
//            Student.setParentPhone1(sData.getParentPhone1());
//        }
//        if (sData.getNumOfAttendance() != null) {
//            Student.setAttendanceSessionsNo(Integer.parseInt(sData.getNumOfAttendance()));
//        }
//        if (sData.getNumOfAttendance() != null) {
//            Student.setAbsenceSessionsNo(Integer.parseInt(sData.getNumOfAttendance()));
//        }
//        if (sData.getBalance() != null) {
//            Student.setWalletBalance(Integer.parseInt(sData.getBalance()));
//        }
//
//        PlaceholderProcessorManager processorManager = new PlaceholderProcessorManager();
//        processorManager.addProcessor(new StudentNameProcessor());
//        processorManager.addProcessor(new AttendanceSessionsProcessor());
//        processorManager.addProcessor(new AbsenceSessionsProcessor());
//        processorManager.addProcessor(new WalletBalanceProcessor());
//
//        return processorManager.processAll(content, Student);
//    }
    
    
}