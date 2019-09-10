package com.example.demo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Calendar;
import java.util.TimeZone;


@RestController
public class hellocont {
    @RequestMapping("/hello")
    public String welcomeMessage(@RequestParam(value = "username") String username) {
        return "Hello " + username + " welcome to my demo!";
    }

    Integer count = 0;

    @RequestMapping("/counter")
    public String counter() {
        count++;
        if (count == 1) {
            return "your webpage has only been viewed once.";
        }
        if (count == 100) {
            return "Congrats on your first 100 visitors!";
        }
        return "your webpage has been viewed a total of " + count.toString() + " times";
    }


    @RequestMapping("/timeZone")
    public String userTZandLocation() {
        TimeZone tz = Calendar.getInstance().getTimeZone();
        String userTimeZone=tz.getDisplayName();
        String userLocation=tz.getID();
        return "Users time zone is " +userTimeZone +"\n Users' server location is "+userLocation;

    }

}



