package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Holiday;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class HolidayController {

    @GetMapping("holidays")
    public String displayHolidays(Model model) {
        List<Holiday> holidays = Arrays.asList(
                new Holiday( " Jan 1 ","New Year's Day" , Holiday.Type.FEDERAL),
                new Holiday( " Oct 31 ","Hollowen" , Holiday.Type.FESTIVAL),
                new Holiday( " Nov 24 ","Thanksgiving Day" , Holiday.Type.FESTIVAL),
                new Holiday( " Dec 25 ","Christmas" , Holiday.Type.FESTIVAL),
                new Holiday( " Jan 1 ","New Year's Day" , Holiday.Type.FEDERAL),
                new Holiday( " Jan 1 ","New Year's Day" , Holiday.Type.FEDERAL),
                new Holiday( " Jan 1 ","New Year's Day" , Holiday.Type.FEDERAL),
                new Holiday( " Jan 1 ","New Year's Day" , Holiday.Type.FEDERAL)
        );
        // course 88 time 3.28
        return "";
    }
}
