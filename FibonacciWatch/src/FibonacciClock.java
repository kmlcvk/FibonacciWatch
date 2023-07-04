import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FibonacciClock {

    public static void main(String[] args) {

        System.out.println(prepareColorLists(getLocalHour(),getLocalMinute()));


    }
    //Ermitteln der Fibonacci-Zahl aus der aktuallen Zeit
    public static ArrayList<Integer> arrangeTimeToFibonacciList ( int time) {

        ArrayList<Integer> timeParts = new ArrayList<>();
        int  fibonacciNumbers[] = {5, 3, 2, 1, 1};

        for (int i= 0; i < fibonacciNumbers.length; i++) {
            if(time - fibonacciNumbers[i] < 0) {
                continue;
            } else if (time - fibonacciNumbers[i] == 0) {
                timeParts.add(fibonacciNumbers[i]);
                break;
            } else if (time - fibonacciNumbers[i] > 0) {
                timeParts.add(fibonacciNumbers[i]);
                time -= fibonacciNumbers[i];
                continue;
            }
        }
        return timeParts;
    }

    // Finden der Farbe Blau-Zahl
    public static List<Integer> createSharedListForHourAndMinute_whichIsTheListForBlauColor(int hour, int minute){
        ArrayList<Integer> sharedList = new ArrayList<>();

        List<Integer> hourParts = arrangeTimeToFibonacciList(hour);
        List<Integer> minuteParts = arrangeTimeToFibonacciList(minute);

        if (hourParts.size() > 0 ) {
            for (int i = 0; i < hourParts.size(); i++) {
                if (minuteParts.contains(hourParts.get(i)))
                    sharedList.add(hourParts.get(i));
            }
        }
        return sharedList;
    }

    public static List<Integer> createHourList_onlyRedColorList(int hour, int minute) {
        List<Integer> hourList =arrangeTimeToFibonacciList(hour);
        List<Integer> sharedList = createSharedListForHourAndMinute_whichIsTheListForBlauColor(hour,minute);

        ArrayList<Integer> hourListWithoutShared = new ArrayList<>();

        if (hourList.size() > 0) {
            for (int i = 0; i < hourList.size(); i++) {
                if (sharedList.contains(hourList.get(i)))
                    continue;
                else hourListWithoutShared.add(hourList.get(i));
            }
        }
        return hourListWithoutShared;
    }

    public static List<Integer> createMinuteList_onlyGreenColorList (int hour, int minute){

        List<Integer> minuteList = arrangeTimeToFibonacciList(minute);
        List<Integer> sharedList = createSharedListForHourAndMinute_whichIsTheListForBlauColor(hour, minute);

        ArrayList<Integer> minuteListWithoutShared = new ArrayList<>();

        if ((minuteList.size() > 0)) {
            for (int i = 0; i < minuteList.size(); i++) {
                if (sharedList.contains(minuteList.get(i)))
                    continue;
                else minuteListWithoutShared.add(minuteList.get(i));
            }
        }
        return minuteListWithoutShared;

    }

    public static List<Integer> createWhiteColorList (int hour, int minute){
        List<Integer> hourListOnly = createHourList_onlyRedColorList(hour, minute);
        List<Integer> minuteListOnly = createMinuteList_onlyGreenColorList(hour, minute);
        List<Integer> sharedList = createSharedListForHourAndMinute_whichIsTheListForBlauColor(hour, minute);

        int fibonacciList [] = {1, 1, 2, 3, 5};
        ArrayList<Integer> whiteColorList = new ArrayList<>();

        for (int i = 0; i < fibonacciList.length; i++) {
            if(!(hourListOnly.contains(fibonacciList[i])
               ||minuteListOnly.contains(fibonacciList[i])
               ||sharedList.contains(fibonacciList[i]))) {
                whiteColorList.add(fibonacciList[i]);
            }
        }
        if(hourListOnly.size() + minuteListOnly.size() + sharedList.size() + whiteColorList.size() == 4)
            whiteColorList.add(1);
        return whiteColorList;
    }

    public static List<Map <String, List <Integer>>> prepareColorLists(int hour, int minute) {
        List<Map<String, List<Integer>>> resultList = new ArrayList<>();

        HashMap<String,List<Integer>> blue = new HashMap<>();
        blue.put("BLUE", createSharedListForHourAndMinute_whichIsTheListForBlauColor(hour, minute));

        HashMap<String,List<Integer>> red = new HashMap<>();
        red.put("RED", createHourList_onlyRedColorList(hour, minute));

        HashMap<String,List<Integer>> green = new HashMap<>();
        green.put("GREEN", createMinuteList_onlyGreenColorList(hour, minute));

        HashMap<String,List<Integer>> white = new HashMap<>();
        white.put("WHITE", createWhiteColorList(hour, minute));

        if(red.get("RED").size() != 0)             resultList.add(red);
        if (green.get("GREEN").size() != 0)        resultList.add(green);
        if (blue.get("BLUE").size() != 0)          resultList.add(blue);
        if (white.get("WHITE").size() != 0)        resultList.add(white);
        return resultList;

    }

    //aktualle Stunde aufgerufen
    public static int getLocalHour(){
        int localHour = LocalTime.now().getHour();
        if (localHour > 12 ) return localHour -12;
        return localHour;
    }

    //aktualle Minute aufgerufen
    public static int getLocalMinute () {
        int localMinute =LocalTime.now().getMinute()/5;
        return localMinute;
    }
}