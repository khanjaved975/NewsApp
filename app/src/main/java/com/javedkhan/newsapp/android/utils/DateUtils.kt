package com.javedkhan.newsapp.android.utils

import android.provider.Settings
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtils {
    fun addDay(date: Date?, i: Int): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.DAY_OF_YEAR, i)

        return cal.time
    }

    fun addMonth(date: Date?, i: Int): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.MONTH, i)
        return cal.time
    }

    fun addYear(date: Date?, i: Int): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.YEAR, i)
        return cal.time
    }

    fun CalculateDaysBetweenDates(startdate: String?, endDate: String?): String {
        var dates = ""
        val myFormat = SimpleDateFormat("dd/MM/yyyy")
        try {
            val dateBefore = myFormat.parse(startdate)
            val dateAfter = myFormat.parse(endDate)
            val difference = dateAfter.time - dateBefore.time
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            var workDays = 0
            var weekoff = 0
            //                    Date startDate= null;
//                    Date endDate= null;
//                Date startDate = sdf.parse(Day + "/" + month + "/" + year);
//                Date endDate = sdf.parse(strDay + "/" + month + "/" + year);
            val startCal = Calendar.getInstance()
            startCal.time = dateBefore
            val endCal = Calendar.getInstance()
            endCal.time = dateAfter
            do {
                //excluding start date
                startCal.add(Calendar.DAY_OF_MONTH, 1)
                if (startCal[Calendar.DAY_OF_WEEK] != Calendar.SATURDAY && startCal[Calendar.DAY_OF_WEEK] != Calendar.SUNDAY) {
                    ++workDays
                } else weekoff++
            } while (startCal.timeInMillis < endCal.timeInMillis)
            val daysBetween = (difference / (1000 * 60 * 60 * 24)).toFloat()

//            dates = String.valueOf(daysBetween);
            dates = workDays.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dates
    }

    fun changeDateFormate(sDate: String?): String {
        val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        var date: Date? = null
        try {
            date = inputFormat.parse(sDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return outputFormat.format(date)
    }

    fun changeDateToDesireFormate(
        sDate: String,
        inputFormat: DateFormat,
        outputFormat: DateFormat
    ): String {
//        DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
//        DateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy");
        return if (!sDate.isEmpty()) {
            var date: Date? = null
            try {
                date = inputFormat.parse(sDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            if (date != null) {
                outputFormat.format(date)
            } else {
                sDate.replace("\\'".toRegex(), "")
            }
        } else {
            sDate
        }
    }

    fun getDaysBetweenDates(start: String?, end: String?): Long {
        val dateFormat = SimpleDateFormat(Settings.System.DATE_FORMAT, Locale.ENGLISH)
        val startDate: Date
        val endDate: Date
        var numberOfDays: Long = 0
        try {
            startDate = dateFormat.parse(start)
            endDate = dateFormat.parse(end)
            numberOfDays = getUnitBetweenDates(startDate, endDate, TimeUnit.DAYS)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return numberOfDays
    }

    private fun getUnitBetweenDates(startDate: Date, endDate: Date, unit: TimeUnit): Long {
        val timeDiff = endDate.time - startDate.time
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS)
    }

    fun compareFromToDate(sipDate: String, sipStartDate: String): Boolean {
        try {
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            val date1 = formatter.parse(sipDate)
            val date2 = formatter.parse(sipStartDate)
            return if (date1.compareTo(date2) < 0) {
                println("date2 is Greater than my date1")
                true
            } else {
                false
            }
        } catch (e1: ParseException) {
            e1.printStackTrace()
        }
        return false
    }

    fun convertStringToDate(date: String): Date? {
        var fromdate: Date? = null
        val format = SimpleDateFormat("MM/dd/yyyy")
        try {
            fromdate = format.parse(date)
            println(fromdate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return fromdate
    }

    fun getCalendar(date: Date?): Calendar {
        val cal = Calendar.getInstance(Locale.US)
        cal.time = date
        return cal
    }
}