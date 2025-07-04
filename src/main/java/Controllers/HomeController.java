package Controllers;

import Database.StationDB;
import Database.TransactionDB;
import Database.VehicleDB;
import Database.TransportLinesDB;
import Model.TransactionModel;
import Model.VehicleModel;
import Model.stationModel;
import Options.MainController;
import Options.MyOptions;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HomeController extends MainController implements Initializable {

    // مكونات الرأس
    @FXML private Label stationNameLabel;
    @FXML private Label dateTimeLabel;
    @FXML private Button settingsButton;

    // قسم قارئ الباركود
    @FXML private TextField barcodeTextField;
    @FXML private Button scanButton;
    @FXML private RadioButton enterRadioButton;
    @FXML private RadioButton exitRadioButton;
    @FXML private ToggleGroup transactionType;

    // قسم معلومات المركبة
    @FXML private Label vehicleIdLabel;
    @FXML private Label plateNumberLabel;
    @FXML private Label driverNameLabel;
    @FXML private Label driverPhoneLabel;
    @FXML private Label statusLabel;
    @FXML private Label homeStationLabel;
    @FXML private Label carLicenseLabel;

    // قسم تعيين الطابور
    @FXML private ComboBox<String> lineNumberComboBox;
    @FXML private Label queuePositionLabel;
    @FXML private Button confirmButton;
    @FXML private Button cancelButton;

    // قسم نتيجة العملية
    @FXML private Label timestampLabel;
    @FXML private Label transactionTypeLabel;
    @FXML private Label assignedQueueLabel;
    @FXML private Label resultMessageLabel;

    // قسم سعة المحطة
    @FXML private PieChart capacityChart;
    @FXML private Label totalCapacityLabel;
    @FXML private Label vehiclesInsideLabel;
    @FXML private Label availableSpaceLabel;

    // جدول الطابور الحالي
    @FXML private TableView<TransactionModel> queueTableView;
    @FXML private TableColumn<TransactionModel, String> queueLineColumn;
    @FXML private TableColumn<TransactionModel, String> queueVehicleIdColumn;
    @FXML private TableColumn<TransactionModel, String> queuePlateColumn;
    @FXML private TableColumn<TransactionModel, String> queueDriverColumn;
    @FXML private TableColumn<TransactionModel, String> queueEntryTimeColumn;
    @FXML private ComboBox<String> filterQueueComboBox;
    @FXML private Button refreshQueueButton;

    // جدول العمليات
    @FXML private TableView<TransactionModel> transactionsTableView;
    @FXML private TableColumn<TransactionModel, String> transIdColumn;
    @FXML private TableColumn<TransactionModel, String> transVehicleColumn;
    @FXML private TableColumn<TransactionModel, String> transTypeColumn;
    @FXML private TableColumn<TransactionModel, String> transLineColumn;
    @FXML private TableColumn<TransactionModel, String> transTimeColumn;
    @FXML private TableColumn<TransactionModel, String> transPlateColumn;
    @FXML private DatePicker transactionDatePicker;
    @FXML private Button exportTransactionsButton;
    @FXML private Button printTransactionsButton;

    // شريط الحالة
    @FXML private Label systemStatusLabel;
    @FXML private Label scannerStatusLabel;
    @FXML private Label databaseStatusLabel;
    @FXML private Label lastUpdateLabel;

    // متغيرات الفئة
    private VehicleModel currentVehicle;
    private stationModel currentStation;
    private int currentStationId = 4; // المحطة الافتراضية (التحرير) بناءً على البيانات المقدمة
    private Timeline clockTimeline;
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startClock();
        filterQueueComboBox.setValue("جميع الخطوط");
        // تهيئة معلومات المحطة
        loadStationInfo();

        // تشغيل الساعة
        startClock();

        // تهيئة مخطط السعة
        updateCapacityChart();

        // تحميل خطوط النقل لتعيين الطابور
        try {
            loadTransportLines();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // تهيئة الجداول
        initializeQueueTable();

        // إعداد معالجات الأحداث
        setupEventHandlers();

        // ضبط الحالة الأولية للواجهة
        resetVehicleInfo();
        updateStatusBar();

        // تحميل البيانات الأولية
        refreshQueueData();
    }

    private void loadStationInfo() {

        currentStation = StationDB.getStationById(currentStationId);

        if (currentStation != null) {
            // Set UI elements
            stationNameLabel.setText("المحطة: " + currentStation.getStationName());
            totalCapacityLabel.setText(String.valueOf(currentStation.getVehicleCapacity()));
            updateCapacityLabels();
        }
    }

    private void startClock() {
        clockTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime now = LocalDateTime.now();
            dateTimeLabel.setText(timeFormatter.format(now));
        }));
        clockTimeline.setCycleCount(Animation.INDEFINITE);
        clockTimeline.play();
    }

    private void updateCapacityChart() {
        try {
            // Add null check to prevent NPE
            if (currentStation == null) {
                System.err.println("Warning: currentStation is null, cannot update capacity chart");
                return; // Exit early
            }

            int vehiclesInside = VehicleDB.getVehiclesInStationCount(currentStationId);
            int totalCapacity = currentStation.getVehicleCapacity();
            int availableSpace = totalCapacity - vehiclesInside;

            // Update labels
            vehiclesInsideLabel.setText(String.valueOf(vehiclesInside));
            availableSpaceLabel.setText(String.valueOf(availableSpace));

            // Create pie chart data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("المركبات بالداخل", vehiclesInside),
                    new PieChart.Data("المساحة المتاحة", availableSpace)
            );

            capacityChart.setData(pieChartData);
            capacityChart.setTitle("سعة المحطة: " + totalCapacity);

        } catch (SQLException e) {
            handleError("خطأ في تحديث مخطط السعة", e);
        }
    }

    private void updateCapacityLabels() {
        try {
            int vehiclesInside = VehicleDB.getVehiclesInStationCount(currentStationId);
            int totalCapacity = currentStation.getVehicleCapacity();
            int availableSpace = totalCapacity - vehiclesInside;

            vehiclesInsideLabel.setText(String.valueOf(vehiclesInside));
            totalCapacityLabel.setText(String.valueOf(totalCapacity));
            availableSpaceLabel.setText(String.valueOf(availableSpace));
        } catch (SQLException e) {
            handleError("خطأ في تحديث بيانات السعة", e);
        }
    }


    @FXML
    void filterTableFromCB(ActionEvent event) {
        try {
            String selectedFilter = filterQueueComboBox.getValue();

            // Debug output
            System.out.println("Selected filter: " + selectedFilter);

            if (selectedFilter == null || selectedFilter.equals("جميع الخطوط")) {
                // Show all data
                queueTableView.setItems(TransactionDB.getActiveQueueTransactions(currentStationId));
                return;
            }

            // Extract just the line name part (e.g., "Sayda Aisha" from "13: Sayda Aisha")
            String destinationName;
            if (selectedFilter.contains(":")) {
                destinationName = selectedFilter.split(":")[1].trim();
            } else {
                destinationName = null;
            }

            // Get all queue transactions
            ObservableList<TransactionModel> allTransactions =
                    TransactionDB.getActiveQueueTransactions(currentStationId);

            // Create filtered list
            FilteredList<TransactionModel> filteredData = new FilteredList<>(allTransactions);

            // Apply filter
            filteredData.setPredicate(transaction -> {
                // Get the line column value for this row
                TableColumn<TransactionModel, String> col = queueLineColumn;
                String lineColumnValue = col.getCellData(transaction);

                // Debug output
                System.out.println("Comparing row value: [" + lineColumnValue +
                        "] with filter: [" + selectedFilter +
                        "], destination: [" + destinationName + "]");

                // Try different matching strategies
                if (lineColumnValue != null) {
                    // Strategy 1: Direct match
                    if (lineColumnValue.equals(selectedFilter)) {
                        return true;
                    }

                    // Strategy 2: Match by destination name
                    if (destinationName != null && lineColumnValue.contains(destinationName)) {
                        return true;
                    }

                    // Strategy 3: Extract line number and compare
                    if (selectedFilter.contains(":")) {
                        String lineNumber = selectedFilter.split(":")[0].trim();
                        if (lineColumnValue.startsWith(lineNumber + ":")) {
                            return true;
                        }
                    }
                }

                return false;
            });

            // Update table with filtered data
            SortedList<TransactionModel> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(queueTableView.comparatorProperty());
            queueTableView.setItems(sortedData);

        } catch (Exception e) {
            handleError("Error filtering table", e);
            try {
                // Fallback to showing all data
                queueTableView.setItems(TransactionDB.getActiveQueueTransactions(currentStationId));
            } catch (SQLException ex) {
                handleError("Error loading data", ex);
            }
        }
    }

    @FXML
    void filterTableClicked(MouseEvent event) {

    }

    private void loadTransportLines() throws SQLException {
        // Clear the combo box first to ensure we don't have stale data
        lineNumberComboBox.getItems().clear();

        ObservableList<String> lines = TransportLinesDB.getTransportLinesForStation(currentStationId);

        // Debug output
        System.out.println("Loaded " + lines.size() + " transport lines");

        lineNumberComboBox.setItems(lines);

        // Also populate the filter combo box
        filterQueueComboBox.getItems().clear();
        filterQueueComboBox.getItems().add("جميع الخطوط");
        filterQueueComboBox.getItems().addAll(lines);
        filterQueueComboBox.setValue("جميع الخطوط");
    }

//    private void loadTransportLines() {
//        try {
//            // Cannot resolve symbol 'TransportLinesDB'
//            ObservableList<String> lines = TransportLinesDB.getTransportLinesForStation(currentStationId);
//            lineNumberComboBox.setItems(lines);
//
//            // تعبئة مربع التصفية أيضًا
//            filterQueueComboBox.getItems().clear();
//            filterQueueComboBox.getItems().add("جميع الخطوط");
//            filterQueueComboBox.getItems().addAll(lines);
//            filterQueueComboBox.setValue("جميع الخطوط");
//        } catch (SQLException e) {
//            handleError("خطأ في تحميل خطوط النقل", e);
//        }
//    }

    private void initializeQueueTable() {
        queueLineColumn.setCellValueFactory(cellData -> {
            int lineNumber = cellData.getValue().getLineNumber();
            try {
                String destinationName = TransportLinesDB.getDestinationNameForLine(lineNumber);
                return new SimpleStringProperty(lineNumber + ": " + destinationName);
            } catch (SQLException e) {
                return new SimpleStringProperty(lineNumber + "");
            }
        });

        queueVehicleIdColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getVehicleId())));

        queuePlateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getVehiclePlate()));

        queueDriverColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDriverName()));

        queueEntryTimeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFormattedTimestamp()));
    }
//
//    private void initializeTransactionsTable() {
//        // إعداد أعمدة جدول العمليات
//        transIdColumn.setCellValueFactory(cellData ->
//                new SimpleStringProperty(String.valueOf(cellData.getValue().getTransactionId())));
//
//        transVehicleColumn.setCellValueFactory(cellData ->
//                new SimpleStringProperty(String.valueOf(cellData.getValue().getVehicleId())));
//
//        transPlateColumn.setCellValueFactory(cellData ->
//                new SimpleStringProperty(cellData.getValue().getVehiclePlate()));
//
//        transTypeColumn.setCellValueFactory(cellData -> {
//            String type = cellData.getValue().getType();
//            return new SimpleStringProperty(type.equals("enter") ? "دخول" : "خروج");
//        });
////        Required type:
////        String
////        Provided:
////        int
//        transLineColumn.setCellValueFactory(cellData ->
//                new SimpleStringProperty(cellData.getValue().getLineNumber()+""));
//
//        transTimeColumn.setCellValueFactory(cellData ->
//                new SimpleStringProperty(cellData.getValue().getFormattedTimestamp()));
//    }

    private void setupEventHandlers() {
        // حقل النص للباركود وزر المسح
        barcodeTextField.setOnAction(event -> searchVehicle());
        scanButton.setOnAction(event -> searchVehicle());

        // أزرار التأكيد والإلغاء
        confirmButton.setOnAction(event -> processTransaction());
        cancelButton.setOnAction(event -> resetAll());

        // مربع تصفية الطابور
        filterQueueComboBox.setOnAction(event -> refreshQueueData());

        // زر التحديث
        refreshQueueButton.setOnAction(event -> {
            refreshQueueData();
            updateCapacityChart();
        });

        filterQueueComboBox.setOnAction(event -> filterTableFromCB(event));

        // مجموعة التبديل لنوع العملية
        transactionType.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (currentVehicle != null) {
                try {
                    updateUIBasedOnTransactionType();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    private void

    searchVehicle() {
        String barcode = barcodeTextField.getText().trim();
        if (barcode.isEmpty()) {
            MyOptions.showCustomMessage("خطأ", "يرجى إدخال رقم المركبة أو مسح الباركود");
            return;
        }

        try {
            // محاولة تحليل الباركود كمعرف للمركبة
            int vehicleId;
            try {
                vehicleId = Integer.parseInt(barcode);
            } catch (NumberFormatException e) {
                // إذا لم يكن رقمًا، فقد يكون رقم لوحة أو شيئًا آخر
                vehicleId = VehicleDB.getVehicleIdByPlate(barcode);
                if (vehicleId == -1) {
                    MyOptions.showCustomMessage("خطأ", "لم يتم العثور على المركبة");
                    resetVehicleInfo();
                    return;
                }
            }

            // تحميل معلومات المركبة
            currentVehicle = VehicleDB.getVehicleById(vehicleId);
            if (currentVehicle == null) {
                MyOptions.showCustomMessage("خطأ", "لم يتم العثور على المركبة");
                resetVehicleInfo();
                return;
            }

            // عرض معلومات المركبة
            displayVehicleInfo(currentVehicle);

            // تحديث واجهة المستخدم بناءً على حالة المركبة ونوع العملية
            updateUIBasedOnTransactionType();

            // تمكين زر التأكيد
            confirmButton.setDisable(false);

        } catch (SQLException e) {
            handleError("خطأ في البحث عن المركبة", e);
            resetVehicleInfo();
        }
    }

    private void displayVehicleInfo(VehicleModel vehicle) {
        vehicleIdLabel.setText(String.valueOf(vehicle.getVehicleId()));
        plateNumberLabel.setText(vehicle.getPlateNumber());

        try {
            // الحصول على معلومات السائق
            ResultSet driverInfo = VehicleDB.getDriverInfoForVehicle(vehicle.getVehicleId());
            if (driverInfo.next()) {
                driverNameLabel.setText(driverInfo.getString("full_name"));
                driverPhoneLabel.setText(driverInfo.getString("phone_number"));
            }

            // الحصول على معلومات المحطة الأصلية
            ResultSet stationInfo = StationDB.getStationResultSetById(vehicle.getStationId());
            if (stationInfo.next()) {
                homeStationLabel.setText(stationInfo.getString("Staion_Name"));
            }

            // تعيين معلومات رخصة السيارة
            carLicenseLabel.setText(vehicle.getCarLicense());

            // تعيين الحالة بناءً على علامة inStation
            boolean inStation = vehicle.isInStation();
            statusLabel.setText(inStation ? "داخل المحطة" : "خارج المحطة");
            statusLabel.setTextFill(inStation ? Color.GREEN : Color.RED);

            // تعيين زر الراديو المناسب بناءً على حالة المركبة
            if (inStation) {
                exitRadioButton.setSelected(true);
            } else {
                enterRadioButton.setSelected(true);
            }

        } catch (SQLException e) {
            handleError("خطأ في عرض معلومات المركبة", e);
        }
    }


    private void updateUIBasedOnTransactionType() throws SQLException {
        boolean isEntering = enterRadioButton.isSelected();
        boolean isExiting = exitRadioButton.isSelected();
        boolean vehicleInStation = currentVehicle != null && currentVehicle.isInStation();

        // Check logic: Can't enter if already in station, can't exit if not in station
        if ((isEntering && vehicleInStation) || (isExiting && !vehicleInStation)) {
            String errorMsg = isEntering ?
                    "المركبة بالفعل داخل المحطة، لا يمكن تسجيل دخول مرة أخرى" :
                    "المركبة ليست داخل المحطة، لا يمكن تسجيل خروج";

            MyOptions.showCustomMessage("تنبيه", errorMsg);

            // Reset to correct state
            if (vehicleInStation) {
                exitRadioButton.setSelected(true);
            } else {
                enterRadioButton.setSelected(true);
            }
            return;
        }

        // Update UI elements based on transaction type
        if (isEntering) {
            // IMPORTANT: Make sure to ENABLE the combo box for entering vehicles
            lineNumberComboBox.setDisable(false);

            // Ensure the combo box has items - check if it's empty and reload if needed
            if (lineNumberComboBox.getItems().isEmpty()) {
                loadTransportLines(); // Reload the transport lines
            }

            // Select the first item if nothing is selected
            if (lineNumberComboBox.getValue() == null && !lineNumberComboBox.getItems().isEmpty()) {
                lineNumberComboBox.setValue(lineNumberComboBox.getItems().get(0));
            }

            // Update queue position
            try {
                int lineNumber = TransportLinesDB.extractVehicleIdFromLineString(lineNumberComboBox.getValue());
                if (lineNumber != -1) {
                    int queuePosition = TransactionDB.getNextQueuePosition(currentStationId, lineNumber);
                    queuePositionLabel.setText(String.valueOf(queuePosition));
                } else {
                    queuePositionLabel.setText("--");
                }
            } catch (Exception e) {
                queuePositionLabel.setText("--");
            }
        } else {
            // For exiting vehicles, use current line number
            try {
                int lineNumber = TransactionDB.getVehicleLineNumber(currentVehicle.getVehicleId(), currentStationId);
                String destinationName = TransportLinesDB.getDestinationNameForLine(lineNumber);
                lineNumberComboBox.setValue(lineNumber + ": " + destinationName);
                lineNumberComboBox.setDisable(true);
            } catch (SQLException e) {
                lineNumberComboBox.setValue("--");
            }
            queuePositionLabel.setText("--");
        }
    }


    @FXML
    private void processTransaction() {
        if (currentVehicle == null) {
            MyOptions.showCustomMessage("خطأ", "لم يتم تحديد مركبة");
            return;
        }

        boolean isEntering = enterRadioButton.isSelected();
        String transactionType = isEntering ? "enter" : "exit";
        int vehicleId = currentVehicle.getVehicleId();

        try {
            // الحصول على رقم الخط
            int lineNumber = -1;
            if (lineNumberComboBox.getValue() != null && !lineNumberComboBox.getValue().equals("--")) {
                try {
                    lineNumber = TransportLinesDB.extractVehicleIdFromLineString(lineNumberComboBox.getValue());
                    if (lineNumber == -1) {
                        MyOptions.showCustomMessage("خطأ", "رقم الخط غير صالح");
                        return;
                    }
                } catch (Exception e) {
                    MyOptions.showCustomMessage("خطأ", "رقم الخط غير صالح");
                    return;
                }
            } else if (isEntering) {
                MyOptions.showCustomMessage("خطأ", "يرجى تحديد رقم الخط");
                return;
            }

            // للمركبات الداخلة، تحقق مما إذا كانت المحطة لديها سعة
            if (isEntering) {
                int vehiclesInside = Integer.parseInt(vehiclesInsideLabel.getText());
                int totalCapacity = Integer.parseInt(totalCapacityLabel.getText());

                if (vehiclesInside >= totalCapacity) {
                    MyOptions.showCustomMessage("تنبيه", "المحطة ممتلئة، لا يمكن إضافة مركبات أخرى");
                    return;
                }
            }

            // تسجيل العملية
            LocalDateTime now = LocalDateTime.now();
            long transactionId = TransactionDB.recordTransaction(
                    vehicleId,
                    currentStationId,
                    lineNumber,
                    transactionType,
                    now
            );

            if (transactionId > 0) {
                // تحديث حالة المركبة في قاعدة البيانات
                VehicleDB.updateVehicleStatus(vehicleId, isEntering);

                // تحديث كائن المركبة الحالي
                currentVehicle.setInStation(isEntering);

                // تحديث واجهة المستخدم لإظهار نتيجة العملية
                displayTransactionResult(transactionId, transactionType, lineNumber, now);

                // تحديث عروض البيانات
                refreshQueueData();
                updateCapacityChart();

                // تحديث وقت آخر تحديث
                lastUpdateLabel.setText("آخر تحديث: " + timeFormatter.format(now));

            } else {
                MyOptions.showCustomMessage("خطأ", "فشلت عملية تسجيل الحركة");
            }

        } catch (SQLException e) {
            handleError("خطأ في معالجة العملية", e);
        }
    }

    private void displayTransactionResult(long transactionId, String type, int lineNumber, LocalDateTime timestamp) {
        // Update transaction result panel
        transactionTypeLabel.setText(type.equals("enter") ? "دخول" : "خروج");

        // Enhanced display for assigned queue label
        if (type.equals("enter")) {
            try {
                // Get destination name for the line number
                String destinationName = TransportLinesDB.getDestinationNameForLine(lineNumber);
                // Format: "13: Abbasiya"
                assignedQueueLabel.setText(lineNumber + ": " + destinationName);
            } catch (SQLException e) {
                assignedQueueLabel.setText(String.valueOf(lineNumber));
            }
        } else {
            assignedQueueLabel.setText("--");
        }

        timestampLabel.setText(timeFormatter.format(timestamp));

        String message = type.equals("enter")
                ? "تم تسجيل دخول المركبة بنجاح"
                : "تم تسجيل خروج المركبة بنجاح";
        resultMessageLabel.setText(message);

        // Update vehicle info display to reflect new status
        statusLabel.setText(type.equals("enter") ? "داخل المحطة" : "خارج المحطة");
        statusLabel.setTextFill(type.equals("enter") ? Color.GREEN : Color.RED);
    }

    private void refreshQueueData() {
        try {
            String filterLine = filterQueueComboBox.getValue();
            ObservableList<TransactionModel> queueData;

            if (filterLine.equals("جميع الخطوط")) {
                queueData = TransactionDB.getActiveQueueTransactions(currentStationId);
            } else {
                int lineNumber = TransportLinesDB.extractVehicleIdFromLineString(filterLine);
                if (lineNumber != -1) {
                    queueData = TransactionDB.getActiveQueueTransactionsByLine(currentStationId, lineNumber);
                } else {
                    queueData = FXCollections.observableArrayList(); // Empty list
                }
            }

            queueTableView.setItems(queueData);
        } catch (SQLException e) {
            handleError("خطأ في تحديث بيانات الطابور", e);
        }
    }




    private void exportTransactions() {
        // تنفيذ تصدير العمليات إلى ملف
        MyOptions.showCustomMessage("تنبيه", "سيتم تنفيذ تصدير البيانات في الإصدار القادم");
    }

    private void printTransactions() {
        // تنفيذ طباعة العمليات
        MyOptions.showCustomMessage("تنبيه", "سيتم تنفيذ طباعة البيانات في الإصدار القادم");
    }

    private void resetVehicleInfo() {
        // مسح معلومات المركبة
        vehicleIdLabel.setText("--");
        plateNumberLabel.setText("--");
        driverNameLabel.setText("--");
        driverPhoneLabel.setText("--");
        statusLabel.setText("--");
        homeStationLabel.setText("--");
        carLicenseLabel.setText("--");

        // إعادة تعيين نموذج العملية
        lineNumberComboBox.setValue(null);
        queuePositionLabel.setText("--");

        // مسح نتيجة العملية
        transactionTypeLabel.setText("--");
        assignedQueueLabel.setText("--");
        timestampLabel.setText("--");
        resultMessageLabel.setText("جاهز لمعالجة المركبة");

        // تعطيل زر التأكيد
        confirmButton.setDisable(true);

        // مسح المركبة الحالية
        currentVehicle = null;

        // مسح حقل الباركود
        barcodeTextField.clear();
    }

    private void resetAll() {
        resetVehicleInfo();

        // إعادة تعيين أزرار الراديو إلى الافتراضي
        enterRadioButton.setSelected(true);

        // تمكين مربع القائمة المنسدلة لرقم الخط
        lineNumberComboBox.setDisable(false);
    }

    private void updateStatusBar() {
        // تعيين حالة النظام
        systemStatusLabel.setText("متصل");
        systemStatusLabel.setTextFill(Color.GREEN);

        // تعيين حالة الماسح الضوئي (محاكاة)
        scannerStatusLabel.setText("متصل");
        scannerStatusLabel.setTextFill(Color.GREEN);

        // تعيين حالة قاعدة البيانات
        // محاولة استعلام بسيط لقاعدة البيانات للتحقق من الاتصال
        StationDB.checkConnection();
        databaseStatusLabel.setText("متصل");
        databaseStatusLabel.setTextFill(Color.GREEN);

        // تعيين وقت آخر تحديث
        lastUpdateLabel.setText("آخر تحديث: " + timeFormatter.format(LocalDateTime.now()));
    }

    private void handleError(String message, Exception e) {
        System.err.println(message + ": " + e.getMessage());
        e.printStackTrace();

        // عرض مربع حوار الخطأ للمستخدم
        Platform.runLater(() -> {
            MyOptions.showCustomMessage("خطأ", message);
        });
    }

    // هذه الطريقة سيتم استدعاؤها عند إغلاق التطبيق
    public void shutdown() {
        if (clockTimeline != null) {
            clockTimeline.stop();
        }
    }
}