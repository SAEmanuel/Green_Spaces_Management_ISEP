package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Represents a date through the day, month, and year.
 *
 * @author Make IT Simple
 */
public class Data implements Comparable<Data>, Serializable {

    /**
     * The year of the date.
     */
    private int year;

    /**
     * The month of the date.
     */
    private Month month;

    /**
     * The day of the date.
     */
    private int day;

    /**
     * The default year.
     */
    private static final int DEFAULT_YEAR = 1;

    /**
     * The default month.
     */
    private static final Month DEFAULT_MONTH = Month.JANUARY;

    /**
     * The default day.
     */
    private static final int DEFAULT_DAY = 1;


    private static final int MIN_ALLOWED_YEAR = 1400;
    private static final int MIN_ALLOWED_MONTH = 1;
    private static final int MAX_ALLOWED_MONTH = 12;
    private static final int MIN_ALLOWED_DAY = 1;


    /**
     * Represents the days of the week.
     */
    private static enum DayOfWeek {

        /**
         * The days of the week.
         */
        SUNDAY {
            @Override
            public String toString() {
                return "Sunday";
            }
        },
        MONDAY {
            @Override
            public String toString() {
                return "Monday";
            }
        },
        TUESDAY {
            @Override
            public String toString() {
                return "Tuesday";
            }
        },
        WEDNESDAY {
            @Override
            public String toString() {
                return "Wednesday";
            }
        },
        THURSDAY {
            @Override
            public String toString() {
                return "Thursday";
            }
        },
        FRIDAY {
            @Override
            public String toString() {
                return "Friday";
            }
        },
        SATURDAY {
            @Override
            public String toString() {
                return "Saturday";
            }
        };

        /**
         * Returns the designation of the day of the week whose order is received as a parameter.
         *
         * @param dayOfWeekOrder the order of the day of the week between zero and six, inclusive. The lowest order corresponds to Sunday.
         * @return the designation of the day of the week.
         */
        public static String getDayOfWeekDesignation(int dayOfWeekOrder) {
            return DayOfWeek.values()[dayOfWeekOrder].toString();
        }
    }

    /**
     * Represents the months of the year.
     */
    private static enum Month {

        /**
         * The months of the year.
         */
        JANUARY(31) {
            @Override
            public String toString() {
                return "January";
            }
        },
        FEBRUARY(28) {
            @Override
            public String toString() {
                return "February";
            }
        },
        MARCH(31) {
            @Override
            public String toString() {
                return "March";
            }
        },
        APRIL(30) {
            @Override
            public String toString() {
                return "April";
            }
        },
        MAY(31) {
            @Override
            public String toString() {
                return "May";
            }
        },
        JUNE(30) {
            @Override
            public String toString() {
                return "June";
            }
        },
        JULY(31) {
            @Override
            public String toString() {
                return "July";
            }
        },
        AUGUST(31) {
            @Override
            public String toString() {
                return "August";
            }
        },
        SEPTEMBER(30) {
            @Override
            public String toString() {
                return "September";
            }
        },
        OCTOBER(31) {
            @Override
            public String toString() {
                return "October";
            }
        },
        NOVEMBER(30) {
            @Override
            public String toString() {
                return "November";
            }
        },
        DECEMBER(31) {
            @Override
            public String toString() {
                return "December";
            }
        };

        /**
         * The number of days in a month.
         */
        private int numberOfDays;

        /**
         * Constructs a month with the number of days received as a parameter.
         *
         * @param numberOfDays the number of days in the month.
         */
        private Month(int numberOfDays) {
            this.numberOfDays = numberOfDays;
        }

        /**
         * Returns the number of days of the month of the year received as a parameter.
         *
         * @param year the year of the month.
         * @return the number of days of the month of the year.
         */
        public int numberOfDays(int year) {
            if (ordinal() == 1 && Data.isLeapYear(year)) {
                return numberOfDays + 1;
            }
            return numberOfDays;
        }

        /**
         * Returns the month whose order is received as a parameter.
         *
         * @param monthOrder the order of the month.
         * @return the month whose order is received as a parameter.
         */
        public static Month getMonth(int monthOrder) {
            return Month.values()[monthOrder - 1];
        }

    }

    /**
     * Constructs an instance of Data by receiving the year, month, and day, calling the @method setData.
     *
     * @param year  the year of the date.
     * @param month the month of the date.
     * @param day   the day of the date.
     */
    public Data(int year, int month, int day) {
        setData(year, month, day);
    }

    /**
     * Constructs an instance of Data with the default date.
     */
    public Data() {
        year = DEFAULT_YEAR;
        month = DEFAULT_MONTH;
        day = DEFAULT_DAY;
    }

    /**
     * Constructs an instance of Data with the same characteristics as the date received by parameter.
     *
     * @param otherDate the date with the characteristics to copy.
     */
    public Data(Data otherDate) {
        year = otherDate.year;
        month = otherDate.month;
        day = otherDate.day;
    }

    /**
     * Returns the year of the date.
     *
     * @return year of the date.
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the month of the date.
     *
     * @return month of the date.
     */
    public int getMonth() {
        return month.ordinal() + 1;
    }

    /**
     * Returns the day of the date.
     *
     * @return day of the date.
     */
    public int getDay() {
        return day;
    }

    /**
     * Modifies the year, month, and day of the date.
     *
     * @param year  the new year of the date.
     * @param month the new month of the date.
     * @param day   the new day of the date.
     */
    public final void setData(int year, int month, int day) {
        if (year < MIN_ALLOWED_YEAR ) {
            throw new IllegalArgumentException("INVALID YEAR, verify that year is gratter than: [" + MIN_ALLOWED_YEAR + "]");
        }

        this.year = year;

        if (month < MIN_ALLOWED_MONTH || month > MAX_ALLOWED_MONTH) {
            throw new IllegalArgumentException("INVALID MONTH, verify that is in the interval: [" + MIN_ALLOWED_MONTH + "-" + MAX_ALLOWED_MONTH + "]");
        }

        this.month = Month.getMonth(month);

        if ((day < MIN_ALLOWED_DAY) || (day > this.month.numberOfDays)) {
            throw new IllegalArgumentException("INVALID DAY, verify that is in the interval: [" + MIN_ALLOWED_DAY + "-" + this.month.numberOfDays + "]");
        }

        this.day = day;
    }

    /**
     * Returns the textual description of the date in the format: dayOfWeek, day of month of year.
     *
     * @return characteristics of the date.
     */
    @Override
    public String toString() {
        return String.format("%s, %d of %s of %d", dayOfWeek(), day, month, year);
    }

    /**
     * Returns the date in the format: %04d/%02d/%02d.
     *
     * @return characteristics of the date.
     */
    public String toYearMonthDayString() {
        return String.format("%04d/%02d/%02d", year, month.ordinal() + 1, day);
    }

    /**
     * Compares the date with the object received.
     *
     * @param otherObject the object to compare with the date.
     * @return true if the received object represents a date equivalent to the date. Otherwise, returns false.
     */
    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || getClass() != otherObject.getClass()) {
            return false;
        }
        Data otherDate = (Data) otherObject;
        return year == otherDate.year && month.equals(otherDate.month)
                && day == otherDate.day;
    }

    /**
     * Compares the date with the other date received by parameter.
     *
     * @param otherDate the date to be compared.
     * @return the value 0 if the otherDate received is equal to the date; the value -1 if the otherDate is after the date; the value 1 if the otherDate is before the date.
     */
    @Override
    public int compareTo(Data otherDate) {
        return (otherDate.isGreater(this)) ? -1 : (isGreater(otherDate)) ? 1 : 0;
    }

    /**
     * Returns the day of the week of the date.
     *
     * @return day of the week of the date.
     */
    public String dayOfWeek() {
        int totalDays = countDays();
        totalDays = totalDays % 7;

        return DayOfWeek.getDayOfWeekDesignation(totalDays);
    }


    /**
     * Returns true if the date is greater than the date received by parameter. If the date is less than or equal to the date received by parameter, returns false.
     *
     * @param otherDate the other date with which the date is compared.
     * @return true if the date is greater than the date received by parameter, otherwise returns false.
     */
    public boolean isGreater(Data otherDate) {
        int totalDays = countDays();
        int totalDays1 = otherDate.countDays();

        return totalDays > totalDays1;
    }

    public int isGreaterByYear(int yearParam) {
        int year = this.year;
        return Integer.compare(year, yearParam);
    }

    public boolean isGreaterOrEquals(Data otherDate) {
        int totalDays = countDays();
        int totalDays1 = otherDate.countDays();

        return totalDays >= totalDays1;
    }


    /**
     * Returns the difference in number of days between the date and the date received by parameter.
     *
     * @param otherDate the other date with which the date is compared to calculate the difference in number of days.
     * @return difference in number of days between the date and the date received by parameter.
     */
    public int difference(Data otherDate) {
        int totalDays = countDays();
        int totalDays1 = otherDate.countDays();

        return Math.abs(totalDays - totalDays1);
    }

    /**
     * Verifies if the difference between two dates exceeds 18 years
     *
     * @param data to be compared
     * @return true if exceeds 18 years, false otherwise
     */
    public boolean over18(Data data) {
        int result = difference(data);
        return (result / 365) >= 18;
    }

    /**
     * Returns the difference in number of days between the date and the date received by parameter with year, month, and day.
     *
     * @param year  the year of the date with which the date is compared to calculate the difference in number of days.
     * @param month the month of the date with which the date is compared to calculate the difference in number of days.
     * @param day   the day of the date with which the date is compared to calculate the difference in number of days.
     * @return difference in number of days between the date and the date received by parameter with year, month, and day.
     */
    public int difference(int year, int month, int day) {
        int totalDays = countDays();
        Data otherDate = new Data(year, month, day);
        int totalDays1 = otherDate.countDays();

        return Math.abs(totalDays - totalDays1);
    }

    /**
     * Returns true if the year passed by parameter is a leap year. If the year passed by parameter is not a leap year, returns false.
     *
     * @param year the year to validate.
     * @return true if the year passed by parameter is a leap year, otherwise returns false.
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * Returns the current date of the system.
     *
     * @return the current date of the system.
     */
    public static Data currentDate() {
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH) + 1;    // January is represented by 0.
        int day = today.get(Calendar.DAY_OF_MONTH);
        return new Data(year, month, day);
    }

    /**
     * Returns the number of days from day 1/1/1 to the date.
     *
     * @return number of days from day 1/1/1 to the date.
     */
    private int countDays() {
        int totalDays = 0;

        for (int i = 1; i < year; i++) {
            totalDays += isLeapYear(i) ? 366 : 365;
        }
        for (int i = 1; i < month.ordinal() + 1; i++) {
            totalDays += Month.getMonth(i).numberOfDays(year);
        }
        totalDays += day;

        return totalDays;
    }

    /**
     *
     * @param days
     * @return add days to a date and returns date in specific format.
     */
    public Data calculateData(int days) {
        int ttlDays = this.countDays() + days;

        int year = 1;
        int month = 1;
        int day = 1;

        // Find the year
        while (ttlDays > 365) {
            if (isLeapYear(year))
                ttlDays -= 366;
            else
                ttlDays -= 365;
            year++;
        }

        // Find the month and day within that year
        for (int i = 1; i <= 12; i++) {
            int daysInMonth = Month.getMonth(i).numberOfDays(year);
            if (ttlDays <= daysInMonth) {
                month = i;
                day = ttlDays;
                break;
            }
            ttlDays -= daysInMonth;
        }

        return new Data(year, month, day);
    }

    /**
     * @param day
     * @param month
     * @return true if current day and month equals birthdate day and month.
     */
    public boolean isBirthDate(int day, int month) {
        return currentDate().getDay() == day && currentDate().getMonth() == month;
    }

    public boolean isGraterThanCurrentDate(){
        return this.isGreaterOrEquals(currentDate());
    }


}
