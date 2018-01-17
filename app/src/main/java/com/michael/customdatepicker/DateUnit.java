package com.michael.customdatepicker;

public enum DateUnit {

        Year("year"),Month("month"),Day("day"),
        Hour("hour"),Minute("minute"),Second("second");
        String value;

        DateUnit(String value) {
            this.value = value;
        }
        public static DateUnit match(String unit) {
            for(DateUnit u: DateUnit.values()){
                if(u.value.equals(unit)){
                    return u;
                }
            }
            return Day;
        }

    public static DateUnit matchAttrValue(Integer attr) {
        DateUnit mode;
        switch (attr){
            case 0:
                mode = Year;
                break;
            case 1:
                mode = Month;
                break;
            case 2:
                mode = Day;
                break;
            case 3:
                mode = Hour;
                break;
            case 4:
                mode = Day;
                break;
            case 5:
                mode = Second;
                break;
            default:
                mode = Day;
        }
        return mode;
    }
}