//package Options;
//
//import TablesClasses.Student;
//import javafx.animation.*;
//import javafx.scene.control.TableRow;
//import javafx.scene.control.TableView;
//import javafx.scene.paint.Color;
//import javafx.util.Duration;
//
//
//public class TableViewEffect {
//
//    // Make RowState a public static nested enum
//    public static enum RowState {
//        NONE, WARNING, ERROR
//    }
//
//    /**
//     * Applies random effects to the rows of the specified table based on certain conditions.
//     * Specifically, it checks the balance of each {@code Student} in the table and sets the row's state
//     * to {@code WARNING} if the balance is negative. Finally, the table is refreshed to reflect these changes.
//     *
//     * @param tableView the TableView containing {@code Student} objects whose rows are subject to styling changes
//     */
//    // Function to randomly apply styles to rows
//    public static void applyRandomEffects(TableView<Student> tableView) {
//        // Total number of rows
//        int rowCount = tableView.getItems().size();
//
//        // Apply warning effect to random rows
//        for (int i = 0; i < rowCount; i++) {
//            Student item = tableView.getItems().get(i);
//            int balance = Integer.parseInt(item.getBalance());
//            if (balance < 0) {
//                item.setRowState(RowState.WARNING);
//            }
//        }
//        // Refresh table to trigger row rendering
//        tableView.refresh();
//    }
//
//    /**
//     * Sets a custom row factory for the provided TableView to dynamically update row styles
//     * based on the state of the data item in each row. Different styles and effects are
//     * applied for specific row states like WARNING or ERROR.
//     *
//     * @param tableView the TableView to which the custom row factory is being applied
//     */
//    // Set row factory to apply styles based on item state
//    public static void setRowFactory(TableView<Student> tableView) {
//        tableView.setRowFactory(tv -> new TableRow<Student>() {
//            @Override
//            protected void updateItem(Student item, boolean empty) {
//                super.updateItem(item, empty);
//                if (item == null || empty) {
//                    setStyle("");
//                } else {
//                    // Apply styles based on row state
//                    switch (item.getRowState()) {
//                        case WARNING:
//                            applyWarningStyle(this);
//                            applyEffects(this);
//                            break;
//                        case ERROR:
//                            applyErrorStyle(this);
//                            applyBlinkingEffect(this);
//                            break;
//                        default:
//                            setStyle("");
//                    }
//                }
//            }
//        });
//    }
//
//    /**
//     * Applies a warning style to the specified table row by modifying its style classes.
//     * Removes the "error-row" style class, if present, and adds the "warning-row" style class.
//     *
//     * @param row the TableRow to which the warning style will be applied; represents a single row in a TableView
//     */
//    // Apply warning style to a row
//    public static <T> void applyWarningStyle(TableRow<T> row) {
//        row.getStyleClass().removeAll("error-row");
//        row.getStyleClass().add("warning-row");
//    }
//
//    /**
//     * Applies error styling to the specified table row.
//     * This method removes any "warning-row" styles from the row and applies the "error-row" style.
//     *
//     * @param row the {@code TableRow} to which the error style should be applied
//     * @param <T> the type parameter for the items contained in the {@code TableRow}
//     */
//    // Apply error style to a row
//    public static <T> void applyErrorStyle(TableRow<T> row) {
//        row.getStyleClass().removeAll("warning-row");
//        row.getStyleClass().add("error-row");
//    }
//
//    /**
//     * Applies specific visual effects to the provided table row, enhancing its appearance.
//     * This method utilizes color pulse and opacity wave effects for modern UI feedback.
//     *
//     * @param row the table row to which the visual effects are applied
//     */
//    // Apply various effects for modern visual feedback
//    public static void applyEffects(TableRow<?> row) {
////        applyBlinkingEffect(row);
////        applyScalingEffect(row);
//        applyColorPulseEffect(row);
////        applyRotationEffect(row);
//        applyOpacityWaveEffect(row);
////        applySlideEffect(row);
//    }
//
//    /**
//     * Applies a blinking effect to the specified table row, typically to indicate an error state.
//     * The effect alternates the row's opacity between two values in a continuous loop.
//     *
//     * @param row the TableRow to which the blinking effect is applied
//     */
//    // Apply blinking effect for error rows
//    public static void applyBlinkingEffect(TableRow<?> row) {
//        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), row);
//        fadeTransition.setFromValue(1.0);
//        fadeTransition.setToValue(0.5);
//        fadeTransition.setCycleCount(FadeTransition.INDEFINITE);
//        fadeTransition.setAutoReverse(true);
//        fadeTransition.play();
//    }
//
//    /**
//     * Applies a scaling effect to the specified table row. The effect dynamically
//     * increases and decreases the size of the row in a loop to create a pulsating
//     * animation.
//     *
//     * @param row the TableRow to which the scaling effect is applied
//     */
//    // Apply scaling effect
//    public static void applyScalingEffect(TableRow<?> row) {
//        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(1000), row);
//        scaleTransition.setFromX(1.0);
//        scaleTransition.setFromY(1.0);
//        scaleTransition.setToX(1.05);
//        scaleTransition.setToY(1.05);
//        scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);
//        scaleTransition.setAutoReverse(true);
//        scaleTransition.play();
//    }
//
//    /**
//     * Applies a color pulse effect to the specified TableRow. The effect smoothly transitions
//     * the background color of the row between two colors (yellow and orange) in a looping manner.
//     * This is intended to visually highlight the row for emphasis or drawing attention.
//     *
//     * @param row the TableRow to which the color pulse effect will be applied
//     */
//    // Apply color pulse effect
//    public static void applyColorPulseEffect(TableRow<?> row) {
//        FillTransition fillTransition = new FillTransition(Duration.millis(1200));
//        fillTransition.setCycleCount(FillTransition.INDEFINITE);
//        fillTransition.setAutoReverse(true);
//        fillTransition.setFromValue(Color.YELLOW);
//        fillTransition.setToValue(Color.ORANGE);
//        fillTransition.play();
//    }
//
//    /**
//     * Applies a continuous rotation effect to the specified TableRow. The row will rotate
//     * back and forth between 0 and 3 degrees in an indefinite loop for visual feedback.
//     *
//     * @param row the TableRow to which the rotation effect is applied
//     */
//    // Apply rotation effect
//    public static void applyRotationEffect(TableRow<?> row) {
//        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1500), row);
//        rotateTransition.setFromAngle(0);
//        rotateTransition.setToAngle(3);
//        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
//        rotateTransition.setAutoReverse(true);
//        rotateTransition.play();
//    }
//
//    /**
//     * Applies an opacity wave effect to the specified table row. This effect creates
//     * a smooth fade transition where the row's opacity alternates between fully visible
//     * and partially transparent. The transition is continuous and set to reverse automatically.
//     *
//     * @param row the TableRow to which the opacity wave effect will be applied
//     */
//    // Apply opacity wave effect
//    public static void applyOpacityWaveEffect(TableRow<?> row) {
//        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), row);
//        fadeTransition.setFromValue(1.0);
//        fadeTransition.setToValue(0.8);
//        fadeTransition.setCycleCount(FadeTransition.INDEFINITE);
//        fadeTransition.setAutoReverse(true);
//        fadeTransition.play();
//    }
//
//    /**
//     * Applies a sliding effect to the specified TableRow. The row is moved
//     * back and forth horizontally to create a visual sliding animation.
//     * The effect is continuous and alternates its direction after each cycle.
//     *
//     * @param row the TableRow to which the sliding effect will be applied
//     */
//    // Apply slide effect
//    public static void applySlideEffect(TableRow<?> row) {
//        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1500), row);
//        translateTransition.setFromX(0);
//        translateTransition.setToX(10);
//        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
//        translateTransition.setAutoReverse(true);
//        translateTransition.play();
//    }
//
//    public class TableItem {
//        private String name;
//        private RowState rowState = RowState.NONE;
//
//        // Constructor, getters, and setters
//
//        public TableItem(String name) {
//            this.name = name;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public RowState getRowState() {
//            return rowState;
//        }
//
//        public void setRowState(RowState rowState) {
//            this.rowState = rowState;
//        }
//    }
//
//
//}