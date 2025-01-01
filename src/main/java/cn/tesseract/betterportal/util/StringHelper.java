package cn.tesseract.betterportal.util;

import java.util.List;

public final class StringHelper {
   public static final String newline = new String(System.lineSeparator().getBytes());

   public static String capitalize(String s) {
      return s.substring(0, 1).toUpperCase() + s.substring(1);
   }

   public static String capitalizeEachWord(String s) {
      char[] chars = s.toCharArray();

      for(int i = 0; i < chars.length; ++i) {
         if (i == 0 || chars[i - 1] == ' ') {
            chars[i] = ("" + chars[i]).toUpperCase().charAt(0);
         }
      }

      return new String(chars);
   }

   public static String getFirstWord(String s) {
      int i = s.trim().indexOf(32);
      return i < 0 ? s : s.substring(0, i);
   }

   public static String formatFloat(float f, int min_num_decimal_places, int max_num_decimal_places) {
      if (min_num_decimal_places >= 0 && max_num_decimal_places >= 0) {
         if (min_num_decimal_places > max_num_decimal_places) {
            System.out.println("formatFloat: min_num_decimal_places > max_num_decimal_places");
            return Float.toString(f);
         } else {
            String s = String.format("%." + max_num_decimal_places + "f", f);

            for(int i = 0; i < max_num_decimal_places - min_num_decimal_places && s.endsWith("0"); ++i) {
               s = s.substring(0, s.length() - 1);
            }

            return s.endsWith(".") ? s.substring(0, s.length() - 1) : s;
         }
      } else {
         System.out.println("formatFloat: min_num_decimal_places or max_num_decimal_places is less than 0");
         return Float.toString(f);
      }
   }

   public static String formatFloat(float f) {
      return formatFloat(f, 0, 2);
   }

   public static String formatDouble(double d) {
      return formatDouble(d, 0, 2);
   }

   public static String formatDouble(double d, int min_num_decimal_places, int max_num_decimal_places) {
      return formatFloat((float)d, min_num_decimal_places, max_num_decimal_places);
   }

   public static String getCommaSeparatedList(String[] strings) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < strings.length; ++i) {
         sb.append(strings[i]);
         if (i < strings.length - 1) {
            sb.append(", ");
         }
      }

      return sb.toString();
   }

   public static String getCommaSeparatedList(Object[] objects) {
      String[] strings = new String[objects.length];

      for(int i = 0; i < objects.length; ++i) {
         strings[i] = objects[i].toString();
      }

      return getCommaSeparatedList(strings);
   }

   private String getCommaSeparatedList(List list) {
      String[] strings = new String[list.size()];

      for(int i = 0; i < list.size(); ++i) {
         strings[i] = list.get(i).toString();
      }

      return getCommaSeparatedList(strings);
   }

   public static String mirrorString(String s) {
      char[] chars = s.toCharArray();

      for(int i = 0; i < chars.length; ++i) {
         int c = chars[i];
         if (c >= 65 && c <= 90) {
            c = 90 - (c - 65);
         } else if (c >= 97 && c <= 122) {
            c = 122 - (c - 97);
         } else if (c >= 48 && c <= 57) {
            c = 57 - (c - 48);
         }

         chars[i] = (char)c;
      }

      return new String(chars);
   }

   public static String repeat(String text, int n) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < n; ++i) {
         sb.append(text);
      }

      return sb.toString();
   }

   public static boolean startsWithVowel(String s) {
      if (s != null && !s.isEmpty()) {
         char c = s.toLowerCase().charAt(0);
         return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
      } else {
         return false;
      }
   }

   public static boolean startsWithDigit(String s) {
      if (s != null && !s.isEmpty()) {
         char c = s.charAt(0);
         return c >= '0' && c <= '9';
      } else {
         return false;
      }
   }

   public static String stripLeading(String leading, String text) {
      return leading != null && text != null && text.startsWith(leading) ? text.substring(leading.length()) : text;
   }

   public static String stripTrailing(String trailing, String text) {
      return trailing != null && text != null && text.endsWith(trailing) ? text.substring(0, text.length() - trailing.length()) : text;
   }

   public static String left(String s, int num_chars) {
      if (num_chars < 0) {
         num_chars += s.length();
      }

      if (num_chars < 0) {
         num_chars = 0;
      }

      return s.substring(0, num_chars);
   }

   public static String aOrAn(String next_word) {
      return (startsWithVowel(next_word) ? "an " : "a ") + next_word;
   }

   public static String yesOrNo(boolean b) {
      return b ? "yes" : "no";
   }

   public static String getCoordsAsString(int x, int y, int z) {
      return x + "," + y + "," + z;
   }

   public static String getCoordsAsString(int[] coords) {
      return coords[0] + "," + coords[1] + "," + coords[2];
   }

   public static String getPosAsString(double pos_x, double pos_y, double pos_z, int decimal_places) {
      return formatDouble(pos_x, 0, decimal_places) + "," + formatDouble(pos_y, 0, decimal_places) + "," + formatDouble(pos_z, 0, decimal_places);
   }

   public static String getOnOrOff(boolean on) {
      return on ? "ON" : "OFF";
   }

   public static void addToStringArray(String s, String[] array) {
      for(int i = 0; i < array.length; ++i) {
         if (array[i] == null) {
            array[i] = s;
            return;
         }
      }

      System.out.println("addToStringArray: No room left in array");
   }

   public static int getNumNonNullStrings(String[] array) {
      int num = 0;

      for(int i = 0; i < array.length; ++i) {
         if (array[i] != null) {
            ++num;
         }
      }

      return num;
   }

   public static String[] explode(String s, String delimiter) {
      return s == null ? null : s.split(delimiter);
   }

   public static String implode(String[] string_array, String delimiter, boolean include_empties, boolean include_nulls) {
      StringBuilder sb = new StringBuilder();
      boolean include_delimiter = false;
      if ((include_nulls || string_array[0] != null) && (include_empties || !string_array[0].isEmpty())) {
         sb.append(string_array[0]);
         include_delimiter = true;
      }

      for(int i = 1; i < string_array.length; ++i) {
         if ((include_nulls || string_array[i] != null) && (include_empties || !string_array[i].isEmpty())) {
            if (include_delimiter) {
               sb.append(delimiter);
            } else {
               include_delimiter = true;
            }

            sb.append(string_array[i]);
         }
      }

      return sb.toString();
   }

   public static String implode(String[] string_array, String delimiter) {
      return implode(string_array, delimiter, true, true);
   }

   public static String getRomanNumeral(int n) {
      if (n == 1) {
         return "I";
      } else if (n == 2) {
         return "II";
      } else if (n == 3) {
         return "III";
      } else if (n == 4) {
         return "IV";
      } else if (n == 5) {
         return "V";
      } else {
         System.out.println("getRomanNumeral: unhandled number " + n);
         return "?";
      }
   }

   public static String getBooleanAsLetter(boolean b) {
      return b ? "T" : "F";
   }

   public static String convertUnderscoresToCamelCase(String s) {
      if (s == null) {
         return null;
      } else if (!s.contains("_")) {
         return s;
      } else {
         String[] arr = explode(s, "_");
         StringBuilder sb = new StringBuilder(arr[0]);

         for(int i = 1; i < arr.length; ++i) {
            sb.append(capitalize(arr[i]));
         }

         return sb.toString();
      }
   }
}
