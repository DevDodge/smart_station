package Options;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

public class Encode_Decode {

    static int thirdval = 2020;
    static final String third = Integer.toString(thirdval);
    static int fifthval = 2021;
    static final String fifth = String.valueOf(fifthval);
    static int thirdval2 = 2002;
    static final String third2 = Integer.toString(thirdval2);
    static int fifthval2 = 2441;
    static final String fifth2 = String.valueOf(fifthval2);
    static int Seventhval = 412478;
    static final String seventh = Integer.toString(Seventhval);
    static long firstVal = 198547216547L;
    static final String first = String.valueOf(firstVal);
    static Date nowDate = new Date();
    static SimpleDateFormat YearFormat = new SimpleDateFormat("yyyy");
    static SimpleDateFormat MonthFormat = new SimpleDateFormat("MM");
    static SimpleDateFormat DayFormat = new SimpleDateFormat("dd");
    private static final int Year = (Integer.parseInt(YearFormat.format(nowDate)));
    private static final int Month = (Integer.parseInt(MonthFormat.format(nowDate)));
    private static final int Day = (Integer.parseInt(DayFormat.format(nowDate)));

    public static void main(String[] args) {//7541555478532145873214453
        EncodewithCipher("hello");
        DecodewithCipher("c1b5e32216913977f32543e44bd75cbf");
    }

    public static String EncodewithCipher(String Data) {
        byte[] key = "7541555478532145873214453".getBytes();
        byte[] data = Data.getBytes();
        Key deskey = null;
        DESedeKeySpec spec;
        try {
            spec = new DESedeKeySpec(key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
            byte[] CipherText = cipher.doFinal(data);
            StringBuffer hexCiphertext = new StringBuffer();
            for (int i = 0; i < CipherText.length; i++)
                hexCiphertext.append(Integer.toString((CipherText[i] & 0xff) + 0x100, 16).substring(1));
            System.out.println("CipherText is --> (" + hexCiphertext + ")");
            cipher.init(Cipher.DECRYPT_MODE, deskey);
            byte[] plaintext = cipher.doFinal(CipherText);
            return hexCiphertext.toString();
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException |
                 IllegalBlockSizeException | BadPaddingException ex) {
            java.util.logging.Logger.getLogger(Encode_Decode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static String DecodewithCipher(String Data) {
        byte[] key = "7541555478532145873214453".getBytes();
        byte[] data = Data.getBytes();
        Key deskey = null;
        DESedeKeySpec spec;
        try {
            spec = new DESedeKeySpec(key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
            byte[] CipherText = cipher.doFinal(data);
            StringBuffer hexCiphertext = new StringBuffer();
            for (int i = 0; i < CipherText.length; i++)
                hexCiphertext.append(Integer.toString((CipherText[i] & 0xff) + 0x100, 16).substring(1));
            cipher.init(Cipher.DECRYPT_MODE, deskey);
            byte[] plaintext = cipher.doFinal(data);
            System.out.println("Plaint text is ===>" + new String(plaintext));
            return plaintext.toString();
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException |
                 IllegalBlockSizeException | BadPaddingException ex) {
            java.util.logging.Logger.getLogger(Encode_Decode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    /**
     * This Function Encoded the Date
     *
     * @param Year  will added to unknown Value
     * @param Month will added to unknown Value
     * @return the encoded date contains 43 digit
     */
    public static String Encode(int Year, int Month, int Day) {
        int EncodedYear = Year + 38998;
        int EncodedMonth = Month + 2701992;
        int EncodedDay = Day + 184799;

        String[] Encode = {String.valueOf(firstVal),
                Integer.toString(EncodedYear), Integer.toString(thirdval),
                Integer.toString(EncodedMonth), Integer.toString(fifthval),
                Integer.toString(EncodedDay), Integer.toString(Seventhval)};
        String EncodedDate = Encode[0] + Encode[1] + Encode[2] + Encode[3]
                + Encode[4] + Encode[5] + Encode[6];
        return EncodedDate;
    }

    public static String EncodeTodayDate() {
        int EncodedYear = Year + 26537;
        int EncodedMonth = Month + 1453999;
        int EncodedDay = Day + 576299;

        String[] Encode = {String.valueOf(firstVal),
                Integer.toString(EncodedYear), Integer.toString(thirdval2),
                Integer.toString(EncodedMonth), Integer.toString(fifthval2),
                Integer.toString(EncodedDay), Integer.toString(Seventhval)};
        String EncodedDate = Encode[0] + Encode[1] + Encode[2] + Encode[3]
                + Encode[4] + Encode[5] + Encode[6];
        return EncodedDate;
    }

    public static String[] DecodeTodayDate(String EncodedDate) {
        char[] ch = EncodedDate.toCharArray();
        String Val_1 = ch[0] + String.valueOf(ch[1]) + ch[2] + ch[3] + ch[4] + ch[5] + ch[6] + ch[7] + ch[8] + ch[9]
                + ch[10] + ch[11];
        String Val_2 = String.valueOf(ch[12]) + ch[13] + ch[14] + ch[15] + ch[16];
        String Val_3 = String.valueOf(ch[17]) + ch[18] + ch[19] + ch[20];
        String Val_4 = String.valueOf(ch[21]) + ch[22] + ch[23] + ch[24] + ch[25] + ch[26] + ch[27];
        String Val_5 = String.valueOf(ch[28]) + ch[29] + ch[30] + ch[31];
        String Val_6 = String.valueOf(ch[32]) + ch[33] + ch[34] + ch[35] + ch[36] + ch[37];
        String Val_7 = String.valueOf(ch[38]) + ch[39] + ch[40] + ch[41] + ch[42] + ch[43];
        if (Val_1.equals(first) && Val_3.equals(third2) && Val_5.equals(fifth2) && Val_7.equals(seventh)) {
            int Year = Integer.parseInt(Val_2) - 26537;
            int Months = Integer.parseInt(Val_4) - 1453999;
            int Days = Integer.parseInt(Val_6) - 576299;
            String[] DecodedDate = {Integer.toString(Year), Integer.toString(Months), Integer.toString(Days)};
            return DecodedDate;
        } else {
            int error = 0;
            String[] DecodedDate = {Integer.toString(error)};
            return DecodedDate;
        }

    }

    /**
     * This will take the Encoded 43 Digits And Decode it
     *
     * @param EncodedDate
     * @return String Array index 0 : Years // index 1 : Months // index 2 :
     * Days
     */
    public static String[] DecodeFUN(String EncodedDate) throws IOException {

        try {
            String str = EncodedDate;
            char[] ch = str.toCharArray();
            String Val_1 = String.valueOf(ch[0]) + ch[1] + ch[2] + ch[3] + ch[4] + ch[5] + ch[6] + ch[7] + ch[8] + ch[9] + ch[10] + ch[11];
            String Val_2 = String.valueOf(ch[12]) + ch[13] + ch[14] + ch[15] + ch[16];
            String Val_3 = String.valueOf(ch[17]) + ch[18] + ch[19] + ch[20];
            String Val_4 = String.valueOf(ch[21]) + ch[22] + ch[23] + ch[24] + ch[25] + ch[26] + ch[27];
            String Val_5 = String.valueOf(ch[28]) + ch[29] + ch[30] + ch[31];
            String Val_6 = String.valueOf(ch[32]) + ch[33] + ch[34] + ch[35] + ch[36] + ch[37];
            String Val_7 = String.valueOf(ch[38]) + ch[39] + ch[40] + ch[41] + ch[42] + ch[43];
            if (Val_1.equals(first) && Val_3.equals(third) && Val_5.equals(fifth) && Val_7.equals(seventh)) {
                int Year = Integer.parseInt(Val_2) - 38998;
                int Months = Integer.parseInt(Val_4) - 2701992;
                int Days = Integer.parseInt(Val_6) - 184799;
                String[] DecodedDate = {Integer.toString(Year), Integer.toString(Months), Integer.toString(Days)};
                return DecodedDate;
            } else {
                int error = 0;
                String[] DecodedDate = {Integer.toString(error)};
                return DecodedDate;
            }
        } catch (Exception ex) {
            Scene.OpenScene("src/SubForms/CrashedProgram.fxml");
        }
        return null;
    }

    public static String EncodeHoleNumbers(int Num) {
        switch (Num) {
            case 0:
                return "e1@oA$y*z?0;§^";
            case 1:
                return "♥☻r6¼δS8u☻►^◙";
            case 101:
                return "♠╜º#7ü^0╞é¬╡Wn=♀";
            default:
                return null;
        }
    }

    public static int DecodeHoleNumbers(String text) {
        switch (text) {
            case "e1@oA$y*z?0;§^":
                return 0;
            case "♥☻r6¼δS8u☻►^◙":
                return 1;
            case "♠╜º#7ü^0╞é¬╡Wn=♀":
                return 101;
            default:
                return 5000;
        }
    }

}
