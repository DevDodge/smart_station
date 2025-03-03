/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Options;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author hp
 */
public class HardwareData {

    /**
     * this function takes hardware data and check if true
     *
     * @param Processor_ID
     * @param getBIOS_serialNumber
     * @param getBASEBOARD_serialNumber
     * @return
     */
    public static boolean checkHardware(String Processor_ID,
                                        String getBIOS_serialNumber, String getBASEBOARD_serialNumber) {

        String s;
        String proc1 = "";
        String baseboerd1 = "";
        String bios1 = "";
        String proc ;
        String baseboerd;
        String bios;

        String[][] getProcessorID = new String[][]{{"CMD", "/C", "WMIC CPU GET ProcessorId"}};
        String[][] getBIOSserialNumber = new String[][]{{"CMD", "/C", "WMIC bios get serialnumber"}};
        String[][] getBASEBOARDserialNumber = new String[][]{{"CMD", "/C", "WMIC BASEBOARD GET SerialNumber"}};

        try {
            for (int i = 0; i < getProcessorID.length; i++) {
                String[] processor = getProcessorID[i];
                Process process = Runtime.getRuntime().exec(processor);
                process.getOutputStream().close();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while ((s = reader.readLine()) != null) {
                    proc1 += s;
                }
            }

            for (int i = 0; i < getBIOSserialNumber.length; i++) {
                String[] processor = getBIOSserialNumber[i];
                Process process = Runtime.getRuntime().exec(processor);
                process.getOutputStream().close();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while ((s = reader.readLine()) != null) {
                    bios1 += s;
                }
            }

            for (int i = 0; i < getBASEBOARDserialNumber.length; i++) {
                String[] processor = getBASEBOARDserialNumber[i];
                Process process = Runtime.getRuntime().exec(processor);
                process.getOutputStream().close();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while ((s = reader.readLine()) != null) {
                    baseboerd1 += s;
                }
            }
            baseboerd = baseboerd1.replaceAll("SerialNumber",  "").trim();
            bios = bios1.replaceAll("SerialNumber",  "").trim();
            proc = proc1.replaceAll("ProcessorId",  "").trim();
            if (baseboerd.equals(getBASEBOARD_serialNumber)
                    && bios.equals(getBIOS_serialNumber)
                    && proc.equals(Processor_ID)) {
                return true;
            } else {
                return false;
            }

        } catch (IOException e) {
            
            e.printStackTrace();
            return false;
        }

    }
    public static String[] getHardwareData(){
        String s;
        String proc1 = "";
        String baseboerd1 = "";
        String bios1 = "";
        String proc ;
        String baseboerd;
        String bios;

        String[][] getProcessorID = new String[][]{{"CMD", "/C", "WMIC CPU GET ProcessorId"}};
        String[][] getBIOSserialNumber = new String[][]{{"CMD", "/C", "WMIC bios get serialnumber"}};
        String[][] getBASEBOARDserialNumber = new String[][]{{"CMD", "/C", "WMIC BASEBOARD GET SerialNumber"}};

        try {
            for (int i = 0; i < getProcessorID.length; i++) {
                String[] processor = getProcessorID[i];
                Process process = Runtime.getRuntime().exec(processor);
                process.getOutputStream().close();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while ((s = reader.readLine()) != null) {
                    proc1 += s;
                }
            }

            for (int i = 0; i < getBIOSserialNumber.length; i++) {
                String[] processor = getBIOSserialNumber[i];
                Process process = Runtime.getRuntime().exec(processor);
                process.getOutputStream().close();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while ((s = reader.readLine()) != null) {
                    bios1 += s;
                }
            }

            for (int i = 0; i < getBASEBOARDserialNumber.length; i++) {
                String[] processor = getBASEBOARDserialNumber[i];
                Process process = Runtime.getRuntime().exec(processor);
                process.getOutputStream().close();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while ((s = reader.readLine()) != null) {
                    baseboerd1 += s;
                }
            }
            baseboerd = baseboerd1.replaceAll("SerialNumber",  "").trim();
            bios = bios1.replaceAll("SerialNumber",  "").trim();
            proc = proc1.replaceAll("ProcessorId",  "").trim();
            String Finallbaseboerd = baseboerd.replaceAll("[^a-zA-z0-9]", "");
            String Finallbios = bios.replaceAll("[^a-zA-z0-9]", "");
            String Finallproc = proc.replaceAll("[^a-zA-z0-9]", "");
            String[] hardwareData = {Finallproc,Finallbaseboerd,Finallbios};
            return hardwareData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean addDeviceData(){
        String HDdata[] = getHardwareData();
        Database.MainConnection.addDeviceHardwareData(HDdata[0], HDdata[1], HDdata[2]);
        return true;
    }
}
