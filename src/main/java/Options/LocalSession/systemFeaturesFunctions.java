package Options.LocalSession;

public class systemFeaturesFunctions {
    public static String[] getEduLevels(Object eduLevel) {
        return eduLevel.equals("detailed") ? new String[]{"إختر الصف", "صفوف النقل", // Initial options for the detailed level
                "الاول الابتدائي", "الثاني الابتدائي", "الثالث الابتدائي", // Primary education levels
                "الرابع الابتدائي", "الخامس الابتدائي", "السادس الابتدائي", // Remaining primary levels
                "الاول الاعدادي", "الثاني الاعدادي", "الثالث الاعدادي", // Preparatory levels
                "الاول الثانوي", "الثاني الثانوي", "الثالث الثانوي" // Secondary levels
        } : new String[]{"إختر الصف", "صفوف النقل", "الثالث الثانوي"}; // Simple level options if not detailed
    }
    
//    public static void syncSystemFeaturesValues() {
//        systemFeatures.getAllFeatureDetails().stream()
//                .filter(feature -> "اسم المدرس - اسماء صفوف النقل".equals(feature.get("FeatureName")))
//                .findFirst()
//                .ifPresent(feature -> DataStorage.setValue("EduLevel",
//                        "true".equals(feature.get("Value")) ? "detailed" : "notDetailed"));
//
//        systemFeatures.getAllFeatureDetails().stream()
//                .filter(feature -> "عدم تعارض مواعيد القاعات".equals(feature.get("FeatureName")))
//                .findFirst()
//                .ifPresent(feature -> DataStorage.setValue("hallsConflicts",
//                        "true".equals(feature.get("Value")) ? "yes" : "no"));
//
//        systemFeatures.getAllFeatureDetails().stream()
//                .filter(feature -> "القاعات".equals(feature.get("FeatureName")))
//                .findFirst()
//                .ifPresent(feature -> DataStorage.setValue("hallsFeatures",
//                        "true".equals(feature.get("Value"))));
//
//        systemFeatures.getAllFeatureDetails().stream()
//                .filter(feature -> "الخصم عند الغياب".equals(feature.get("FeatureName")))
//                .findFirst()
//                .ifPresent(feature -> DataStorage.setValue("SessionDeduction",
//                        "true".equals(feature.get("Value"))));
//
//        systemFeatures.getAllFeatureDetails().stream()
//                .filter(feature -> "إيداع المحفظة".equals(feature.get("FeatureName")))
//                .findFirst()
//                .ifPresent(feature -> DataStorage.setValue("WAPP_WalletDeposit",
//                        "true".equals(feature.get("Value"))));
//
//        systemFeatures.getAllFeatureDetails().stream()
//                .filter(feature -> "غياب الطالب".equals(feature.get("FeatureName")))
//                .findFirst()
//                .ifPresent(feature -> DataStorage.setValue("WAPP_StudentAbsence",
//                        "true".equals(feature.get("Value"))));
//
//        systemFeatures.getAllFeatureDetails().stream()
//                .filter(feature -> "حضور الطالب".equals(feature.get("FeatureName")))
//                .findFirst()
//                .ifPresent(feature -> DataStorage.setValue("WAPP_StudentAttendance",
//                        "true".equals(feature.get("Value"))));
//
//        systemFeatures.getAllFeatureDetails().stream()
//                .filter(feature -> "طباعة استبيان معفي".equals(feature.get("FeatureName")))
//                .findFirst()
//                .ifPresent(feature -> DataStorage.setValue("printFreeForm",
//                        "true".equals(feature.get("Value"))));
//    }
}
