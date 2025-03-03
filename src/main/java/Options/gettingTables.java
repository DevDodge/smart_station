///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Options;
//
//import TablesClasses.*;
//import javafx.collections.ObservableList;
//import javafx.scene.control.CheckBox;
//import javafx.scene.control.Label;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//
//import static Database.GrpHasStd.getStdTable;
//import static Database.MoneyCalculations.*;
//import static Database.Student.searchStudentTable;
//import static Database.Student_Works.*;
//import static Options.MyOptions.sortTableCol;
//
///**
// *
// * @author Mohamed
// */
//public class gettingTables {
//
//    /**
//     * Configures and populates the given TableView with user data.
//     *
//     * @param IDCol the table column for user IDs
//     * @param userNameCol the table column for usernames
//     * @param PassCol the table column for passwords
//     * @param DepCol the table column for departments
//     * @param UsersTable the table view to be populated with user data
//     */
//    public static void getUsersTable(
//            TableColumn<Users, String> IDCol,
//            TableColumn<Users, String> userNameCol,
//            TableColumn<Users, String> PassCol,
//            TableColumn<Users, String> DepCol,
//            TableView<Users> UsersTable
//    ) {
//        IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
//        userNameCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
//        PassCol.setCellValueFactory(new PropertyValueFactory<>("Password"));
//        DepCol.setCellValueFactory(new PropertyValueFactory<>("Departement"));
//        ObservableList<Users> UsersList = Database.TableQuery.getUsers();
//        UsersTable.getItems().clear();
//        UsersTable.setItems(UsersList);
//    }
//
//    /**
//     * Configures the columns and populates the subject table with data from the database.
//     *
//     * @param IdCol the table column representing the ID of the subject
//     * @param NameCol the table column representing the name of the subject
//     * @param EduLevelCol the table column representing the educational level of the subject
//     * @param SessionPriceCol the table column representing the session price of the subject
//     * @param TermPriceCol the table column representing the term price of the subject
//     * @param SubjectTable the table view where the subject data is displayed
//     */
//    public static void getSubjectTable(
//            TableColumn<Subject, String> IdCol,
//            TableColumn<Subject, String> NameCol,
//            TableColumn<Subject, String> EduLevelCol,
//            TableColumn<Subject, String> SessionPriceCol,
//            TableColumn<Subject, String> TermPriceCol,
//            TableView<Subject> SubjectTable
//    ) {
//        IdCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
//        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        EduLevelCol.setCellValueFactory(new PropertyValueFactory<>("EduLeve"));
//        SessionPriceCol.setCellValueFactory(new PropertyValueFactory<>("SessionPrice"));
//        TermPriceCol.setCellValueFactory(new PropertyValueFactory<>("TermPrice"));
//        ObservableList<Subject> SubjectList = Database.Subject.getSubjectTable();
//        SubjectTable.getItems().clear();
//        SubjectTable.setItems(SubjectList);
//    }
//
//    /**
//     * Configures and populates a table for displaying group information.
//     *
//     * @param groupNameCol  The table column for group names.
//     * @param SubjectNameCol  The table column for subject names.
//     * @param MaxNumCol  The table column for maximum number of members.
//     * @param GroupsTable  The TableView object that will display the group data.
//     * @param EduLevel  The education level used to retrieve group data.
//     */
//    public static void getGroupsTable(
//            TableColumn<Groups, String> groupNameCol,
//            TableColumn<Groups, String> SubjectNameCol,
//            TableColumn<Groups, String> MaxNumCol,
//            TableView<Groups> GroupsTable, String EduLevel
//    ) {
//        groupNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
//        SubjectNameCol.setCellValueFactory(new PropertyValueFactory<>("Subject"));
//        MaxNumCol.setCellValueFactory(new PropertyValueFactory<>("MaxNum"));
//        ObservableList<Groups> List = Database.Groups.getGroupsTable(EduLevel);
//        GroupsTable.getItems().clear();
//        GroupsTable.setItems(List);
//    }
//
//    /**
//     * Configures and populates the groups table with data based on the specified educational level
//     * and subject name.
//     *
//     * @param groupNameCol the table column for displaying group names
//     * @param SubjectNameCol the table column for displaying subject names
//     * @param MaxNumCol the table column for displaying the maximum number of students in a group
//     * @param GroupsTable the table view to be populated with group data
//     * @param EduLevel the educational level used to filter the groups
//     * @param SubjectName the subject name used to filter the groups
//     */
//    public static void getGroupsTable(
//            TableColumn<Groups, String> groupNameCol,
//            TableColumn<Groups, String> SubjectNameCol,
//            TableColumn<Groups, String> MaxNumCol,
//            TableView<Groups> GroupsTable, String EduLevel, String SubjectName
//    ) {
//        groupNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
//        SubjectNameCol.setCellValueFactory(new PropertyValueFactory<>("Subject"));
//        MaxNumCol.setCellValueFactory(new PropertyValueFactory<>("MaxNum"));
//        ObservableList<Groups> List = Database.Groups.getGroupsTable(EduLevel,
//                SubjectName);
//        GroupsTable.getItems().clear();
//        GroupsTable.setItems(List);
//    }
//
//    /**
//     * Configures and populates an appointment table with data based on the provided education level.
//     * The method sets up the cell value factories for the table columns and retrieves the appointment data
//     * to populate the table view.
//     *
//     * @param AppNameCol The table column representing the appointment name.
//     * @param SubNameCol The table column representing the subject name.
//     * @param GpNameCol The table column representing the group name.
//     * @param WeekDayCoil The table column representing the weekday.
//     * @param FromCol The table column representing the start time of the appointment.
//     * @param ToCol The table column representing the end time of the appointment.
//     * @param AppoinementTable The table view to be populated with appointment data.
//     * @param EduLevel The educational level on which to filter the appointments.
//     */
//    public static void getAppointementTable(
//            TableColumn<Appointement, String> AppNameCol,
//            TableColumn<Appointement, String> SubNameCol,
//            TableColumn<Appointement, String> GpNameCol,
//            TableColumn<Appointement, String> WeekDayCoil,
//            TableColumn<Appointement, String> FromCol,
//            TableColumn<Appointement, String> ToCol,
//            TableView<Appointement> AppoinementTable, String EduLevel
//    ) {
//        AppNameCol.setCellValueFactory(new PropertyValueFactory<>("AppName"));
//        SubNameCol.setCellValueFactory(new PropertyValueFactory<>("SubName"));
//        GpNameCol.setCellValueFactory(new PropertyValueFactory<>("GroupName"));
//        WeekDayCoil.setCellValueFactory(new PropertyValueFactory<>("WeekDay"));
//        FromCol.setCellValueFactory(new PropertyValueFactory<>("From"));
//        ToCol.setCellValueFactory(new PropertyValueFactory<>("To"));
//        ObservableList<Appointement> List = Database.Appointement.getAppointementsTable(EduLevel);
//        AppoinementTable.getItems().clear();
//        AppoinementTable.setItems(List);
//    }
//
//    public static void getAppointementTable(
//            TableColumn<Appointement, String> AppNameCol,
//            TableColumn<Appointement, String> SubNameCol,
//            TableColumn<Appointement, String> GpNameCol,
//            TableColumn<Appointement, String> WeekDayCoil,
//            TableColumn<Appointement, String> FromCol,
//            TableColumn<Appointement, String> ToCol,
//            TableColumn<Appointement, String> hallName,
//            TableView<Appointement> AppoinementTable, String EduLevel
//    ) {
//        AppNameCol.setCellValueFactory(new PropertyValueFactory<>("AppName"));
//        SubNameCol.setCellValueFactory(new PropertyValueFactory<>("SubName"));
//        GpNameCol.setCellValueFactory(new PropertyValueFactory<>("GroupName"));
//        WeekDayCoil.setCellValueFactory(new PropertyValueFactory<>("WeekDay"));
//        FromCol.setCellValueFactory(new PropertyValueFactory<>("From"));
//        ToCol.setCellValueFactory(new PropertyValueFactory<>("To"));
//        hallName.setCellValueFactory(new PropertyValueFactory<>("hallName"));
//        ObservableList<Appointement> List = Database.Appointement.getAppointementsTable(EduLevel);
//        AppoinementTable.getItems().clear();
//        AppoinementTable.setItems(List);
//    }
//
//    /**
//     * Configures and populates the appointment table with data by setting the cell value factories
//     * for each column and retrieving the relevant data from the database.
//     *
//     * @param AppNameCol the table column to display the appointment name
//     * @param SubNameCol the table column to display the subject name
//     * @param GpNameCol the table column to display the group name
//     * @param WeekDayCoil the table column to display the weekday
//     * @param FromCol the table column to display the start time of the appointment
//     * @param ToCol the table column to display the end time of the appointment
//     * @param AppoinementTable the table view that will display the appointments
//     */
//    public static void RememberAppointementTable(
//            TableColumn<Appointement, String> AppNameCol,
//            TableColumn<Appointement, String> SubNameCol,
//            TableColumn<Appointement, String> GpNameCol,
//            TableColumn<Appointement, String> WeekDayCoil,
//            TableColumn<Appointement, String> FromCol,
//            TableColumn<Appointement, String> ToCol,
//            TableView<Appointement> AppoinementTable
//    ) {
//        AppNameCol.setCellValueFactory(new PropertyValueFactory<>("AppName"));
//        SubNameCol.setCellValueFactory(new PropertyValueFactory<>("SubName"));
//        GpNameCol.setCellValueFactory(new PropertyValueFactory<>("GroupName"));
//        WeekDayCoil.setCellValueFactory(new PropertyValueFactory<>("WeekDay"));
//        FromCol.setCellValueFactory(new PropertyValueFactory<>("From"));
//        ToCol.setCellValueFactory(new PropertyValueFactory<>("To"));
//        ObservableList<Appointement> List = Database.Appointement.getAllAppointementsTable();
//        AppoinementTable.getItems().clear();
//        AppoinementTable.setItems(List);
//    }
//
//    /**
//     * Populates the appointment table view with data retrieved from the database.
//     * Sets up the columns to map their values to the corresponding properties
//     * in the {@code Appointement} model.
//     *
//     * @param AppNameCol the table column for the appointment name
//     * @param SubNameCol the table column for the subject name
//     * @param GpNameCol the table column for the group name
//     * @param WeekDayCoil the table column for the weekday
//     * @param FromCol the table column for the starting time
//     * @param ToCol the table column for the ending time
//     * @param AppoinementTable the table view to display the list of appointments
//     * @param EduLevel the education level used as a filter for fetching appointments
//     * @param SubjectName the subject name used as a filter for fetching appointments
//     */
//    public static void getAppointementTable(
//            TableColumn<Appointement, String> AppNameCol,
//            TableColumn<Appointement, String> SubNameCol,
//            TableColumn<Appointement, String> GpNameCol,
//            TableColumn<Appointement, String> WeekDayCoil,
//            TableColumn<Appointement, String> FromCol,
//            TableColumn<Appointement, String> ToCol,
//            TableView<Appointement> AppoinementTable, String EduLevel,
//            String SubjectName
//    ) {
//        AppNameCol.setCellValueFactory(new PropertyValueFactory<>("AppName"));
//        SubNameCol.setCellValueFactory(new PropertyValueFactory<>("SubName"));
//        GpNameCol.setCellValueFactory(new PropertyValueFactory<>("GroupName"));
//        WeekDayCoil.setCellValueFactory(new PropertyValueFactory<>("WeekDay"));
//        FromCol.setCellValueFactory(new PropertyValueFactory<>("From"));
//        ToCol.setCellValueFactory(new PropertyValueFactory<>("To"));
//        ObservableList<Appointement> List = Database.Appointement.
//                getAppointementsTable(EduLevel, SubjectName);
//        AppoinementTable.getItems().clear();
//        AppoinementTable.setItems(List);
//    }
//
//    /**
//     * Populates the appointment table view with data retrieved from the database.
//     * Sets up the columns to map their values to the corresponding properties
//     * in the {@code Appointement} model.
//     *
//     * @param AppNameCol the table column for the appointment name
//     * @param SubNameCol the table column for the subject name
//     * @param GpNameCol the table column for the group name
//     * @param WeekDayCoil the table column for the weekday
//     * @param FromCol the table column for the starting time
//     * @param ToCol the table column for the ending time
//     * @param AppoinementTable the table view to display the list of appointments
//     * @param EduLevel the education level used as a filter for fetching appointments
//     * @param SubjectName the subject name used as a filter for fetching appointments
//     */
//    public static void getAppointementTable(
//            TableColumn<Appointement, String> AppNameCol,
//            TableColumn<Appointement, String> SubNameCol,
//            TableColumn<Appointement, String> GpNameCol,
//            TableColumn<Appointement, String> WeekDayCoil,
//            TableColumn<Appointement, String> FromCol,
//            TableColumn<Appointement, String> ToCol,
//            TableColumn<Appointement, String> hallName,
//            TableView<Appointement> AppoinementTable,
//            String EduLevel, String SubjectName
//    ) {
//        AppNameCol.setCellValueFactory(new PropertyValueFactory<>("AppName"));
//        SubNameCol.setCellValueFactory(new PropertyValueFactory<>("SubName"));
//        GpNameCol.setCellValueFactory(new PropertyValueFactory<>("GroupName"));
//        WeekDayCoil.setCellValueFactory(new PropertyValueFactory<>("WeekDay"));
//        FromCol.setCellValueFactory(new PropertyValueFactory<>("From"));
//        ToCol.setCellValueFactory(new PropertyValueFactory<>("To"));
//        hallName.setCellValueFactory(new PropertyValueFactory<>("hallName"));
//        ObservableList<Appointement> List = Database.Appointement.
//                getAppointementsTable(EduLevel, SubjectName);
//        AppoinementTable.getItems().clear();
//        AppoinementTable.setItems(List);
//    }
//
//    /**
//     * Configures and populates the appointment table with data fetched based on the provided parameters.
//     *
//     * @param AppNameCol The table column displaying the appointment name.
//     * @param SubNameCol The table column displaying the subject name.
//     * @param GpNameCol The table column displaying the group name.
//     * @param WeekDayCoil The table column displaying the weekday.
//     * @param FromCol The table column displaying the start time of the appointment.
//     * @param ToCol The table column displaying the end time of the appointment.
//     * @param AppoinementTable The TableView to display the list of appointments.
//     * @param EduLevel The educational level used to filter the appointments.
//     * @param SubjectName The subject name used to filter the appointments.
//     * @param GroupName The group name used to filter the appointments.
//     */
//    public static void getAppointementTable(
//            TableColumn<Appointement, String> AppNameCol,
//            TableColumn<Appointement, String> SubNameCol,
//            TableColumn<Appointement, String> GpNameCol,
//            TableColumn<Appointement, String> WeekDayCoil,
//            TableColumn<Appointement, String> FromCol,
//            TableColumn<Appointement, String> ToCol,
//            TableView<Appointement> AppoinementTable, String EduLevel,
//            String SubjectName, String GroupName
//    ) {
//        AppNameCol.setCellValueFactory(new PropertyValueFactory<>("AppName"));
//        SubNameCol.setCellValueFactory(new PropertyValueFactory<>("SubName"));
//        GpNameCol.setCellValueFactory(new PropertyValueFactory<>("GroupName"));
//        WeekDayCoil.setCellValueFactory(new PropertyValueFactory<>("WeekDay"));
//        FromCol.setCellValueFactory(new PropertyValueFactory<>("From"));
//        ToCol.setCellValueFactory(new PropertyValueFactory<>("To"));
//        ObservableList<Appointement> List = Database.Appointement.
//                getAppointementsTable(EduLevel, SubjectName, GroupName);
//        AppoinementTable.getItems().clear();
//        AppoinementTable.setItems(List);
//    }
//
//    /**
//     * Populates the appointment details table by setting cell value factories for table columns
//     * and updating the table with the relevant data from the database.
//     *
//     * @param App_AppTable the table column displaying the appointment name
//     * @param Date_AppTable the table column displaying the appointment date
//     * @param Att_AppTable the table column displaying the number of attendees
//     * @param Abs_AppTable the table column displaying the number of absentees
//     * @param AppointementsTable the main table view to display the appointment data
//     * @param EduLevel the education level used to filter the appointment data
//     * @param GroupName the group name used to filter the appointment data
//     */
//    public static void appointementDetailsTable(
//            TableColumn<Appointement, String> App_AppTable,
//            TableColumn<Appointement, String> Date_AppTable,
//            TableColumn<Appointement, String> Att_AppTable,
//            TableColumn<Appointement, String> Abs_AppTable,
//            TableView<Appointement> AppointementsTable,
//            String EduLevel, String GroupName
//    ) {
//        App_AppTable.setCellValueFactory(new PropertyValueFactory<>("AppName"));
//        Date_AppTable.setCellValueFactory(new PropertyValueFactory<>("Date"));
//        Att_AppTable.setCellValueFactory(new PropertyValueFactory<>("NumOfAtt"));
//        Abs_AppTable.setCellValueFactory(new PropertyValueFactory<>("NumOfAbsc"));
//        ObservableList<Appointement> List = Database.Appointement.
//                appointementsDetailsTable(EduLevel, GroupName);
//        AppointementsTable.getItems().clear();
//        AppointementsTable.setItems(List);
//        sortTableCol(AppointementsTable, Date_AppTable, "Desc");
//    }
//
//    /**
//     * Configures table columns for displaying assignment data and populates the table with assignment records
//     * retrieved from the database based on the educational level.
//     *
//     * @param SessionNoCol The table column representing the session number of the assignment.
//     * @param SubjectNameCol The table column representing the subject name of the assignment.
//     * @param GroupNameCol The table column representing the group name of the assignment.
//     * @param ContentCol The table column representing the content or description of the assignment.
//     * @param QuesNoCol The table column representing the number of questions in the assignment.
//     * @param MaxDegreeCol The table column representing the maximum degree or score of the assignment.
//     * @param PublishDateCol The table column representing the publish date of the assignment.
//     * @param RecieveDateCol The table column representing the receive date or submission deadline of the assignment.
//     * @param userCol The table column representing the username of the individual associated with the assignment.
//     * @param AssignmentTable The table view that displays the assignments.
//     * @param EduLevel The educational level filter used to retrieve assignments from the database.
//     */
//    public static void getAssignmentTable(
//            TableColumn<Assignments, String> SessionNoCol,
//            TableColumn<Assignments, String> SubjectNameCol,
//            TableColumn<Assignments, String> GroupNameCol,
//            TableColumn<Assignments, String> ContentCol,
//            TableColumn<Assignments, String> QuesNoCol,
//            TableColumn<Assignments, String> MaxDegreeCol,
//            TableColumn<Assignments, String> PublishDateCol,
//            TableColumn<Assignments, String> RecieveDateCol,
//            TableColumn<Assignments, String> userCol,
//            TableView<Assignments> AssignmentTable, String EduLevel
//    ) {
//        SessionNoCol.setCellValueFactory(new PropertyValueFactory<>("SessionNo"));
//        SubjectNameCol.setCellValueFactory(new PropertyValueFactory<>("SubjectName"));
//        GroupNameCol.setCellValueFactory(new PropertyValueFactory<>("GroupName"));
//        ContentCol.setCellValueFactory(new PropertyValueFactory<>("Content"));
//        QuesNoCol.setCellValueFactory(new PropertyValueFactory<>("QuesNo"));
//        MaxDegreeCol.setCellValueFactory(new PropertyValueFactory<>("MaxDegree"));
//        PublishDateCol.setCellValueFactory(new PropertyValueFactory<>("PublishDate"));
//        RecieveDateCol.setCellValueFactory(new PropertyValueFactory<>("RecieveDate"));
//        userCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
//        ObservableList<Assignments> List = Database.Assignments.getAssignmentsTable(EduLevel);
//        AssignmentTable.getItems().clear();
//        AssignmentTable.setItems(List);
//    }
//
//    /**
//     * Populates the assignment table with data retrieved from the database based on the specified
//     * educational level and subject name. This method configures the table columns and sets
//     * the table data.
//     *
//     * @param SessionNoCol the table column representing the session number
//     * @param SubjectNameCol the table column representing the subject name
//     * @param GroupNameCol the table column representing the group name
//     * @param ContentCol the table column representing the assignment content
//     * @param QuesNoCol the table column representing the number of questions
//     * @param MaxDegreeCol the table column representing the maximum degree for the assignment
//     * @param PublishDateCol the table column representing the publish date of the assignment
//     * @param RecieveDateCol the table column representing the receive date of the assignment
//     * @param userCol the table column representing the username associated with the assignment
//     * @param AssignmentTable the table view to be populated with assignments
//     * @param EduLevel the educational level used to filter the assignments
//     * @param SubjectName the subject name used to filter the assignments
//     */
//    public static void getAssignmentTable(
//            TableColumn<Assignments, String> SessionNoCol,
//            TableColumn<Assignments, String> SubjectNameCol,
//            TableColumn<Assignments, String> GroupNameCol,
//            TableColumn<Assignments, String> ContentCol,
//            TableColumn<Assignments, String> QuesNoCol,
//            TableColumn<Assignments, String> MaxDegreeCol,
//            TableColumn<Assignments, String> PublishDateCol,
//            TableColumn<Assignments, String> RecieveDateCol,
//            TableColumn<Assignments, String> userCol,
//            TableView<Assignments> AssignmentTable, String EduLevel,
//            String SubjectName
//    ) {
//        SessionNoCol.setCellValueFactory(new PropertyValueFactory<>("SessionNo"));
//        SubjectNameCol.setCellValueFactory(new PropertyValueFactory<>("SubjectName"));
//        GroupNameCol.setCellValueFactory(new PropertyValueFactory<>("GroupName"));
//        ContentCol.setCellValueFactory(new PropertyValueFactory<>("Content"));
//        QuesNoCol.setCellValueFactory(new PropertyValueFactory<>("QuesNo"));
//        MaxDegreeCol.setCellValueFactory(new PropertyValueFactory<>("MaxDegree"));
//        PublishDateCol.setCellValueFactory(new PropertyValueFactory<>("PublishDate"));
//        RecieveDateCol.setCellValueFactory(new PropertyValueFactory<>("RecieveDate"));
//        userCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
//        ObservableList<Assignments> List = Database.Assignments.
//                getAssignmentsTable(EduLevel, SubjectName);
//        AssignmentTable.getItems().clear();
//        AssignmentTable.setItems(List);
//    }
//
//    /**
//     * Configures the assignment table by setting up the cell value factories
//     * for the table columns and populates the table with data retrieved
//     * from the database based on the specified education level, subject name, and group name.
//     *
//     * @param SessionNoCol the table column for displaying the session number of the assignment
//     * @param SubjectNameCol the table column for displaying the subject name of the assignment
//     * @param GroupNameCol the table column for displaying the group name of the assignment
//     * @param ContentCol the table column for displaying the content of the assignment
//     * @param QuesNoCol the table column for displaying the number of questions in the assignment
//     * @param MaxDegreeCol the table column for displaying the maximum degree for the assignment
//     * @param PublishDateCol the table column for displaying the publish date of the assignment
//     * @param RecieveDateCol the table column for displaying the receive date of the assignment
//     * @param userCol the table column for displaying the username associated with the assignment
//     * @param AssignmentTable the table view that will display the assignments
//     * @param EduLevel the education level to filter the assignments
//     * @param SubjectName the subject name to filter the assignments
//     * @param GroupName the group name to filter the assignments
//     */
//    public static void getAssignmentTable(
//            TableColumn<Assignments, String> SessionNoCol,
//            TableColumn<Assignments, String> SubjectNameCol,
//            TableColumn<Assignments, String> GroupNameCol,
//            TableColumn<Assignments, String> ContentCol,
//            TableColumn<Assignments, String> QuesNoCol,
//            TableColumn<Assignments, String> MaxDegreeCol,
//            TableColumn<Assignments, String> PublishDateCol,
//            TableColumn<Assignments, String> RecieveDateCol,
//            TableColumn<Assignments, String> userCol,
//            TableView<Assignments> AssignmentTable, String EduLevel,
//            String SubjectName, String GroupName
//    ) {
//        SessionNoCol.setCellValueFactory(new PropertyValueFactory<>("SessionNo"));
//        SubjectNameCol.setCellValueFactory(new PropertyValueFactory<>("SubjectName"));
//        GroupNameCol.setCellValueFactory(new PropertyValueFactory<>("GroupName"));
//        ContentCol.setCellValueFactory(new PropertyValueFactory<>("Content"));
//        QuesNoCol.setCellValueFactory(new PropertyValueFactory<>("QuesNo"));
//        MaxDegreeCol.setCellValueFactory(new PropertyValueFactory<>("MaxDegree"));
//        PublishDateCol.setCellValueFactory(new PropertyValueFactory<>("PublishDate"));
//        RecieveDateCol.setCellValueFactory(new PropertyValueFactory<>("RecieveDate"));
//        userCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
//        ObservableList<Assignments> List = Database.Assignments.
//                getAssignmentsTable(EduLevel, SubjectName, GroupName);
//        AssignmentTable.getItems().clear();
//        AssignmentTable.setItems(List);
//    }
//
//    /**
//     * Configures the columns of the Exam table with corresponding data properties retrieved from the database
//     * and binds the data to the given TableView.
//     *
//     * @param SessionNoCol the table column to display the session number of the exam
//     * @param SubjectNameCol the table column to display the subject name of the exam
//     * @param GroupNameCol the table column to display the group name associated with the exam
//     * @param ExamTypeCol the table column to display the type of the exam
//     * @param ContentCol the table column to display the content or title of the exam
//     * @param QuesNoCol the table column to display the number of questions in the exam
//     * @param DegreeCol the table column to display the degree or score information of the exam
//     * @param PublishDateCol the table column to display the publish date of the exam
//     * @param userCol the table column to display the username of the creator or publisher of the exam
//     * @param ExamTable the TableView to bind and display the exam data
//     * @param EduLevel the educational level used as a filter criterion for fetching exam data from the database
//     */
//    public static void getExamsTable(
//            TableColumn<Exam, String> SessionNoCol,
//            TableColumn<Exam, String> SubjectNameCol,
//            TableColumn<Exam, String> GroupNameCol,
//            TableColumn<Exam, String> ExamTypeCol,
//            TableColumn<Exam, String> ContentCol,
//            TableColumn<Exam, String> QuesNoCol,
//            TableColumn<Exam, String> DegreeCol,
//            TableColumn<Exam, String> PublishDateCol,
//            TableColumn<Exam, String> userCol,
//            TableView<Exam> ExamTable, String EduLevel
//    ) {
//        SessionNoCol.setCellValueFactory(new PropertyValueFactory<>("SessionNo"));
//        SubjectNameCol.setCellValueFactory(new PropertyValueFactory<>("SubName"));
//        GroupNameCol.setCellValueFactory(new PropertyValueFactory<>("GpName"));
//        ExamTypeCol.setCellValueFactory(new PropertyValueFactory<>("ExamType"));
//        ContentCol.setCellValueFactory(new PropertyValueFactory<>("Content"));
//        QuesNoCol.setCellValueFactory(new PropertyValueFactory<>("QuestionNo"));
//        DegreeCol.setCellValueFactory(new PropertyValueFactory<>("Degree"));
//        PublishDateCol.setCellValueFactory(new PropertyValueFactory<>("PublishDate"));
//        userCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
//        ObservableList<Exam> List = Database.Exam.getExamsTable(EduLevel);
//        ExamTable.getItems().clear();
//        ExamTable.setItems(List);
//    }
//
//    /**
//     * Sets up the columns of a TableView for displaying exam information and populates the table
//     * with data retrieved from a database based on the provided education level and subject name.
//     *
//     * @param SessionNoCol    the TableColumn representing the session number of the exam
//     * @param SubjectNameCol  the TableColumn representing the subject name of the exam
//     * @param GroupNameCol    the TableColumn representing the group name associated with the exam
//     * @param ExamTypeCol     the TableColumn representing the type of exam
//     * @param ContentCol      the TableColumn representing the content or description of the exam
//     * @param QuesNoCol       the TableColumn representing the number of questions in the exam
//     * @param DegreeCol       the TableColumn representing the degree/score value of the exam
//     * @param PublishDateCol  the TableColumn representing the publish date of the exam
//     * @param userCol         the TableColumn representing the name of the user who published the exam
//     * @param ExamTable       the TableView that will display the list of exams
//     * @param EduLevel        the educational level used to filter the exams retrieved from the database
//     * @param subjectName     the name of the subject used to filter the exams retrieved from the database
//     */
//    public static void getExamsTable(
//            TableColumn<Exam, String> SessionNoCol,
//            TableColumn<Exam, String> SubjectNameCol,
//            TableColumn<Exam, String> GroupNameCol,
//            TableColumn<Exam, String> ExamTypeCol,
//            TableColumn<Exam, String> ContentCol,
//            TableColumn<Exam, String> QuesNoCol,
//            TableColumn<Exam, String> DegreeCol,
//            TableColumn<Exam, String> PublishDateCol,
//            TableColumn<Exam, String> userCol,
//            TableView<Exam> ExamTable, String EduLevel, String subjectName
//    ) {
//        SessionNoCol.setCellValueFactory(new PropertyValueFactory<>("SessionNo"));
//        SubjectNameCol.setCellValueFactory(new PropertyValueFactory<>("SubName"));
//        GroupNameCol.setCellValueFactory(new PropertyValueFactory<>("GpName"));
//        ExamTypeCol.setCellValueFactory(new PropertyValueFactory<>("ExamType"));
//        ContentCol.setCellValueFactory(new PropertyValueFactory<>("Content"));
//        QuesNoCol.setCellValueFactory(new PropertyValueFactory<>("QuestionNo"));
//        DegreeCol.setCellValueFactory(new PropertyValueFactory<>("Degree"));
//        PublishDateCol.setCellValueFactory(new PropertyValueFactory<>("PublishDate"));
//        userCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
//        ObservableList<Exam> List = Database.Exam.getExamsTable(EduLevel, subjectName);
//        ExamTable.getItems().clear();
//        ExamTable.setItems(List);
//    }
//
//    /**
//     * Populates the provided table columns and table view with exam data fetched from the database
//     * for the specified education level, subject name, and group name.
//     *
//     * @param SessionNoCol TableColumn for displaying the session number of the exam.
//     * @param SubjectNameCol TableColumn for displaying the subject name of the exam.
//     * @param GroupNameCol TableColumn for displaying the group name of the exam.
//     * @param ExamTypeCol TableColumn for displaying the type of the exam.
//     * @param ContentCol TableColumn for displaying the content or description of the exam.
//     * @param QuesNoCol TableColumn for displaying the number of questions in the exam.
//     * @param DegreeCol TableColumn for displaying the degree or grade associated with the exam.
//     * @param PublishDateCol TableColumn for displaying the publish date of the exam.
//     * @param userCol TableColumn for displaying the username of the user who created or published the exam.
//     * @param ExamTable TableView to be populated with the exam data.
//     * @param EduLevel The education level filter for fetching the exam data.
//     * @param subjectName The subject name filter for fetching the exam data.
//     * @param GroupName The group name filter for fetching the exam data.
//     */
//    public static void getExamsTable(
//            TableColumn<Exam, String> SessionNoCol,
//            TableColumn<Exam, String> SubjectNameCol,
//            TableColumn<Exam, String> GroupNameCol,
//            TableColumn<Exam, String> ExamTypeCol,
//            TableColumn<Exam, String> ContentCol,
//            TableColumn<Exam, String> QuesNoCol,
//            TableColumn<Exam, String> DegreeCol,
//            TableColumn<Exam, String> PublishDateCol,
//            TableColumn<Exam, String> userCol,
//            TableView<Exam> ExamTable, String EduLevel, String subjectName,
//            String GroupName
//    ) {
//        SessionNoCol.setCellValueFactory(new PropertyValueFactory<>("SessionNo"));
//        SubjectNameCol.setCellValueFactory(new PropertyValueFactory<>("SubName"));
//        GroupNameCol.setCellValueFactory(new PropertyValueFactory<>("GpName"));
//        ExamTypeCol.setCellValueFactory(new PropertyValueFactory<>("ExamType"));
//        ContentCol.setCellValueFactory(new PropertyValueFactory<>("Content"));
//        QuesNoCol.setCellValueFactory(new PropertyValueFactory<>("QuestionNo"));
//        DegreeCol.setCellValueFactory(new PropertyValueFactory<>("Degree"));
//        PublishDateCol.setCellValueFactory(new PropertyValueFactory<>("PublishDate"));
//        userCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
//        ObservableList<Exam> List = Database.Exam.getExamsTable(EduLevel,
//                subjectName, GroupName);
//        ExamTable.getItems().clear();
//        ExamTable.setItems(List);
//    }
//
//    /**
//     * Configures and populates the specified TableView with exam data based on
//     * the provided parameters. Maps data fields to the corresponding table columns,
//     * fetches the relevant exam data, and updates the TableView accordingly.
//     *
//     * @param SessionNoCol the table column for displaying session numbers
//     * @param SubjectNameCol the table column for displaying subject names
//     * @param GroupNameCol the table column for displaying group names
//     * @param ExamTypeCol the table column for displaying exam types
//     * @param ContentCol the table column for displaying exam content
//     * @param QuesNoCol the table column for displaying the number of questions
//     * @param DegreeCol the table column for displaying exam degrees
//     * @param PublishDateCol the table column for displaying publication dates
//     * @param userCol the table column for displaying usernames
//     * @param ExamTable the TableView object to populate with exam data
//     * @param EduLevel the education level filter for fetching exam data
//     * @param subjectName the subject name filter for fetching exam data
//     * @param GroupName the group name filter for fetching exam data
//     * @param ExamType the exam type filter for fetching exam data
//     */
//    public static void getExamsTable(
//            TableColumn<Exam, String> SessionNoCol,
//            TableColumn<Exam, String> SubjectNameCol,
//            TableColumn<Exam, String> GroupNameCol,
//            TableColumn<Exam, String> ExamTypeCol,
//            TableColumn<Exam, String> ContentCol,
//            TableColumn<Exam, String> QuesNoCol,
//            TableColumn<Exam, String> DegreeCol,
//            TableColumn<Exam, String> PublishDateCol,
//            TableColumn<Exam, String> userCol,
//            TableView<Exam> ExamTable, String EduLevel, String subjectName,
//            String GroupName, String ExamType
//    ) {
//        SessionNoCol.setCellValueFactory(new PropertyValueFactory<>("SessionNo"));
//        SubjectNameCol.setCellValueFactory(new PropertyValueFactory<>("SubName"));
//        GroupNameCol.setCellValueFactory(new PropertyValueFactory<>("GpName"));
//        ExamTypeCol.setCellValueFactory(new PropertyValueFactory<>("ExamType"));
//        ContentCol.setCellValueFactory(new PropertyValueFactory<>("Content"));
//        QuesNoCol.setCellValueFactory(new PropertyValueFactory<>("QuestionNo"));
//        DegreeCol.setCellValueFactory(new PropertyValueFactory<>("Degree"));
//        PublishDateCol.setCellValueFactory(new PropertyValueFactory<>("PublishDate"));
//        userCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
//        ObservableList<Exam> List = Database.Exam.getExamsTable(EduLevel,
//                subjectName, GroupName, ExamType);
//        ExamTable.getItems().clear();
//        ExamTable.setItems(List);
//    }
//
//    /**
//     * Configures and populates a table view to display student information.
//     *
//     * @param IdCol        the table column to display student IDs
//     * @param NameCol      the table column to display student names
//     * @param BalanceCol   the table column to display student balances
//     * @param PhoneCol     the table column to display student phone numbers
//     * @param StudentTable the table to be populated with student data
//     * @param EduLevel     the education level used to filter students
//     */
//    public static void getStudentsTable(
//            TableColumn<Student, String> IdCol,
//            TableColumn<Student, String> NameCol,
//            TableColumn<Student, String> BalanceCol,
//            TableColumn<Student, String> PhoneCol,
//            TableView<Student> StudentTable, String EduLevel
//    ) {
//        IdCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
//        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        BalanceCol.setCellValueFactory(new PropertyValueFactory<>("Balance"));
//        PhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
//        ObservableList<Student> List = Database.Student.getStudentTable(EduLevel);
//        StudentTable.getItems().clear();
//        StudentTable.setItems(List);
//    }
//  /**
//   * Configures the specified TableColumns with the appropriate property bindings
//   * for a list of Student objects and populates the given TableView with the data.
//   *
//   * @param IdCol the TableColumn to display the student's ID
//   * @param NameCol the TableColumn to display the student's name
//   * @param Phone1Col the TableColumn to display the first parent's phone number
//   * @param Phone2Col the TableColumn to display the second parent's phone number
//   * @param State1Col the TableColumn to display the first parent's state label
//   * @param State2Col the TableColumn to display the second parent's state label
//   * @param PhonesTable the TableView to populate with the list of Student objects
//   * @param ID the identifier used to retrieve specific Student data
//   * @return an ObservableList containing the Student objects populated in the TableView
//   */
//  public static ObservableList<Student> getStudentPhonesTable(
//            TableColumn<Student, String> IdCol,
//            TableColumn<Student, String> NameCol,
//            TableColumn<Student, String> Phone1Col,
//            TableColumn<Student, String> Phone2Col,
//            TableColumn<Student, Label> State1Col,
//            TableColumn<Student, Label> State2Col,
//            TableView<Student> PhonesTable, String ID
//    ) {
//        IdCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
//        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        Phone1Col.setCellValueFactory(new PropertyValueFactory<>("parentPhone1"));
//        Phone2Col.setCellValueFactory(new PropertyValueFactory<>("parentPhone2"));
//        State1Col.setCellValueFactory(new PropertyValueFactory<>("state1"));
//        State2Col.setCellValueFactory(new PropertyValueFactory<>("state2"));
//        ObservableList<Student> List = Database.Student.getStudentPhones(ID);
//        PhonesTable.setItems(List);
//        return List;
//    }
//
//    /**
//     * Populates a TableView with students belonging to a specific educational level and group,
//     * and returns the list of students added to the table.
//     *
//     * @param IdCol The TableColumn representing the ID field of the student.
//     * @param NameCol The TableColumn representing the Name field of the student.
//     * @param StudentTable The TableView that will display the list of students.
//     * @param EduLevel The educational level associated with the students to be displayed.
//     * @param GroupName The group name associated with the students to be displayed.
//     * @return An ObservableList of Student objects matching the educational level and group name.
//     */
//    public static ObservableList<Student> getStudentsInGroupTable(
//            TableColumn<Student, String> IdCol,
//            TableColumn<Student, String> NameCol,
//            TableView<Student> StudentTable, String EduLevel, String GroupName
//    ) {
//        IdCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
//        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        ObservableList<Student> List = GrpHasStd.getStudentsInGroupTable(EduLevel, GroupName);
//        StudentTable.getItems().clear();
//        StudentTable.setItems(List);
//        return List;
//    }
//
//    /**
//     * Populates the student attendance table with students belonging to a specific
//     * group, educational level, and subject. It binds the provided table columns
//     * to the corresponding properties in the Student objects and updates the table view.
//     *
//     * @param IdCol the table column for displaying the student IDs
//     * @param NameCol the table column for displaying the student names
//     * @param WalletBallanceCol the table column for displaying the students' wallet balances
//     * @param StudentTable the table view to be populated with student data
//     * @param EduLevel the education level to filter the students
//     * @param GroupName the specific group name to filter the students
//     * @param SubjectName the name of the subject to filter the students
//     */
//    public static void getStudentsInGroup_AttendanceTable(
//            TableColumn<Student, String> IdCol,
//            TableColumn<Student, String> NameCol,
//            TableColumn<Student, String> WalletBallanceCol,
//            TableView<Student> StudentTable, String EduLevel, String GroupName,
//            String SubjectName
//    ) {
//        IdCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
//        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        WalletBallanceCol.setCellValueFactory(new PropertyValueFactory<>("Balance"));
//        ObservableList<Student> List = GrpHasStd.
//                getStudentsInGroupAttendaceTable(EduLevel, GroupName, SubjectName);
//        StudentTable.getItems().clear();
//        StudentTable.setItems(List);
//    }
//
//    /**
//     * Populates a student attendance table with details for students
//     * in a specified educational level and group.
//     * The table columns display the student's ID, name,
//     * number of attendances, and number of absences.
//     *
//     * @param code_AllStudentsTable the table column that displays student IDs
//     * @param name_AllStudents the table column that displays student names
//     * @param att_AllStudentsTable the table column that displays the number of attendances
//     * @param abs_AllStudentsTable the table column that displays the number of absences
//     * @param AllStudentsTable the table view that holds the list of students and their attendance details
//     * @param EduLevel the educational level of interest (e.g., grade or year level)
//     * @param GroupName the name of the group whose attendance details need to be shown
//     */
//    public static void getStudentsInGroup_AttendanceDetails(
//            TableColumn code_AllStudentsTable, TableColumn name_AllStudents,
//            TableColumn att_AllStudentsTable, TableColumn abs_AllStudentsTable,
//            TableView AllStudentsTable, String EduLevel, String GroupName
//    ) {
//        code_AllStudentsTable.setCellValueFactory(new PropertyValueFactory<>("ID"));
//        name_AllStudents.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        att_AllStudentsTable.setCellValueFactory(new PropertyValueFactory<>("NumOfAttendance"));
//        abs_AllStudentsTable.setCellValueFactory(new PropertyValueFactory<>("NumOfAbscence"));
//        ObservableList<Student> List = GrpHasStd.
//                studentsInGroupDetails(EduLevel, GroupName);
//        AllStudentsTable.getItems().clear();
//        AllStudentsTable.setItems(List);
//    }
//
//    /**
//     * Populates a given student table with data about students' attendance and details
//     * filtered by education level, group name, subject name, and a specific value.
//     *
//     * @param IdCol the table column for displaying student IDs
//     * @param NameCol the table column for displaying student names
//     * @param WalletBallanceCol the table column for displaying students' wallet balances
//     * @param StudentTable the TableView where student details and attendance information must populate
//     * @param EduLevel the education level used to filter the students
//     * @param GroupName the group name used to filter the students
//     * @param SubjectName the subject name used to filter the students
//     * @param Val an additional filter value to retrieve specific attendance data
//     */
//    public static void getStudentsInGroup_AttendanceTable(
//            TableColumn<Student, String> IdCol,
//            TableColumn<Student, String> NameCol,
//            TableColumn<Student, String> WalletBallanceCol,
//            TableView<Student> StudentTable, String EduLevel, String GroupName,
//            String SubjectName, String Val
//    ) {
//        IdCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
//        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        WalletBallanceCol.setCellValueFactory(new PropertyValueFactory<>("Balance"));
//        ObservableList<Student> List = GrpHasStd.
//                getStudentsInGroupAttendaceTable(EduLevel, GroupName, SubjectName, Val);
//        StudentTable.getItems().clear();
//        StudentTable.setItems(List);
//    }
//
//    /**
//     * Populates the provided table with students belonging to a specific group based on the given educational level,
//     * group name, and search criteria. The method also sets up the cell value factories for ID and Name columns.
//     *
//     * @param IdCol         The TableColumn representing the ID column to set the cell value factory.
//     * @param NameCol       The TableColumn representing the Name column to set the cell value factory.
//     * @param StudentTable  The TableView to display the students.
//     * @param EduLevel      The educational level used to filter students.
//     * @param GroupName     The name of the group used to filter students.
//     * @param searchValue   The search criteria for filtering students.
//     */
//    public static void getStudentsInGroupTable(
//            TableColumn<Student, String> IdCol,
//            TableColumn<Student, String> NameCol,
//            TableView<Student> StudentTable, String EduLevel,
//            String GroupName, String searchValue
//    ) {
//        IdCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
//        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        ObservableList<Student> List = GrpHasStd.getStudentsInGroupTable(EduLevel,
//                GroupName, searchValue);
//        StudentTable.getItems().clear();
//        StudentTable.setItems(List);
//    }
//
//    /**
//     * Populates a provided TableView with student data from the archive based on the given parameters.
//     * This method sets up table columns with data values and retrieves student records from the archive
//     * database to populate the table.
//     *
//     * @param selectionCol the TableColumn representing the selection checkbox for each student
//     * @param archIdCol the TableColumn representing the archived student ID
//     * @param archNameCol the TableColumn representing the archived student name
//     * @param parentPhoneCol the TableColumn representing the parent's phone number of the archived student
//     * @param StudentTable the TableView that will be populated with archived student data
//     * @param EduLevel the education level associated with the student group
//     * @param GroupName the name of the student group
//     * @param SubjectName the name of the subject associated with the student data
//     * @param CurDate the current date associated with the data retrieval operation
//     * @param AppoiID the appointment ID linked to the archived student data
//     * @param AppName the application name or identifier relevant for the data retrieval
//     */
//    public static void getStudentsArchiveTable(
//            TableColumn<Student, CheckBox> selectionCol,
//            TableColumn<Student, String> archIdCol,
//            TableColumn<Student, String> archNameCol,
//            TableColumn<Student, String> parentPhoneCol,
//            TableView<Student> StudentTable, String EduLevel,
//            String GroupName, String SubjectName, String CurDate, String AppoiID,
//            String AppName
//    ) {
//        selectionCol.setCellValueFactory(new PropertyValueFactory<Student, CheckBox>("checkBox"));
//        archIdCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
//        archNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        parentPhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
//        ObservableList<Student> List = Database.Attendace_archive.getStudentTable(
//                EduLevel, GroupName, SubjectName, CurDate, AppoiID, AppName);
//        StudentTable.getItems().clear();
//        StudentTable.setItems(List);
//    }
//
//    /**
//     * Configures a table view to display student absence information with specified columns and data retrieved
//     * based on educational level, group name, and other parameters.
//     *
//     * @param selectionCol the table column for displaying selection checkboxes for students
//     * @param archIdCol the table column for displaying student archive IDs
//     * @param archNameCol the table column for displaying student archive names
//     * @param parentPhoneCol the table column for displaying parents' phone numbers
//     * @param StudentTable the table view where the student absence information will be displayed
//     * @param EduLevel the education level of the students
//     * @param GroupName the group name of the students
//     * @param CurDate the current date being used to filter student absence records
//     * @param AppoiID the appointment ID associated with the student records
//     * @param AppName the appointment name used for filtering student data
//     * @param subjectName the subject name used for filtering student data
//     */
//    public static void getAbcenseStudentTable(
//            TableColumn<Student, CheckBox> selectionCol,
//            TableColumn<Student, String> archIdCol,
//            TableColumn<Student, String> archNameCol,
//            TableColumn<Student, String> parentPhoneCol,
//            TableView<Student> StudentTable, String EduLevel, String GroupName,
//            String CurDate, String AppoiID, String AppName, String subjectName) {
//        selectionCol.setCellValueFactory(new PropertyValueFactory<Student, CheckBox>("checkBox"));
//        archIdCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
//        archNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        parentPhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
//        ObservableList<Student> List = Database.Attendace_archive.
//                getAbscenceStudents(EduLevel, GroupName, CurDate, AppoiID,
//                        AppName, subjectName);
//        StudentTable.getItems().clear();
//        StudentTable.setItems(List);
//    }
//
//    /**
//     * Configures and populates the student attendance table with data based on the given educational level,
//     * group name, appointment ID, and date. Sets up the table columns with appropriate cell value factories
//     * and updates the table with the retrieved student data.
//     *
//     * @param attIdCol the table column for displaying student IDs
//     * @param attNameCol the table column for displaying student names
//     * @param studentAttendancedTable the table view to be populated with attendance data
//     * @param EduLevel the educational level used to filter the student data
//     * @param GroupName the group name used to filter the student data
//     * @param AppointementID the appointment ID used to filter the student data
//     * @param Date the date used to filter the student data
//     */
//    public static void getStudentsAttendencedTable(
//            TableColumn<Student, String> attIdCol,
//            TableColumn<Student, String> attNameCol,
//            TableView<Student> studentAttendancedTable, String EduLevel,
//            String GroupName, String AppointementID, String Date
//    ) {
//        attIdCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
//        attNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        ObservableList<Student> List = Database.Student.
//                getStudentAttendencedTable(EduLevel, GroupName, AppointementID, Date);
//        studentAttendancedTable.getItems().clear();
//        studentAttendancedTable.setItems(List);
//    }
//
//    /**
//     * Populates the attendance table for students based on the provided parameters.
//     *
//     * @param attIdCol          The table column for the student ID.
//     * @param attNameCol        The table column for the student name.
//     * @param studentAttendancedTable The table view displaying the attendance data.
//     * @param EduLevel          The education level of the students.
//     * @param GroupName         The name of the group/class.
//     * @param AppointementID    The appointment ID for the attendance session.
//     * @param Date              The date for which attendance data is fetched.
//     */
//    public static void getAttendencedDateTable(
//            TableColumn<Student, String> attIdCol,
//            TableColumn<Student, String> attNameCol,
//            TableView<Student> studentAttendancedTable, String EduLevel,
//            String GroupName, String AppointementID, String Date
//    ) {
//        attIdCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
//        attNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        ObservableList<Student> List = Database.Student.getAttendencedDateTable(
//                EduLevel, GroupName, AppointementID, Date);
//        studentAttendancedTable.getItems().clear();
//        studentAttendancedTable.setItems(List);
//    }
//
//    /**
//     * Populates a given table with attendance details of a student based on specified parameters.
//     *
//     * @param APP_Att The TableColumn representing the application name for attendance details.
//     * @param Date_Att The TableColumn representing the date of attendance.
//     * @param PType_Att The TableColumn representing the payment type for attendance records.
//     * @param Att_Abs_Table The TableView where attendance details will be displayed.
//     * @param EduLevel The education level of the student, used to filter attendance details.
//     * @param studentID The unique identifier of the student whose attendance details are being fetched.
//     * @param GroupName The group name associated with the student, used to filter attendance details.
//     */
//    public static void students_AttendanceDetails(
//            TableColumn<Att_Abs_Details, String> APP_Att,
//            TableColumn<Att_Abs_Details, String> Date_Att,
//            TableColumn<Att_Abs_Details, String> PType_Att,
//            TableView<Att_Abs_Details> Att_Abs_Table, String EduLevel,
//            String studentID, String GroupName
//    ) {
//        APP_Att.setCellValueFactory(new PropertyValueFactory<>("appName"));
//        Date_Att.setCellValueFactory(new PropertyValueFactory<>("Date"));
//        PType_Att.setCellValueFactory(new PropertyValueFactory<>("PaymentType"));
//        ObservableList<Att_Abs_Details> List = Attendance.attendanceDetailsTable(
//                EduLevel, studentID, GroupName);
//        Att_Abs_Table.getItems().clear();
//        Att_Abs_Table.setItems(List);
//        sortTableCol(Att_Abs_Table, Date_Att, "Desc");
//    }
//
//    /**
//     * Populates a table with details about student absences based on education level,
//     * student ID, and group name. Configures the behavior and data source for the table columns.
//     *
//     * @param App_Abs The TableColumn representing the application's absence name column.
//     * @param Date_Abs The TableColumn representing the date of absence column.
//     * @param Abs_Table The TableView to be populated with student absence details.
//     * @param EduLevel The education level of the student (e.g., grade or class).
//     * @param studentID The unique identifier of the student for whom absence details are retrieved.
//     * @param GroupName The name of the group or class to filter the student's absence records.
//     */
//    public static void students_AbscenceDetails(
//            TableColumn<Att_Abs_Details, String> App_Abs,
//            TableColumn<Att_Abs_Details, String> Date_Abs,
//            TableView<Att_Abs_Details> Abs_Table, String EduLevel,
//            String studentID, String GroupName
//    ) {
//        App_Abs.setCellValueFactory(new PropertyValueFactory<>("appName"));
//        Date_Abs.setCellValueFactory(new PropertyValueFactory<>("Date"));
//        ObservableList<Att_Abs_Details> List = Absence.
//                abscenceDetailsTable(EduLevel, studentID, GroupName);
//        Abs_Table.getItems().clear();
//        Abs_Table.setItems(List);
//        sortTableCol(Abs_Table, Date_Abs, "Desc");
//    }
//
//    /**
//     * Populates a given TableView with assignment details of students for a specified group and subject.
//     * It sets the cell value factories for each column and binds the data retrieved to the TableView.
//     *
//     * @param IdCol          The column displaying the student IDs.
//     * @param NameCol        The column displaying the student names.
//     * @param SessionCol     The column displaying the session numbers.
//     * @param RecieveDateCol The column displaying the receiving date of the assignment.
//     * @param DegreeCol      The column displaying the degree achieved by the student.
//     * @param MaxDegreeCol   The column displaying the maximum possible degree for the assignment.
//     * @param RatioCol       The column displaying the ratio of achieved to maximum degree.
//     * @param UserCol        The column displaying user details or additional information.
//     * @param StudentTable   The TableView to be populated with students' assignment data.
//     * @param EduLevel       The education level of the students (e.g., undergraduate, graduate).
//     * @param GroupName      The name of the student group.
//     * @param SubjectName    The name of the subject for which data is populated.
//     */
//    public static void getStdsAssignments_InGroupTable_Subject(
//            TableColumn<StudentWorks, String> IdCol,
//            TableColumn<StudentWorks, String> NameCol,
//            TableColumn<StudentWorks, String> SessionCol,
//            TableColumn<StudentWorks, String> RecieveDateCol,
//            TableColumn<StudentWorks, String> DegreeCol,
//            TableColumn<StudentWorks, String> MaxDegreeCol,
//            TableColumn<StudentWorks, String> RatioCol,
//            TableColumn<StudentWorks, String> UserCol,
//            TableView<StudentWorks> StudentTable, String EduLevel,
//            String GroupName, String SubjectName
//    ) {
//        IdCol.setCellValueFactory(new PropertyValueFactory<>("StudentID"));
//        NameCol.setCellValueFactory(new PropertyValueFactory<>("StudentName"));
//        SessionCol.setCellValueFactory(new PropertyValueFactory<>("SessionNo"));
//        RecieveDateCol.setCellValueFactory(new PropertyValueFactory<>("RecieveDate"));
//        DegreeCol.setCellValueFactory(new PropertyValueFactory<>("Degree"));
//        MaxDegreeCol.setCellValueFactory(new PropertyValueFactory<>("MaxDegree"));
//        RatioCol.setCellValueFactory(new PropertyValueFactory<>("Ratio"));
//        UserCol.setCellValueFactory(new PropertyValueFactory<>("User"));
//        ObservableList<StudentWorks> List = StdsAssignments_InGroup_Subject(
//                EduLevel, SubjectName, GroupName);
//        StudentTable.getItems().clear();
//        StudentTable.setItems(List);
//    }
//
//    /**
//     * Populates the given table with student assignment information based on the specified educational level, group, subject, and ID.
//     * Sets the cell value factories for the table columns and assigns the resulting data to the table.
//     *
//     * @param IdCol         The table column for student IDs.
//     * @param NameCol       The table column for student names.
//     * @param SessionCol    The table column for session numbers.
//     * @param RecieveDateCol The table column for the date assignments were received.
//     * @param DegreeCol     The table column for student degrees.
//     * @param MaxDegreeCol  The table column for the maximum degree for an assignment.
//     * @param RatioCol      The table column for the ratio of the obtained degree to the max degree.
//     * @param UserCol       The table column for the user responsible for the assignment.
//     * @param StudentTable  The table to display the student assignment information.
//     * @param EduLevel      The educational level used to filter the assignments.
//     * @param GroupName     The group name used to filter the assignments.
//     * @param SubjectName   The subject name used to filter the assignments.
//     * @param ID            The specific ID used to filter the assignments.
//     */
//    public static void getStdsAssignments_InGroupTable(
//            TableColumn<StudentWorks, String> IdCol,
//            TableColumn<StudentWorks, String> NameCol,
//            TableColumn<StudentWorks, String> SessionCol,
//            TableColumn<StudentWorks, String> RecieveDateCol,
//            TableColumn<StudentWorks, String> DegreeCol,
//            TableColumn<StudentWorks, String> MaxDegreeCol,
//            TableColumn<StudentWorks, String> RatioCol,
//            TableColumn<StudentWorks, String> UserCol,
//            TableView<StudentWorks> StudentTable, String EduLevel,
//            String GroupName, String SubjectName, String ID
//    ) {
//        IdCol.setCellValueFactory(new PropertyValueFactory<>("StudentID"));
//        NameCol.setCellValueFactory(new PropertyValueFactory<>("StudentName"));
//        SessionCol.setCellValueFactory(new PropertyValueFactory<>("SessionNo"));
//        RecieveDateCol.setCellValueFactory(new PropertyValueFactory<>("RecieveDate"));
//        DegreeCol.setCellValueFactory(new PropertyValueFactory<>("Degree"));
//        MaxDegreeCol.setCellValueFactory(new PropertyValueFactory<>("MaxDegree"));
//        RatioCol.setCellValueFactory(new PropertyValueFactory<>("Ratio"));
//        UserCol.setCellValueFactory(new PropertyValueFactory<>("User"));
//        ObservableList<StudentWorks> List
//                = StdsAssignments_InGroup(EduLevel, GroupName, SubjectName, ID);
//        StudentTable.getItems().clear();
//        StudentTable.setItems(List);
//    }
//
//    /**
//     * Configures the table view and its columns for displaying student subject information.
//     * Sets up value factories for the provided columns and populates the table with data
//     * based on the educational level and student ID.
//     *
//     * @param subjNameCol the table column for displaying subject names
//     * @param GrpNameCol the table column for displaying group names
//     * @param DayOfWeekCol the table column for displaying the day of the week
//     * @param FromCol the table column for displaying the start time
//     * @param ToCol the table column for displaying the end time
//     * @param stdSubjTable the TableView to be configured and populated with student subject data
//     * @param EduLevel the educational level used to filter data
//     * @param StdID the student ID used to filter data
//     */
//    public static void getStdSubjTable(
//            TableColumn<StdSubjTable, String> subjNameCol,
//            TableColumn<StdSubjTable, String> GrpNameCol,
//            TableColumn<StdSubjTable, String> DayOfWeekCol,
//            TableColumn<StdSubjTable, String> FromCol,
//            TableColumn<StdSubjTable, String> ToCol,
//            TableView<StdSubjTable> stdSubjTable, String EduLevel, String StdID
//    ) {
//        subjNameCol.setCellValueFactory(new PropertyValueFactory<>("Subject"));
//        GrpNameCol.setCellValueFactory(new PropertyValueFactory<>("Group"));
//        DayOfWeekCol.setCellValueFactory(new PropertyValueFactory<>("Day"));
//        FromCol.setCellValueFactory(new PropertyValueFactory<>("From"));
//        ToCol.setCellValueFactory(new PropertyValueFactory<>("To"));
//        ObservableList<StdSubjTable> List = getStdTable(EduLevel, StdID);
//        stdSubjTable.getItems().clear();
//        stdSubjTable.setItems(List);
//    }
//
//    /**
//     * Searches and updates the student table based on the provided education level and search value.
//     *
//     * @param IdCol        the table column representing student IDs
//     * @param NameCol      the table column representing student names
//     * @param BalanceCol   the table column representing student balances
//     * @param PhoneCol     the table column representing student phone numbers
//     * @param StudentTable the table view to display student data
//     * @param EduLevel     the education level filter used for the search
//     * @param SearchVal    the search value used to filter the students
//     */
//    public static void searchStudentsTable(
//            TableColumn<Student, String> IdCol,
//            TableColumn<Student, String> NameCol,
//            TableColumn<Student, String> BalanceCol,
//            TableColumn<Student, String> PhoneCol,
//            TableView<Student> StudentTable, String EduLevel, String SearchVal
//    ) {// ID, Name, Balance, Phone;
//        IdCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
//        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        BalanceCol.setCellValueFactory(new PropertyValueFactory<>("Balance"));
//        PhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
//        ObservableList<Student> List = searchStudentTable(EduLevel, SearchVal);
//        StudentTable.getItems().clear();
//        StudentTable.setItems(List);
//    }
//
//    /**
//     * Populates the provided table columns and table view with data related to assignments,
//     * based on the specified education level, subject name, and group name.
//     *
//     * @param SessionNoCol the table column to display session numbers of assignments
//     * @param ContentCol the table column to display the content of assignments
//     * @param QuesNoCol the table column to display the number of questions for assignments
//     * @param AssMaxDegreeCol the table column to display the maximum degree or marks for assignments
//     * @param PublishDateCol the table column to display the publish date of assignments
//     * @param AssignmentTable the table view to hold the list of assignments
//     * @param EduLevel the education level to filter the assignments (e.g., primary, secondary)
//     * @param SubjectName the subject name to filter the assignments
//     * @param GroupName the group name to filter the assignments
//     */
//    public static void getAssignmentTable(
//            TableColumn<Assignments, String> SessionNoCol,
//            TableColumn<Assignments, String> ContentCol,
//            TableColumn<Assignments, String> QuesNoCol,
//            TableColumn<Assignments, String> AssMaxDegreeCol,
//            TableColumn<Assignments, String> PublishDateCol,
//            TableView<Assignments> AssignmentTable, String EduLevel,
//            String SubjectName, String GroupName
//    ) {
//        SessionNoCol.setCellValueFactory(new PropertyValueFactory<>("SessionNo"));
//        ContentCol.setCellValueFactory(new PropertyValueFactory<>("Content"));
//        QuesNoCol.setCellValueFactory(new PropertyValueFactory<>("QuesNo"));
//        AssMaxDegreeCol.setCellValueFactory(new PropertyValueFactory<>("MaxDegree"));
//        PublishDateCol.setCellValueFactory(new PropertyValueFactory<>("PublishDate"));
//        ObservableList<Assignments> List = Database.Assignments.
//                getAssignmentsTable(EduLevel, SubjectName, GroupName);
//        AssignmentTable.getItems().clear();
//        AssignmentTable.setItems(List);
//    }
//
//    /**
//     * Populates the provided table columns and table view with information about exams,
//     * based on the specified education level, subject name, and group name.
//     *
//     * @param SessionNoCol The table column to display the session number of assignments.
//     * @param ContentCol The table column to display the content of assignments.
//     * @param ExamTypeCol The table column to display the type of the exam.
//     * @param QuesNoCol The table column to display the number of questions in the exam.
//     * @param AssMaxDegreeCol The table column to display the maximum degree of the assignment.
//     * @param PublishDateCol The table column to display the publish date of the assignment.
//     * @param AssignmentTable The table view to display the list of assignments.
//     * @param EduLevel The educational level (e.g., grade or year) to filter the assignments.
//     * @param SubjectName The subject name to filter the assignments.
//     * @param GroupName The group name or class name to filter the assignments.
//     */
//    public static void getExamTable(
//            TableColumn<Assignments, String> SessionNoCol,
//            TableColumn<Assignments, String> ContentCol,
//            TableColumn<Assignments, String> ExamTypeCol,
//            TableColumn<Assignments, String> QuesNoCol,
//            TableColumn<Assignments, String> AssMaxDegreeCol,
//            TableColumn<Assignments, String> PublishDateCol,
//            TableView<Assignments> AssignmentTable, String EduLevel,
//            String SubjectName, String GroupName
//    ) {
//        SessionNoCol.setCellValueFactory(new PropertyValueFactory<>("SessionNo"));
//        ContentCol.setCellValueFactory(new PropertyValueFactory<>("Content"));
//        ExamTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
//        QuesNoCol.setCellValueFactory(new PropertyValueFactory<>("QuesNo"));
//        AssMaxDegreeCol.setCellValueFactory(new PropertyValueFactory<>("MaxDegree"));
//        PublishDateCol.setCellValueFactory(new PropertyValueFactory<>("PublishDate"));
//        ObservableList<Assignments> List = Database.Assignments.getExamsTable(
//                EduLevel, SubjectName, GroupName);
//        AssignmentTable.getItems().clear();
//        AssignmentTable.setItems(List);
//    }
//
//    /**
//     * Populates the given TableView with assignment data filtered by specific educational parameters and exam type.
//     *
//     * @param SessionNoCol the table column representing the session number
//     * @param ContentCol the table column representing the content of the assignment
//     * @param ExamTypeCol the table column representing the type of the exam
//     * @param QuesNoCol the table column representing the number of questions in the assignment
//     * @param AssMaxDegreeCol the table column representing the maximum degree/score for the assignment
//     * @param PublishDateCol the table column representing the publish date of the assignment
//     * @param AssignmentTable the TableView to be populated with the data
//     * @param EduLevel the educational level used for filtering assignments
//     * @param SubjectName the subject name used for filtering assignments
//     * @param GroupName the group name used for filtering assignments
//     * @param ExamType the specific exam type used to filter the displayed assignments
//     */
//    public static void getExamTypeTable(
//            TableColumn<Assignments, String> SessionNoCol,
//            TableColumn<Assignments, String> ContentCol,
//            TableColumn<Assignments, String> ExamTypeCol,
//            TableColumn<Assignments, String> QuesNoCol,
//            TableColumn<Assignments, String> AssMaxDegreeCol,
//            TableColumn<Assignments, String> PublishDateCol,
//            TableView<Assignments> AssignmentTable, String EduLevel,
//            String SubjectName, String GroupName, String ExamType
//    ) {
//        SessionNoCol.setCellValueFactory(new PropertyValueFactory<>("SessionNo"));
//        ContentCol.setCellValueFactory(new PropertyValueFactory<>("Content"));
//        ExamTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
//        QuesNoCol.setCellValueFactory(new PropertyValueFactory<>("QuesNo"));
//        AssMaxDegreeCol.setCellValueFactory(new PropertyValueFactory<>("MaxDegree"));
//        PublishDateCol.setCellValueFactory(new PropertyValueFactory<>("PublishDate"));
//        ObservableList<Assignments> List = Database.Assignments.getExamsTypeTable(
//                EduLevel, SubjectName, GroupName, ExamType);
//        AssignmentTable.getItems().clear();
//        AssignmentTable.setItems(List);
//    }
//
//    /**
//     * Populates the provided TableView and its columns with data about student exams
//     * in a specific group based on the given educational level, group name, subject name,
//     * and ID. The method links the provided TableColumn objects with respective
//     * properties of the StudentWorks objects.
//     *
//     * @param IdCol        TableColumn representing the student ID.
//     * @param NameCol      TableColumn representing the student name.
//     * @param SessionCol   TableColumn representing the session number.
//     * @param SubExamTypeCol TableColumn representing the subtype of the exam.
//     * @param RecieveDateCol TableColumn representing the receive date of the exam.
//     * @param DegreeCol    TableColumn representing the student's obtained degree.
//     * @param MaxDegreeCol TableColumn representing the maximum degree possible.
//     * @param RatioCol     TableColumn representing the ratio information of the degree.
//     * @param UserCol      TableColumn representing the user information.
//     * @param StudentTable TableView to be populated with the student exam data.
//     * @param EduLevel     String specifying the education level of the students.
//     * @param GroupName    String specifying the name of the group to which the students belong.
//     * @param SubjectName  String specifying the subject for which the exam data applies.
//     * @param ID           String specifying a unique identifier to fetch specific exam data.
//     */
//    public static void getStdExams_InGroupTable(
//            TableColumn<StudentWorks, String> IdCol,
//            TableColumn<StudentWorks, String> NameCol,
//            TableColumn<StudentWorks, String> SessionCol,
//            TableColumn<StudentWorks, String> SubExamTypeCol,
//            TableColumn<StudentWorks, String> RecieveDateCol,
//            TableColumn<StudentWorks, String> DegreeCol,
//            TableColumn<StudentWorks, String> MaxDegreeCol,
//            TableColumn<StudentWorks, String> RatioCol,
//            TableColumn<StudentWorks, String> UserCol,
//            TableView<StudentWorks> StudentTable, String EduLevel,
//            String GroupName, String SubjectName, String ID
//    ) {
//        IdCol.setCellValueFactory(new PropertyValueFactory<>("StudentID"));
//        NameCol.setCellValueFactory(new PropertyValueFactory<>("StudentName"));
//        SessionCol.setCellValueFactory(new PropertyValueFactory<>("SessionNo"));
//        SubExamTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
//        RecieveDateCol.setCellValueFactory(new PropertyValueFactory<>("RecieveDate"));
//        DegreeCol.setCellValueFactory(new PropertyValueFactory<>("Degree"));
//        MaxDegreeCol.setCellValueFactory(new PropertyValueFactory<>("MaxDegree"));
//        RatioCol.setCellValueFactory(new PropertyValueFactory<>("Ratio"));
//        UserCol.setCellValueFactory(new PropertyValueFactory<>("User"));
//        ObservableList<StudentWorks> List
//                = StdsAssignments_InGroup(EduLevel, GroupName, SubjectName, ID);
//        StudentTable.getItems().clear();
//        StudentTable.setItems(List);
//    }
//
//    /**
//     * Configures the specified columns of a table to represent student exam data and
//     * populates the table with data filtered by education level, group name, subject name, and type.
//     * This method sets up cell value factories for each table column and fills the table with the data
//     * retrieved from the corresponding data source.
//     *
//     * @param IdCol the table column representing the student ID
//     * @param NameCol the table column representing the student name
//     * @param SessionCol the table column representing the session number
//     * @param SubExamTypeCol the table column representing the type of the sub-exam
//     * @param RecieveDateCol the table column representing the receive date of the exam
//     * @param DegreeCol the table column representing the degree achieved by the student
//     * @param MaxDegreeCol the table column representing the maximum degree possible for the exam
//     * @param RatioCol the table column representing the ratio achieved by the student
//     * @param UserCol the table column representing the user information
//     * @param StudentTable the table view that will display the student exam data
//     * @param EduLevel the education level used to filter the displayed data
//     * @param GroupName the group name used to filter the displayed data
//     * @param SubjectName the subject name used to filter the displayed data
//     * @param Type the type of examination used to filter the displayed data
//     */
//    public static void getStdExams_InGroupTable_Subject(///////////////////////////////////////////////
//            TableColumn<StudentWorks, String> IdCol,
//            TableColumn<StudentWorks, String> NameCol,
//            TableColumn<StudentWorks, String> SessionCol,
//            TableColumn<StudentWorks, String> SubExamTypeCol,
//            TableColumn<StudentWorks, String> RecieveDateCol,
//            TableColumn<StudentWorks, String> DegreeCol,
//            TableColumn<StudentWorks, String> MaxDegreeCol,
//            TableColumn<StudentWorks, String> RatioCol,
//            TableColumn<StudentWorks, String> UserCol,
//            TableView<StudentWorks> StudentTable, String EduLevel,
//            String GroupName, String SubjectName, String Type
//    ) {
//        IdCol.setCellValueFactory(new PropertyValueFactory<>("StudentID"));
//        NameCol.setCellValueFactory(new PropertyValueFactory<>("StudentName"));
//        SessionCol.setCellValueFactory(new PropertyValueFactory<>("SessionNo"));
//        SubExamTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
//        RecieveDateCol.setCellValueFactory(new PropertyValueFactory<>("RecieveDate"));
//        DegreeCol.setCellValueFactory(new PropertyValueFactory<>("Degree"));
//        MaxDegreeCol.setCellValueFactory(new PropertyValueFactory<>("MaxDegree"));
//        RatioCol.setCellValueFactory(new PropertyValueFactory<>("Ratio"));
//        UserCol.setCellValueFactory(new PropertyValueFactory<>("User"));
//        ObservableList<StudentWorks> List = StdExams_InGroup_Subject(
//                EduLevel, SubjectName, GroupName, Type);
//        StudentTable.getItems().clear();
//        StudentTable.setItems(List);
//    }
//
//    /**
//     * Populates a TableView with student exam data filtered by educational level,
//     * group name, subject name, exam type, and student ID. The method sets up
//     * cell value factories for table columns and updates the TableView with the
//     * retrieved data.
//     *
//     * @param IdCol           TableColumn for displaying the student's ID.
//     * @param NameCol         TableColumn for displaying the student's name.
//     * @param SessionCol      TableColumn for displaying the session number.
//     * @param SubExamTypeCol  TableColumn for displaying the exam type.
//     * @param RecieveDateCol  TableColumn for displaying the receive date of the exam.
//     * @param DegreeCol       TableColumn for displaying the degree achieved.
//     * @param MaxDegreeCol    TableColumn for displaying the maximum degree possible.
//     * @param RatioCol        TableColumn for displaying the ratio of the obtained degree to the maximum degree.
//     * @param UserCol         TableColumn for displaying the user associated with the record.
//     * @param StudentTable    TableView to be populated with student exam records.
//     * @param EduLevel        The educational level to filter the exam data.
//     * @param GroupName       The name of the group to filter the exam data.
//     * @param SubjectName     The subject name to filter the exam data.
//     * @param Type            The type of exam to filter the exam data.
//     * @param ID              The student ID to filter the exam data.
//     */
//    public static void getStdExams_InGroupTable_Subject_StdID(
//            TableColumn<StudentWorks, String> IdCol,
//            TableColumn<StudentWorks, String> NameCol,
//            TableColumn<StudentWorks, String> SessionCol,
//            TableColumn<StudentWorks, String> SubExamTypeCol,
//            TableColumn<StudentWorks, String> RecieveDateCol,
//            TableColumn<StudentWorks, String> DegreeCol,
//            TableColumn<StudentWorks, String> MaxDegreeCol,
//            TableColumn<StudentWorks, String> RatioCol,
//            TableColumn<StudentWorks, String> UserCol,
//            TableView<StudentWorks> StudentTable, String EduLevel,
//            String GroupName, String SubjectName, String Type, String ID
//    ) {
//        IdCol.setCellValueFactory(new PropertyValueFactory<>("StudentID"));
//        NameCol.setCellValueFactory(new PropertyValueFactory<>("StudentName"));
//        SessionCol.setCellValueFactory(new PropertyValueFactory<>("SessionNo"));
//        SubExamTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
//        RecieveDateCol.setCellValueFactory(new PropertyValueFactory<>("RecieveDate"));
//        DegreeCol.setCellValueFactory(new PropertyValueFactory<>("Degree"));
//        MaxDegreeCol.setCellValueFactory(new PropertyValueFactory<>("MaxDegree"));
//        RatioCol.setCellValueFactory(new PropertyValueFactory<>("Ratio"));
//        UserCol.setCellValueFactory(new PropertyValueFactory<>("User"));
//        ObservableList<StudentWorks> List = StdExams_InGroup_Subject(
//                EduLevel, SubjectName, GroupName, Type, ID);
//        StudentTable.getItems().clear();
//        StudentTable.setItems(List);
//    }
//
//    /**
//     * Configures the specified table columns to display wallet transaction data and sets the data to the table view.
//     *
//     * @param TransactionIdCol The table column to display transaction IDs.
//     * @param StudentIdCol The table column to display student IDs.
//     * @param studentNameCol The table column to display student names.
//     * @param TypeCol The table column to display the type of transaction.
//     * @param BeforeCol The table column to display the wallet balance before the transaction.
//     * @param AfterCol The table column to display the wallet balance after the transaction.
//     * @param valueCol The table column to display transaction values.
//     * @param DateCol The table column to display transaction dates.
//     * @param userCol The table column to display usernames associated with the transactions.
//     * @param walletTransactionsTable The table view to display the wallet transaction data.
//     */
//    public static void getWalletTransactions(
//            TableColumn<WalletTransactions, String> TransactionIdCol,
//            TableColumn<WalletTransactions, String> StudentIdCol,
//            TableColumn<WalletTransactions, String> studentNameCol,
//            TableColumn<WalletTransactions, String> TypeCol,
//            TableColumn<WalletTransactions, String> BeforeCol,
//            TableColumn<WalletTransactions, String> AfterCol,
//            TableColumn<WalletTransactions, String> valueCol,
//            TableColumn<WalletTransactions, String> DateCol,
//            TableColumn<WalletTransactions, String> userCol,
//            TableView<WalletTransactions> walletTransactionsTable
//    ) {
//        TransactionIdCol.setCellValueFactory(new PropertyValueFactory<>("TransactionID"));
//        StudentIdCol.setCellValueFactory(new PropertyValueFactory<>("StudentID"));
//        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("StudentName"));
//        TypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
//        BeforeCol.setCellValueFactory(new PropertyValueFactory<>("Before"));
//        AfterCol.setCellValueFactory(new PropertyValueFactory<>("After"));
//        valueCol.setCellValueFactory(new PropertyValueFactory<>("Value"));
//        DateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
//        userCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
//        ObservableList<WalletTransactions> List = Wallet_Transactions.getWalletTransactions();
//        walletTransactionsTable.getItems().clear();
//        walletTransactionsTable.setItems(List);
//    }
//
//    /**
//     * Populates the given table columns and table view with wallet transaction data based on a specified value.
//     *
//     * @param TransactionIdCol Column to display transaction ID data.
//     * @param StudentIdCol Column to display student ID data.
//     * @param studentNameCol Column to display student name data.
//     * @param TypeCol Column to display transaction type data.
//     * @param BeforeCol Column to display wallet balance before the transaction.
//     * @param AfterCol Column to display wallet balance after the transaction.
//     * @param valueCol Column to display the transaction value.
//     * @param DateCol Column to display the transaction date.
//     * @param userCol Column to display the username associated with the transaction.
//     * @param walletTransactionsTable The table view to display the list of wallet transactions.
//     * @param value A search value used to filter wallet transactions.
//     */
//    public static void getWalletTransactions(
//            TableColumn<WalletTransactions, String> TransactionIdCol,
//            TableColumn<WalletTransactions, String> StudentIdCol,
//            TableColumn<WalletTransactions, String> studentNameCol,
//            TableColumn<WalletTransactions, String> TypeCol,
//            TableColumn<WalletTransactions, String> BeforeCol,
//            TableColumn<WalletTransactions, String> AfterCol,
//            TableColumn<WalletTransactions, String> valueCol,
//            TableColumn<WalletTransactions, String> DateCol,
//            TableColumn<WalletTransactions, String> userCol,
//            TableView<WalletTransactions> walletTransactionsTable, String value
//    ) {
//        TransactionIdCol.setCellValueFactory(new PropertyValueFactory<>("TransactionID"));
//        StudentIdCol.setCellValueFactory(new PropertyValueFactory<>("StudentID"));
//        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("StudentName"));
//        TypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
//        BeforeCol.setCellValueFactory(new PropertyValueFactory<>("Before"));
//        AfterCol.setCellValueFactory(new PropertyValueFactory<>("After"));
//        valueCol.setCellValueFactory(new PropertyValueFactory<>("Value"));
//        DateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
//        userCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
//        ObservableList<WalletTransactions> List = Wallet_Transactions.searchWalletTransactions(value);
//        walletTransactionsTable.getItems().clear();
//        walletTransactionsTable.setItems(List);
//    }
//
//    /**
//     * Populates the given table columns and table view with wallet transactions data filtered by a specific transaction ID.
//     * The method uses the provided table columns and a search value to fetch and display the appropriate data.
//     *
//     * @param TransactionIdCol the table column for displaying transaction IDs
//     * @param StudentIdCol the table column for displaying student IDs
//     * @param studentNameCol the table column for displaying student names
//     * @param TypeCol the table column for displaying transaction types
//     * @param BeforeCol the table column for displaying the balance before the transaction
//     * @param AfterCol the table column for displaying the balance after the transaction
//     * @param valueCol the table column for displaying the transaction value
//     * @param DateCol the table column for displaying the transaction date
//     * @param userCol the table column for displaying the username associated with the transaction
//     * @param walletTransactionsTable the table view to populate with wallet transactions
//     * @param value the transaction ID used to filter wallet transactions data
//     */
//    public static void getWalletTransactionsID(
//            TableColumn<WalletTransactions, String> TransactionIdCol,
//            TableColumn<WalletTransactions, String> StudentIdCol,
//            TableColumn<WalletTransactions, String> studentNameCol,
//            TableColumn<WalletTransactions, String> TypeCol,
//            TableColumn<WalletTransactions, String> BeforeCol,
//            TableColumn<WalletTransactions, String> AfterCol,
//            TableColumn<WalletTransactions, String> valueCol,
//            TableColumn<WalletTransactions, String> DateCol,
//            TableColumn<WalletTransactions, String> userCol,
//            TableView<WalletTransactions> walletTransactionsTable, String value
//    ) {
//        TransactionIdCol.setCellValueFactory(new PropertyValueFactory<>("TransactionID"));
//        StudentIdCol.setCellValueFactory(new PropertyValueFactory<>("StudentID"));
//        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("StudentName"));
//        TypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
//        BeforeCol.setCellValueFactory(new PropertyValueFactory<>("Before"));
//        AfterCol.setCellValueFactory(new PropertyValueFactory<>("After"));
//        valueCol.setCellValueFactory(new PropertyValueFactory<>("Value"));
//        DateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
//        userCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
//        ObservableList<WalletTransactions> List = Wallet_Transactions.searchWalletTransactionsID(value);
//        walletTransactionsTable.getItems().clear();
//        walletTransactionsTable.setItems(List);
//    }
//
//    /**
//     * Configures the columns of an income table and populates the table with data
//     * retrieved based on the provided education level.
//     *
//     * @param SubjectNameCol the table column for subject names
//     * @param GrpNameCol the table column for group names
//     * @param AppNameCol the table column for application names
//     * @param attendDateCol the table column for attendance dates
//     * @param AttendanceCol the table column for attendance information
//     * @param AbscenceCol the table column for absence information
//     * @param WalletCol the table column for wallet balances
//     * @param NegCountCol the table column for negative balance counts
//     * @param CashCol the table column for cash amounts
//     * @param FreeCol the table column for free entries
//     * @param ValueCol the table column for values or prices
//     * @param MustTotalCol the table column for the total required amount
//     * @param NegativeTotalCol the table column for the total negative amount
//     * @param PositiveTotalCol the table column for the total positive amount
//     * @param WalletIncomeCol the table column for wallet incomes
//     * @param WalletPaymentCol the table column for wallet payments
//     * @param IncomeTable the table view to display income information
//     * @param EduLevel the education level used to filter the retrieved income data
//     */
//    public static void getAllIncomeTable(
//            TableColumn<Income, String> SubjectNameCol,
//            TableColumn<Income, String> GrpNameCol,
//            TableColumn<Income, String> AppNameCol,
//            TableColumn<Income, String> attendDateCol,
//            TableColumn<Income, String> AttendanceCol,
//            TableColumn<Income, String> AbscenceCol,
//            TableColumn<Income, String> WalletCol,
//            TableColumn<Income, String> NegCountCol,
//            TableColumn<Income, String> CashCol,
//            TableColumn<Income, String> FreeCol,
//            TableColumn<Income, String> ValueCol,
//            TableColumn<Income, String> MustTotalCol,
//            TableColumn<Income, String> NegativeTotalCol,
//            TableColumn<Income, String> PositiveTotalCol,
//            TableColumn<Income, String> WalletIncomeCol,
//            TableColumn<Income, String> WalletPaymentCol,
//            TableView<Income> IncomeTable, String EduLevel
//    ) {
//        SubjectNameCol.setCellValueFactory(new PropertyValueFactory<>("SubjectName"));
//        GrpNameCol.setCellValueFactory(new PropertyValueFactory<>("GroupName"));
//        AppNameCol.setCellValueFactory(new PropertyValueFactory<>("AppName"));
//        attendDateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
//        AttendanceCol.setCellValueFactory(new PropertyValueFactory<>("Attendance"));
//        AbscenceCol.setCellValueFactory(new PropertyValueFactory<>("Abscense"));
//        WalletCol.setCellValueFactory(new PropertyValueFactory<>("Wallet"));
//        NegCountCol.setCellValueFactory(new PropertyValueFactory<>("NegCount"));
//        CashCol.setCellValueFactory(new PropertyValueFactory<>("Cash"));
//        FreeCol.setCellValueFactory(new PropertyValueFactory<>("Free"));
//        ValueCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
//        MustTotalCol.setCellValueFactory(new PropertyValueFactory<>("MustTotal"));
//        NegativeTotalCol.setCellValueFactory(new PropertyValueFactory<>("NegativeTotal"));
//        PositiveTotalCol.setCellValueFactory(new PropertyValueFactory<>("positiveCashTotal"));
//        WalletIncomeCol.setCellValueFactory(new PropertyValueFactory<>("WalletIncome"));
//        WalletPaymentCol.setCellValueFactory(new PropertyValueFactory<>("positiveWalletTotal"));
////        NegativeTotalCol.setCellValueFactory(new PropertyValueFactory<>("NegativeTotal"));
////        PositiveTotalCol.setCellValueFactory(new PropertyValueFactory<>("PositiveTotal"));
////        MustTotalCol.setCellValueFactory(new PropertyValueFactory<>("MustTotal"));
////        WalletIncomeCol.setCellValueFactory(new PropertyValueFactory<>("WalletIncome"));
////        WalletPaymentCol.setCellValueFactory(new PropertyValueFactory<>("WalletPayment"));
//        ObservableList<Income> List = Database.MoneyCalculations.getAllIncome(EduLevel);
//        IncomeTable.getItems().clear();
//        IncomeTable.setItems(List);
//    }
//
//    /**
//     * Configures the table columns and populates the {@code IncomeTable} with income data filtered by
//     * educational level and date.
//     *
//     * @param SubjectNameCol the table column for Subject Name
//     * @param GrpNameCol the table column for Group Name
//     * @param AppNameCol the table column for Application Name
//     * @param attendDateCol the table column for Attendance Date
//     * @param AttendanceCol the table column for Attendance count
//     * @param AbscenceCol the table column for Absence count
//     * @param WalletCol the table column for Wallet balance
//     * @param NegCountCol the table column for Negative count
//     * @param CashCol the table column for Cash transactions
//     * @param FreeCol the table column for Free transactions
//     * @param ValueCol the table column for Value or Price
//     * @param MustTotalCol the table column for Mandatory Total
//     * @param NegativeTotalCol the table column for Negative Total
//     * @param PositiveTotalCol the table column for Positive Total Cash transactions
//     * @param WalletIncomeCol the table column for Wallet Income
//     * @param WalletPaymentCol the table column for Wallet Payment Total
//     * @param IncomeTable the table where income data will be displayed
//     * @param EduLevel the educational level used as a filter for retrieving data
//     * @param Date the specific date used as a filter for retrieving income data
//     */
//    public static void getAllIncomeFromDateTable(
//            TableColumn<Income, String> SubjectNameCol,
//            TableColumn<Income, String> GrpNameCol,
//            TableColumn<Income, String> AppNameCol,
//            TableColumn<Income, String> attendDateCol,
//            TableColumn<Income, String> AttendanceCol,
//            TableColumn<Income, String> AbscenceCol,
//            TableColumn<Income, String> WalletCol,
//            TableColumn<Income, String> NegCountCol,
//            TableColumn<Income, String> CashCol,
//            TableColumn<Income, String> FreeCol,
//            TableColumn<Income, String> ValueCol,
//            TableColumn<Income, String> MustTotalCol,
//            TableColumn<Income, String> NegativeTotalCol,
//            TableColumn<Income, String> PositiveTotalCol,
//            TableColumn<Income, String> WalletIncomeCol,
//            TableColumn<Income, String> WalletPaymentCol,
//            TableView<Income> IncomeTable, String EduLevel, String Date
//    ) {
//        SubjectNameCol.setCellValueFactory(new PropertyValueFactory<>("SubjectName"));
//        GrpNameCol.setCellValueFactory(new PropertyValueFactory<>("GroupName"));
//        AppNameCol.setCellValueFactory(new PropertyValueFactory<>("AppName"));
//        attendDateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
//        AttendanceCol.setCellValueFactory(new PropertyValueFactory<>("Attendance"));
//        AbscenceCol.setCellValueFactory(new PropertyValueFactory<>("Abscense"));
//        WalletCol.setCellValueFactory(new PropertyValueFactory<>("Wallet"));
//        NegCountCol.setCellValueFactory(new PropertyValueFactory<>("NegCount"));
//        CashCol.setCellValueFactory(new PropertyValueFactory<>("Cash"));
//        FreeCol.setCellValueFactory(new PropertyValueFactory<>("Free"));
//        ValueCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
//        MustTotalCol.setCellValueFactory(new PropertyValueFactory<>("MustTotal"));
//        NegativeTotalCol.setCellValueFactory(new PropertyValueFactory<>("NegativeTotal"));
//        PositiveTotalCol.setCellValueFactory(new PropertyValueFactory<>("positiveCashTotal"));
//        WalletIncomeCol.setCellValueFactory(new PropertyValueFactory<>("WalletIncome"));
//        WalletPaymentCol.setCellValueFactory(new PropertyValueFactory<>("positiveWalletTotal"));
//        ObservableList<Income> List = getAllIncomeFromDate(EduLevel, Date);
//        IncomeTable.getItems().clear();
//        IncomeTable.setItems(List);
//    }
//
//    /**
//     * Populates a TableView with income data up to a specified date.
//     * Sets the cell value factories for columns in the TableView based on the properties of Income objects
//     * and populates the TableView with an observable list of Income objects retrieved using the given education level and date.
//     *
//     * @param SubjectNameCol          the table column for subject name.
//     * @param GrpNameCol              the table column for group name.
//     * @param AppNameCol              the table column for application name.
//     * @param attendDateCol           the table column for attendance date.
//     * @param AttendanceCol           the table column for attendance count.
//     * @param AbscenceCol             the table column for absence count.
//     * @param WalletCol               the table column for wallet balance.
//     * @param NegCountCol             the table column for negative count.
//     * @param CashCol                 the table column for cash total.
//     * @param FreeCol                 the table column for free income category.
//     * @param ValueCol                the table column for the total value amount.
//     * @param MustTotalCol            the table column for the total amount due ("must total").
//     * @param NegativeTotalCol        the table column for the total negative balance.
//     * @param PositiveTotalCol        the table column for the total positive balance from cash.
//     * @param WalletIncomeCol         the table column for the total income from wallets.
//     * @param WalletPaymentCol        the table column for the total payments via wallet.
//     * @param IncomeTable             the TableView to be populated with data.
//     * @param EduLevel                the education level used to filter the income data.
//     * @param Date                    the date up to which the income data is retrieved.
//     */
//    public static void getAllIncomeToDateTable(
//            TableColumn<Income, String> SubjectNameCol,
//            TableColumn<Income, String> GrpNameCol,
//            TableColumn<Income, String> AppNameCol,
//            TableColumn<Income, String> attendDateCol,
//            TableColumn<Income, String> AttendanceCol,
//            TableColumn<Income, String> AbscenceCol,
//            TableColumn<Income, String> WalletCol,
//            TableColumn<Income, String> NegCountCol,
//            TableColumn<Income, String> CashCol,
//            TableColumn<Income, String> FreeCol,
//            TableColumn<Income, String> ValueCol,
//            TableColumn<Income, String> MustTotalCol,
//            TableColumn<Income, String> NegativeTotalCol,
//            TableColumn<Income, String> PositiveTotalCol,
//            TableColumn<Income, String> WalletIncomeCol,
//            TableColumn<Income, String> WalletPaymentCol,
//            TableView<Income> IncomeTable, String EduLevel, String Date
//    ) {
//        SubjectNameCol.setCellValueFactory(new PropertyValueFactory<>("SubjectName"));
//        GrpNameCol.setCellValueFactory(new PropertyValueFactory<>("GroupName"));
//        AppNameCol.setCellValueFactory(new PropertyValueFactory<>("AppName"));
//        attendDateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
//        AttendanceCol.setCellValueFactory(new PropertyValueFactory<>("Attendance"));
//        AbscenceCol.setCellValueFactory(new PropertyValueFactory<>("Abscense"));
//        WalletCol.setCellValueFactory(new PropertyValueFactory<>("Wallet"));
//        NegCountCol.setCellValueFactory(new PropertyValueFactory<>("NegCount"));
//        CashCol.setCellValueFactory(new PropertyValueFactory<>("Cash"));
//        FreeCol.setCellValueFactory(new PropertyValueFactory<>("Free"));
//        ValueCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
//        MustTotalCol.setCellValueFactory(new PropertyValueFactory<>("MustTotal"));
//        NegativeTotalCol.setCellValueFactory(new PropertyValueFactory<>("NegativeTotal"));
//        PositiveTotalCol.setCellValueFactory(new PropertyValueFactory<>("positiveCashTotal"));
//        WalletIncomeCol.setCellValueFactory(new PropertyValueFactory<>("WalletIncome"));
//        WalletPaymentCol.setCellValueFactory(new PropertyValueFactory<>("positiveWalletTotal"));
//        ObservableList<Income> List = getAllIncomeToDate(EduLevel, Date);
//        IncomeTable.getItems().clear();
//        IncomeTable.setItems(List);
//    }
//
//    /**
//     * Populates a TableView with income data between specified dates based on the given educational level.
//     * Maps the specified columns to the corresponding fields in the Income model.
//     *
//     * @param SubjectNameCol the table column for displaying subject names
//     * @param GrpNameCol the table column for displaying group names
//     * @param AppNameCol the table column for displaying application names
//     * @param attendDateCol the table column for displaying attendance dates
//     * @param AttendanceCol the table column for displaying attendance counts
//     * @param AbscenceCol the table column for displaying absence counts
//     * @param WalletCol the table column for displaying wallet-related values
//     * @param NegCountCol the table column for displaying negative count values
//     * @param CashCol the table column for displaying cash payments
//     * @param FreeCol the table column for displaying free participation counts
//     * @param ValueCol the table column for displaying price values
//     * @param MustTotalCol the table column for displaying the mandatory total amount
//     * @param NegativeTotalCol the table column for displaying the negative total amount
//     * @param PositiveTotalCol the table column for displaying the positive cash total
//     * @param WalletIncomeCol the table column for displaying wallet income values
//     * @param WalletPaymentCol the table column for displaying wallet payment totals
//     * @param IncomeTable the TableView to be populated with income data
//     * @param EduLevel the educational level filter for fetching income data
//     * @param FromDate the start date for filtering income data
//     * @param ToDate the end date for filtering income data
//     */
//    public static void getAllIncomeBetweenDatesTable(
//            TableColumn<Income, String> SubjectNameCol,
//            TableColumn<Income, String> GrpNameCol,
//            TableColumn<Income, String> AppNameCol,
//            TableColumn<Income, String> attendDateCol,
//            TableColumn<Income, String> AttendanceCol,
//            TableColumn<Income, String> AbscenceCol,
//            TableColumn<Income, String> WalletCol,
//            TableColumn<Income, String> NegCountCol,
//            TableColumn<Income, String> CashCol,
//            TableColumn<Income, String> FreeCol,
//            TableColumn<Income, String> ValueCol,
//            TableColumn<Income, String> MustTotalCol,
//            TableColumn<Income, String> NegativeTotalCol,
//            TableColumn<Income, String> PositiveTotalCol,
//            TableColumn<Income, String> WalletIncomeCol,
//            TableColumn<Income, String> WalletPaymentCol,
//            TableView<Income> IncomeTable, String EduLevel, String FromDate,
//            String ToDate
//    ) {
//        SubjectNameCol.setCellValueFactory(new PropertyValueFactory<>("SubjectName"));
//        GrpNameCol.setCellValueFactory(new PropertyValueFactory<>("GroupName"));
//        AppNameCol.setCellValueFactory(new PropertyValueFactory<>("AppName"));
//        attendDateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
//        AttendanceCol.setCellValueFactory(new PropertyValueFactory<>("Attendance"));
//        AbscenceCol.setCellValueFactory(new PropertyValueFactory<>("Abscense"));
//        WalletCol.setCellValueFactory(new PropertyValueFactory<>("Wallet"));
//        NegCountCol.setCellValueFactory(new PropertyValueFactory<>("NegCount"));
//        CashCol.setCellValueFactory(new PropertyValueFactory<>("Cash"));
//        FreeCol.setCellValueFactory(new PropertyValueFactory<>("Free"));
//        ValueCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
//        MustTotalCol.setCellValueFactory(new PropertyValueFactory<>("MustTotal"));
//        NegativeTotalCol.setCellValueFactory(new PropertyValueFactory<>("NegativeTotal"));
//        PositiveTotalCol.setCellValueFactory(new PropertyValueFactory<>("positiveCashTotal"));
//        WalletIncomeCol.setCellValueFactory(new PropertyValueFactory<>("WalletIncome"));
//        WalletPaymentCol.setCellValueFactory(new PropertyValueFactory<>("positiveWalletTotal"));
//        ObservableList<Income> List = getAllIncomeBetweenDates(EduLevel, FromDate, ToDate);
//        IncomeTable.getItems().clear();
//        IncomeTable.setItems(List);
//    }
//
//
//    /**
//     * Configures a table column to display division names and populates a table view with a list of student divisions.
//     *
//     * @param divisionCol The table column of type TableColumn<StudentDivision, String> to be set up for displaying division names.
//     * @param UsersTable The table view of type TableView<StudentDivision> to be populated with student division data.
//     *
//     * The method performs the following steps:
//     * 1. Configures the provided table column to use the "DivisionName" property from the StudentDivision class.
//     * 2. Fetches a list of all student divisions from the database through a helper method.
//     * 3. Clears any existing items in the provided table view to ensure it is refreshed.
//     * 4. Populates the table view with the fetched list of student divisions.
//     */
//    public static void getAllDivisions(
//            TableColumn<StudentDivision, String> divisionCol,
//            TableView<StudentDivision> UsersTable
//    ) {
//        divisionCol.setCellValueFactory(new PropertyValueFactory<>("DivisionName"));
//
//        ObservableList<StudentDivision> UsersList = Database.TableQuery.getAllStudentDivisions();
//        UsersTable.getItems().clear();
//        UsersTable.setItems(UsersList);
//    }
//
//    public static void getAllHalls(
//            TableColumn<Halls, String> hallNameCol,
//            TableView<Halls> hallsTable
//    ) {
//        hallNameCol.setCellValueFactory(new PropertyValueFactory<>("hallName"));
//
//        ObservableList<Halls> hallsList = Database.TableQuery.getAllHalls();
//        hallsTable.getItems().clear();
//        hallsTable.setItems(hallsList);
//    }
//
//}
